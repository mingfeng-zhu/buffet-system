<template>
<div class="unPaid">
  <van-nav-bar
      title="待支付订单"
      left-arrow
      @click-left="onClickLeft"
  />
  <div class="orderList" v-if="orders.length>0">
    <ul>
      <van-swipe-cell>
        <li v-for="item in orders" :key="item.id">
          <div class="orderDetail" @click="orderdetail(item.orderid,item.ordertitle,item.totalprice,item.status)">
            <div class="orderId">订单编号：{{item.orderid}}</div>
            <div class="orderTitle van-multi-ellipsis">{{item.ordertitle}}</div>
            <div class="orderPrice">￥{{item.totalprice}}</div>
          </div>
          <div class="orderStatus">
            <van-button round type="danger" text="取消订单" @click="cancle(item.orderid)"/>
            <van-button round type="danger" text="去支付" @click="pay(item.orderid,item.ordertitle,item.totalprice)"/>
          </div>
        </li>
      </van-swipe-cell>
    </ul>
  </div>
</div>
</template>

<script>
export default {
  name: "unpaid",
  data(){
    return{
      orderStatus:'',
      orders:[
        {
          orderid:'1',
          ordertitle:'标题1',
          totalprice: 110,
          status:1
        },
        {
          orderid:'2',
          ordertitle:'标题2',
          totalprice: 210,
          status:1
        },
        {
          orderid:'3',
          ordertitle:'标题3',
          totalprice: 310,
          status:1
        },
      ],
    }
  },
  mounted() {
    // this.getOrders()
  },
  methods:{
    onClickLeft() {
      this.$router.push('/person')
    },
    cancle(orderid){
      //调用订单取消接口
      this.params = {}
      this.params.id = orderid
      // this.$api.cancelOrder(this.params)
      location.reload()
    },
    pay(id,title,price){
      sessionStorage.setItem('orderid',id)
      sessionStorage.setItem('ordertitle',title)
      sessionStorage.setItem('totalprice',price)
      this.$router.push('/pay')
    },
    //获取待支付订单列表
    getOrders(){
      // 调用订单获取接口   status=0 待支付状态
      this.params = {}
      this.params.orderStatus = this.orderStatus
      // let that = this
      // this.$api.getOrder(this.params).then(function (response){
      //    that.orders = response.data.data
      // })
    },
    orderdetail(orderid,ordertitle,totalprice,status){
      sessionStorage.setItem('orderid',orderid)
      sessionStorage.setItem('ordertitle',ordertitle)
      sessionStorage.setItem('totalprice',totalprice)
      sessionStorage.setItem('status',status)
      this.$router.push('/orderdetail')
    },
  }
}
</script>

<style lang="scss" scoped>
.unPaid{
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
      margin-bottom: 100px;
      li{
        width: 100%;
        height: 125px;
        background-color: #fff;
        display: flex;
        flex-direction: column;
        margin-bottom: 12px;
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
          margin-top: 5px;
          .van-button{
            margin-right: 10px;
          }
        }
      }
    }
  }
}
</style>
