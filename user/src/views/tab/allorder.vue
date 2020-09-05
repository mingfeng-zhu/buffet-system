<template>
  <div class="allOrder">
    <van-nav-bar
        title="全部订单"
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
            <div class="orderStatus">
              <div class="status">
                <div class="orderStatus" v-if="item.status === 0">已取消</div>
                <div class="orderStatus" v-if="item.status === 1">待支付</div>
                <div class="orderStatus" v-if="item.status === 2">待接单</div>
                <div class="orderStatus" v-if="item.status === 3">制作中</div>
                <div class="orderStatus" v-if="item.status === 4">派送中，待收货</div>
                <div class="orderStatus" v-if="item.status === 5">已确认收货，待评价</div>
                <div class="orderStatus" v-if="item.status === 6">已评价</div>
                <div class="orderStatus" v-if="item.status === 7">取消中</div>
              </div>
              <van-button v-if="item.status === 1" round type="danger" text="取消订单" @click="cancle(item.orderid)"/>
              <van-button v-if="item.status === 1" round type="danger" text="去支付" @click="pay(item.orderid,item.ordertitle,item.totalprice)"/>
              <van-button v-if="item.status === 4" round type="danger" text="确认收货" @click="receive()"/>
              <van-button v-if="item.status === 5" round type="danger" text="去评价" @click="evaluate()"/>
            </div>
          </li>
        </van-swipe-cell>
      </ul>
    </div>
  </div>
</template>

<script>
export default {
  name: "allorder",
  data(){
    return{
      orders:[
        {
          orderid:'1',
          ordertitle:'标题1',
          totalprice: 110,
          status:0
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
          status:7
        },
        {
          orderid:'4',
          ordertitle:'标题4',
          totalprice: 110,
          status:2
        },
        {
          orderid:'5',
          ordertitle:'标题5',
          totalprice: 210,
          status:3
        },
        {
          orderid:'6',
          ordertitle:'标题6',
          totalprice: 310,
          status:4
        },
        {
          orderid:'7',
          ordertitle:'标题7',
          totalprice: 110,
          status:5
        },
        {
          orderid:'8',
          ordertitle:'标题8',
          totalprice: 210,
          status:6
        }
      ]
    }
  },
  mounted() {
    //this.getOrders()
  },
  methods:{
    onClickLeft(){
      this.$router.back()
    },
    cancle(orderid){
      //调用订单取消接口
      this.params = {}
      this.params.id = orderid
      // this.$api.cancelOrder(this.params)
      location.reload()
      location.reload()
    },
    pay(id,title,price){
      sessionStorage.setItem('orderid',id)
      sessionStorage.setItem('ordertitle',title)
      sessionStorage.setItem('totalprice',price)
      this.$router.push('/pay')
    },
    receive(){
      //调用收货接口 将status更新为2
      //刷新页面
      location.reload()
    },
    evaluate(){
      //调用评价接口 status更新为3
      //刷新页面
      location.reload()
    },
    //获取待支付订单列表
    getOrders(){
      //调用订单获取接口
      this.params = {}
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
.allOrder{
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
        height: 130px;
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
          width: 100%;
          display: flex;
          flex-direction: row;
          margin-top: 10px;
          .status{
            width: 65%;
            font-size: 12px;
            color: #001410;
            font-weight: 600;
            margin-left: 20px;
          }
          .van-button{
            width: 35%;
            align-self: flex-end;
            margin-right: 10px;
          }
        }
      }
    }
  }
}
</style>
