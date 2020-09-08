<template>
  <div class="unReceived">
    <van-nav-bar
        title="待收货订单"
        left-arrow
        @click-left="onClickLeft"
    />
    <div class="orderList" v-if="orders.length>0">
      <ul>
        <van-swipe-cell>
          <li v-for="(item,index) in orders" :key="index">
            <div class="orderDetail" @click="orderdetail(item)">
              <div class="orderId">订单编号：{{item.orderPO.orderId}}</div>
              <div class="orderTitle van-multi-ellipsis">
                  <span
                      v-for="(item2, index) in item.orderDetailAndProductDTO"
                      :key="index"
                  >
                     {{item2.productSpecificationDTO.productPO.productName}}*{{item2.orderDetail.goodCount}}
                  </span>
              </div>
              <div class="orderPrice">￥{{item.orderPO.totalMoney}}</div>
            </div>
            <div class="orderStatus">
              <van-button round size="small" plain text="取消订单" @click="cancle(item.orderPO.id)"/>
              <van-button round size="small" type="danger" text="确认收货" @click="receive(item.orderPO.id)"/>
            </div>
          </li>
        </van-swipe-cell>
      </ul>
    </div>
    <div class="getmore">
      <van-button square plain hairline type="default" text="加载更多" @click="getMore()" style="width: 100%" v-if="hasmore === 'true'"/>
    </div>
  </div>
</template>

<script>
export default {
  name: "unreceived",
  data(){
    return{
      pageIndex:1,
      orders:[],
      hasmore:'false',
    }
  },
  async created() {
    await this.getOrders()
  },
  methods:{
    onClickLeft(){
      this.$router.back()
    },
    async cancle(id){
      //调用订单取消接口
      this.params = {}
      this.params.id = id
      await this.$api.cancelOrder(this.params)
      location.reload()
    },
    async receive(id){
      //将status更新为5
      this.params={}
      this.params.id = id
      this.params.orderStatus = '5'
      await this.$api.editOrderStatus(this.params)
      //刷新页面
      location.reload()
    },
    //获取待收货订单列表
    async getOrders(){
      // 调用订单获取接口   status=4 待支付状态
      let that = this
      await this.$api.getOrder({pageNum:this.pageIndex,pageSize:5,orderStatus:'4'}).then(function (response){
        that.orders = that.orders.concat(response.data.data)
        if(response.data.data !== null){
          that.hasmore = 'true'
        }
        if(response.data.data.length < 5){
          that.hasmore = 'false'
        }
      })
      if(this.hasmore !== 'false') {
        await this.$api.getOrder({
          pageNum: this.pageIndex + 1,
          pageSize: 5,
          orderStatus: '4'
        }).then(function (response) {
          if (response.data.data === null) {
            that.hasmore = 'false'
          }
        })
      }
    },
    orderdetail(item){
      sessionStorage.setItem('orderid',item.orderPO.id)
      sessionStorage.setItem('serialnumber',item.orderPO.orderId)
      let title = ''
      let idlist = []
      let goodcountlist = []
      for(let i = 0;i<item.orderDetailAndProductDTO.length;i++){
        title += item.orderDetailAndProductDTO[i].productSpecificationDTO.productPO.productName+'*'+item.orderDetailAndProductDTO[i].orderDetail.goodCount+' '
        idlist.push(item.orderDetailAndProductDTO[i].orderDetail.goodId)
        goodcountlist.push(item.orderDetailAndProductDTO[i].orderDetail.goodCount)
      }
      sessionStorage.setItem('ordertitle',title)
      sessionStorage.setItem('totalprice',item.orderPO.totalMoney)
      sessionStorage.setItem('status',item.orderPO.orderStatus)
      sessionStorage.setItem('idlist',JSON.stringify(idlist))
      sessionStorage.setItem('goodcountlist',JSON.stringify(goodcountlist))
      sessionStorage.setItem('cartgoods',JSON.stringify(item.orderDetailAndProductDTO))
      this.$router.push('/orderdetail')
    },
    getMore(){
      this.pageIndex++
      this.getOrders()
    },
  }
}
</script>

<style lang="scss" scoped>
.unReceived{
  width: 100%;
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  background-color: #f6f6f6;
  .orderList{
    width: 100%;
    display: flex;
    flex-direction: column;
    margin-top: 16px;
    ul{
      width: 100%;
      display: flex;
      flex-direction: column;
      li{
        width: 100%;
        height: 125px;
        background-color: #fff;
        display: flex;
        flex-direction: column;
        margin-bottom: 3px;
        .orderDetail{
          width: 100%;
          display: flex;
          flex-direction: column;
          margin-left: 13px;
          .orderId{
            font-size: 16px;
            color: #001410;
            font-weight: 500;
            margin-left: 10px;
          }
          .orderTitle{
            font-size: 13px;
            color: #001410;
            font-weight: 400;
            margin-top: 5px;
            margin-left: 10px;
          }
          .orderPrice{
            width: 89%;
            font-size: 12px;
            color: #15c481;
            font-weight: 600;
            margin-top: 10px;
            display: flex;
            justify-content: flex-end;
          }
        }
        .orderStatus{
          display: flex;
          flex-direction: row;
          align-self: flex-end;
          margin-top: 15px;
          .van-button{
            margin-right: 10px;
          }
        }
      }
    }
  }
  .getmore{
    margin-bottom: 50px;
  }
}
</style>
