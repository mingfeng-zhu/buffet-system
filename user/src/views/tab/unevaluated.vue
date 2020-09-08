<template>
  <div class="unEvaluated">
    <van-nav-bar
        title="待评价订单"
        left-arrow
        @click-left="onClickLeft"
    />
    <div class="orderList" v-if="orders.length>0">
      <ul>
        <van-swipe-cell>
          <li v-for="item in orders" :key="item.id">
            <div class="orderDetail" @click="orderdetail(item.orderid,item.ordertitle,item.totalprice,item.status)">
              <div class="orderId">订单号：{{item.orderid}}</div>
              <div class="orderTitle van-multi-ellipsis">{{item.ordertitle}}</div>
              <div class="orderPrice">￥{{item.totalprice}}</div>
            </div>
            <van-button round type="danger" text="去评价" @click="evaluate()"/>
          </li>
        </van-swipe-cell>
      </ul>
    </div>
  </div>
</template>

<script>
export default {
  name: "unevaluated",
  data(){
    return{
      orderStatus:'',
      orders:[
        {
          orderid:'1',
          ordertitle:'标题1',
          totalprice: 110,
          status:5
        },
        {
          orderid:'2',
          ordertitle:'标题2',
          totalprice: 210,
          status:5
        },
        {
          orderid:'3',
          ordertitle:'标题3',
          totalprice: 310,
          status:5
        }
      ]
    }
  },
  mounted() {
    // this.getOrders()
  },
  methods:{
    onClickLeft(){
      this.$router.back()
    },
    evaluate(){
      //调用评价接口 status更新为3
      //刷新页面
      location.reload()
    },
    //获取待评价订单列表
    getOrders(){
      //调用订单获取接口   status=2 待评价状态
      this.params = {}
      this.params.orderStatus = this.orderStatus
      // let that = this
      // this.$api.getOrder(this.params).then(function (response){
      //   that.orders = response.data.data
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
.unEvaluated{
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
        .van-button{
          margin-top: 5px;
          margin-right: 5px;
          align-self: flex-end;
        }
      }
    }
  }
}
</style>
