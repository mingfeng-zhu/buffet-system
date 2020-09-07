<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input
        v-model="listQuery.orderId"
        placeholder="订单流水号"
        style="width: 200px;"
        class="filter-item"
        @keyup.enter.native="handleFilter"
      />
      <el-select v-model="listQuery.orderStatus" clearable placeholder="请选择" style="width: 200px; margin-left: 10px;">
        <el-option
          v-for="item in statusList"
          :key="item.index"
          :label="item.name"
          :value="item.index">
        </el-option>
      </el-select>
      <el-button
        v-waves
        class="filter-item"
        type="primary"
        style="margin-left: 10px;"
        icon="el-icon-search"
        @click="handleFilter"
      >搜索</el-button>
      <el-button
        class="filter-item"
        style="float: right;margin-left: 10px;"
        type="primary"
        @click="sureCancelOrder()"
      >确认取消</el-button>
      <el-button
        class="filter-item"
        style="float: right;margin-left: 10px;"
        type="primary"
        @click="deliveryOrder()"
      >配送</el-button>
      <el-button
        class="filter-item"
        style="float: right;margin-left: 10px;"
        type="primary"
        @click="receiveOrder()"
      >接单</el-button>

    </div>
    <el-table :data="orderList" border style="width: 100%; margin-top:10px" @selection-change="selectChange">
      <el-table-column
        type="selection"
        width="55">
      </el-table-column>
      <el-table-column fixed label="序号" width="50px" type="index" align="center" />
      <!-- <el-table-column fixed prop="productCategoryId" label="分类ID" /> -->
      <el-table-column fixed prop="orderId" label="订单流水号">
        <template scope="scope"  >
          <el-popover
            placement="right"
            width="400"
            trigger="click"
            >
            <div v-loading="loading">
              <el-timeline :reverse="false" style="margin-top: 20px;" >
                <el-timeline-item
                  v-for="(activity, index) in activities"
                  :key="index"
                  :timestamp="activity.changeTime">
                  {{activity.status | parseOrderStatus}}
                </el-timeline-item>
              </el-timeline>
            </div>
            <el-link slot="reference" type="primary" @click="detail(scope.row.id)" :underline="false">
             {{ scope.row.orderId}}
            </el-link>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column fixed prop="orderStatus" label="订单状态" >
        <template scope="scope">
          {{ scope.row.orderStatus | parseStatus }}
        </template>
      </el-table-column>
      <el-table-column fixed prop="userPO.userTel" label="下单号码" />
      <el-table-column fixed prop="totalMoney" label="总价(元)" >
        <template scope="scope">
          {{ scope.row.totalMoney}}
        </template>
      </el-table-column>
      <el-table-column fixed prop="createTime" label="收货地址">
        <template scope="scope">
          {{ scope.row.addressPO.address}}
        </template>
      </el-table-column>
      <el-table-column fixed prop="createTime" label="下单时间">
        <template scope="scope">
          {{ scope.row.createTime | parseTime }}
        </template>
      </el-table-column>


      <el-table-column fixed="right" label="操作" style="text-align: center;">
        <template slot-scope="scope">
          <el-button
            class="el-icon-view"
            type="primary"
            size="small"
            @click="orderDetail(scope.row.id)"
          > 查看</el-button>

        </template>
      </el-table-column>
    </el-table>
    <pagination
      v-show="listQuery.total > 0"
      :total="listQuery.total"
      :page.sync="listQuery.pageNum"
      :limit.sync="listQuery.pageSize"
      @pagination="listOrderList"
    />
    <el-dialog title="收货地址" :visible.sync="dialogTableVisible">
      <el-table :data="orderDetailAndProductDTO">
        <el-table-column property="productSpecificationDTO.productPO.productName" label="商品名称" width="150"></el-table-column>
        <el-table-column property="orderDetail.goodCount" label="购买数量" width="200"></el-table-column>
        <el-table-column property="orderDetail.money" label="小计(元)"></el-table-column>
        <el-table-column fixed prop="scope.row.productSpecificationDTO.productPO.productPicturePath" label="商品">
          <template scope="scope">
            <el-image
              style="width: 100px; height: 100px"
              :src="scope.row.productSpecificationDTO.productPO.productPicturePath"
              fit="contain">
            </el-image>
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>
  </div>
</template>

<script>
  import { listOrder, listOrderStatusList, getOrderById, updateOrderByIdList, cancelOrderList } from '@/api/order/order'
  import { listOrderStatusRecordByOrderId } from '@/api/order/orderStatus'

  import Pagination from '@/components/Pagination'
  import waves from '@/directive/waves'
  import { parseTime } from '@/utils'
  let thatData;
  export default {
    components: { Pagination },
    directives: { waves },
    filters: {
      parseTime(time) {
        var date = new Date(time)
        return parseTime(date, '{y}-{m}-{d} {h}:{i}')
      },
      parseStatus(status) {
        for(let count = 0; count < thatData.statusList.length; count++){
          if(status == parseInt(thatData.statusList[count].index)) {
            return thatData.statusList[count].name
          }
        }
      },
      parseOrderStatus(status) {
        for(let count = 0; count < thatData.orderStatusNameList.length; count++){
          if(status == thatData.orderStatusNameList[count].index) {
            return thatData.orderStatusNameList[count].name
          }
        }
      }
    },
    data() {
      return {
        orderList:[],
        statusList: [],
        listQuery: {
          total:0,
          pageNum: 1,
          pageSize: 10,
          orderStatus: null,
          orderId: null,
          userId: null
        },
        value: '',
        reverse: true,
        activities: [],
        loading: true,
        orderStatusNameList: [{
          index: "0",
          name: "已取消"
        },{
          index: "1",
          name: "下单成功，等待支付"
        }, {
          index: "2",
          name: "支付成功，等待商家接单"
        },{
          index: "3",
          name: "商家接单成功，等待配送"
        },{
          index: "4",
          name: "外卖配送中"
        },{
          index: "5",
          name: "外卖已到达，待评价"
        },{
          index: "6",
          name: "已评价，订单完成"
        },{
          index: "7",
          name: "订单取消中，待商家审核"
        },{
          index: "8",
          name: "超时未接单"
        }],
        dialogTableVisible: false,
        orderDetailAndProductDTO: [],
        selectList: []
      }
    },
    created() {
      this.init()
    },
    beforeCreate: function () {
      thatData = this;
    },
    methods: {
      init() {
        this.listOrderList()
        this.listOrderStatusList()
      },
      /**
       * 获取订单列表
       */
      listOrderList() {
        listOrder(this.listQuery).then(response => {
          this.orderList = response.data.list
          this.listQuery.total = response.data.total
        })
      },
      /**
       * 获取订单状态信息
       */
      listOrderStatusList(){
        listOrderStatusList().then(response => {
          this.statusList = response.data
        })
      },
      /**
       * 搜索
       */
      handleFilter() {
        this.listQuery.pageNum = 1
        this.listOrderList()
      },
      /**
       * 查看订单时间线详情
       */
      detail(id) {
        let param = {
          'orderId': id
        }
        listOrderStatusRecordByOrderId(param).then(response => {
          this.activities = response.data
          this.loading = false
          // this.statusList = response.data
        })
      },
      /**
       * 订单时间线的展示润色
       */
      orderDetail(id) {
        let param = {
          'id': id
        }
        getOrderById(param).then(response => {
          // this.activities = response.data
          // this.loading = false
          this.orderDetailAndProductDTO = response.data.orderDetailAndProductDTO
          this.dialogTableVisible = true
          // this.statusList = response.data
        })
      },
      /**
       * 多选处理
       * */
      selectChange(selection) {
        this.selectList = []
        for(let count = 0; count < selection.length; count++) {
          let param = {
            id: selection[count].id,
            orderStatus: selection[count].orderStatus
          }
          this.selectList.push(param)
        }
      },
      /**
       * 批量接单
       */
      receiveOrder() {
        let that = this
        let idList = [];
        for(let i = 0; i < that.selectList.length; i++) {
          if(that.selectList[i].orderStatus == '2') {
            idList.push(that.selectList[i].id)
          }
        }
        if(idList.length == 0) {
          this.$message({
            message: '您还未选中处于待接单状态的订单！',
            type: 'success'
          });
          return ;
        }
        let param = {
          'idList': idList,
          'orderStatus': "3"
        }
        updateOrderByIdList(param).then(response => {
          this.$message({
            message: '所有待接单的订单都已成功接受',
            type: 'success'
          });
          that.listOrderList()
        })
      },
      /**
       * 批量确认取消订单
       */
      sureCancelOrder() {
        let that = this
        let idList = [];
        for(let i = 0; i < that.selectList.length; i++) {
          if(that.selectList[i].orderStatus == '7') {
            idList.push(that.selectList[i].id)
          }
        }
        if(idList.length == 0) {
          this.$message({
            message: '您还未选中处于取消中状态的订单！',
            type: 'success'
          });
          return ;
        }
        let param = {
          'idList': idList,
          'orderStatus': "0"
        }
        cancelOrderList(param).then(response => {
          this.$message({
            message: '所有待取消的订单都已成功取消',
            type: 'success'
          });
          that.listOrderList()
        })
      },
      /**
       * 批量派送接单
       */
      deliveryOrder() {
        let that = this
        let idList = [];
        for(let i = 0; i < that.selectList.length; i++) {
          if(that.selectList[i].orderStatus == '3') {
            idList.push(that.selectList[i].id)
          }
        }
        if(idList.length == 0) {
          this.$message({
            message: '您还未选中处于待配送状态的订单！',
            type: 'success'
          });
          return ;
        }
        let param = {
          'idList': idList,
          'orderStatus': "4"
        }
        updateOrderByIdList(param).then(response => {
          this.$message({
            message: '所有待接单的订单都已成功派送',
            type: 'success'
          });
          that.listOrderList()
        })
      }
    }
  }
</script>

<style>

</style>
