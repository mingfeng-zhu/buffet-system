<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input
        v-model="listQuery.username"
        placeholder="用户名"
        style="width: 200px;"
        class="filter-item"
        @keyup.enter.native="handleFilter"
      />
      <el-select
        style="margin-left: 10px;"
        v-model="listQuery.userStatus"
        clearable
        filterable
        placeholder="请选择状态"
      >
        <el-option
          v-for="item in statusOptions"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        >
        </el-option>
      </el-select>
      <el-button
        style="margin-left: 10px;"
        v-waves
        class="filter-item"
        type="primary"
        icon="el-icon-search"
        @click="handleFilter"
        >搜索</el-button
      >
    </div>

    <el-table
      :key="tableKey"
      v-loading="listLoading"
      :data="list"
      border
      fit
      highlight-current-row
      style="width: 100%;top: 20px"
    >
      <el-table-column label="ID" prop="id" align="center" width="55">
        <template slot-scope="scope">
          <span>{{ scope.$index }}</span>
        </template>
      </el-table-column>

      <el-table-column label="用户名" align="center" width="200px">
        <template slot-scope="scope">
          <span>{{ scope.row.userName }}</span>
        </template>
      </el-table-column>
      <el-table-column label="性别" align="center" width="100px">
        <template slot-scope="scope">
          <span>{{ scope.row.userSex | sexFilter }}</span>
        </template>
      </el-table-column>
      <el-table-column label="用户头像" width="75px">
        <template slot-scope="scope">
          <span>
            
            <el-image
              style="width: 50px; height: 50px"
              :src="common.imageUrl + scope.row.userImg"
              :fit="'cover'"></el-image>
          </span>
        </template>
      </el-table-column>
      <el-table-column label="电话号码" width="300px" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.userTel }}</span>
        </template>
      </el-table-column>
      
      <el-table-column
        label="状态"
        class-name="status-col"
        width="130"
        align="center"
      >
        <template slot-scope="{ row }">
          <el-tag
            :type="row.userState | statusFilter"
            @click="openDelete(row)"
          >
            {{ row.userState | statusNameFilter }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="地址信息"  align="center">
        <template slot-scope="{ row }">
          <span
            ><el-button
              type="primary"
              icon="el-icon-search"
              plain
              round
              size="mini"
              @click="getAddressList(row)"
              >查看</el-button
            ></span
          >
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="listQuery.pageNum"
      :limit.sync="listQuery.pageSize"
      @pagination="getList"
    />
    <el-dialog title="用户积分明细" :visible.sync="dialogFormCreditVisible">
      <div class="block">
        <el-timeline>
          <el-timeline-item
            v-for="(item, index) in creditRecordList"
            :timestamp="item.createTime | parseTime"
            placement="top"
            :key="index"
          >
            <el-card>
              <h4>{{ item.record }}</h4>
              <p>
                {{ item.userPO.username }} 提交于
                {{ item.createTime | parseTime }}
              </p>
            </el-card>
          </el-timeline-item>
        </el-timeline>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancelCreditDialog()">取消</el-button>
      </div>
    </el-dialog>

    <el-dialog
      title="用户地址信息"
      width="75%"
      :visible.sync="dialogFormAddressVisible"
    >
      <el-table
        v-loading="addressListLoading"
        :data="addressList"
        border
        highlight-current-row
        style="width: 100%"
      >
        <el-table-column label="ID" prop="id" align="center" width="80">
          <template slot-scope="scope">
            <span>{{ scope.$index }}</span>
          </template>
        </el-table-column>
        <el-table-column label="收货人姓名" width="100px" align="center">
          <template slot-scope="scope">
            <span>{{ scope.row.receiverName }}</span>
          </template>
        </el-table-column>
        <el-table-column label="收货人号码" align="center">
          <template slot-scope="scope">
            <span>{{ scope.row.receiverPhone }}</span>
          </template>
        </el-table-column>
        <el-table-column label="详细地址" width="300px" align="center">
          <template slot-scope="scope">
            <span>{{ scope.row.address }}</span>
          </template>
        </el-table-column>
        <el-table-column label="门牌号" align="center">
          <template slot-scope="scope">
            <span>{{ scope.row.houseNumber }}</span>
          </template>
        </el-table-column>
        <el-table-column
          label="状态"
          class-name="status-col"
          width="100"
          align="center"
        >
          <template slot-scope="{ row }">
            <el-tag :type="row.addressDelete | addressDeleteFilter">
              {{ row.addressDelete | addressDeleteNameFilter }}
            </el-tag>
          </template>
        </el-table-column>
      </el-table>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormAddressVisible = false">取消</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import { getUserList, updateUserStatus } from '@/api/user/user'
import { getAddressListByUserId } from '@/api/user/address'
// import { getCreditRecordListByUserId } from '@/api/admin/credit'
import waves from '@/directive/waves'
import { parseTime } from '@/utils'
import Pagination from '@/components/Pagination'
export default {
  name: 'User',
  components: { Pagination },
  directives: { waves },
  filters: {
    parseTime(time) {
      var date = new Date(time)
      return parseTime(date, '{y}-{m}-{d} {h}:{i}')
    },
    statusFilter(status) {
      const statusMap = {
        0: 'danger',
        1: 'success'
      }
      return statusMap[status]
    },
    statusNameFilter(status) {
      const statusInfo = {
        0: '禁用',
        1: '正常'
      }
      return statusInfo[status]
    },
    addressDeleteFilter(status) {
      const statusMap = {
        0: 'success',
        1: 'danger'
      }
      return statusMap[status]
    },
    addressDeleteNameFilter(status) {
      const statusInfo = {
        0: '正常',
        1: '已删除'
      }
      return statusInfo[status]
    },
    sexFilter(sex) {
      const sexInfo = {
        1: '男',
        2: '女'
      }
      return sexInfo[sex]
    }
  },
  data() {
    return {
      tableKey: 0,
      list: null,
      creditRecordList: null,
      addressList: null,
      total: 0,
      listLoading: true,
      listQuery: {
        pageNum: 1,
        pageSize: 20,
        username: '',
        userStatus: ''
      },
      statusOptions: [
        {
          value: '0',
          label: '禁用'
        },
        {
          value: '1',
          label: '正常'
        }
      ],
      temp: {
        id: '',
        userState: '',
        updateBy: 'admin'
      },
      dialogFormVisible: false,
      addressListLoading: true,
      dialogFormCreditVisible: false,
      addressListLoading: true,
      dialogFormAddressVisible: false
    }
  },
  created() {
    this.getList()
  },
  methods: {
    //禁用操作
    openDelete(row) {
      this.$confirm('是否继续禁用?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          this.handleForbid(row)
          
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '已取消'
          })
        })
    },
    // 获得商品列表
    getList() {
      this.listLoading = true
      getUserList(this.listQuery).then(response => {
        this.list = response.data.list
        this.total = response.data.total
        this.listLoading = false
      })
    },
    //根据userId获取积分记录
    getCreditList(row) {
      this.dialogFormCreditVisible = true
      let temp = {
        userId: row.id
      }
      getCreditRecordListByUserId(temp).then(response => {
        this.creditRecordList = response.data
      })
    },
    //取消积分dialog窗口
    cancelCreditDialog() {
      this.dialogFormCreditVisible = false
      this.creditRecordList = null
    },
    //根据userId获取地址
    getAddressList(row) {
      this.addressList = null
      this.addressListLoading = true
      this.dialogFormAddressVisible = true
      let temp = {
        userId: row.userId
      }
      getAddressListByUserId(temp).then(response => {
        this.addressList = response.data
        this.addressListLoading = false
      })
    },
    handleFilter() {
      this.listQuery.page = 1
      this.getList()
    },
    handleForbid(row) {
      this.temp = Object.assign({}, row)
      if (this.temp.userState === 0) {
        this.temp.userState = 1
      } else {
        this.temp.userState = 0
      }
      updateUserStatus({userId:this.temp.userId,'userState':this.temp.userState}).then(() => {
        if (row.userState === 0) {
          row.userState = 1
        } else {
          row.userState = 0
        }
        this.$message({
            type: 'success',
            message: '成功!'
          })
      })
    }
  }
}
</script>