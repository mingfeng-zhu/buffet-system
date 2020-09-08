<template>
<div class="orderPay">
  <van-nav-bar title="订单支付"
               left-arrow
               @click-left="onClickLeft"
  />

  <div class="orderDetail">
    <p class="orderId">订单号:{{orderid}}</p>
    <p class="orderTitle">{{ordertitle}}</p>
    <p class="orderPrice">￥{{totalprice}}</p>
  </div>

  <van-radio-group v-model="picked">
    <van-radio name="1" label-position="left" label-disabled>支付宝支付</van-radio>
    <van-radio name="2" label-position="left" label-disabled>微信支付</van-radio>
    <van-radio name="3" label-position="left" label-disabled>银联支付</van-radio>
  </van-radio-group>
  <div class="payFotter">
    <van-submit-bar button-text="订单支付" @submit="paySubmit()"></van-submit-bar>
  </div>
</div>
</template>

<script>
import Vue from 'vue';
import { RadioGroup, Radio } from 'vant';
Vue.use(Radio);
Vue.use(RadioGroup);
export default {
  name: "pay",
  data(){
    return{
      picked:'1', //支付方式
      orderid:'', //订单id
      ordertitle:'',
      totalprice: 0,
      paycode:'', //支付结果
      idlist:[],
      goodcountlist:[]

    }
  },
  mounted() {
    this.orderid = sessionStorage.getItem('orderid')
    this.ordertitle = sessionStorage.getItem('ordertitle')
    this.totalprice = sessionStorage.getItem('totalprice')
    this.idlist = JSON.parse(sessionStorage.getItem('idlist'))
    this.goodcountlist = JSON.parse(sessionStorage.getItem('goodcountlist'))
  },
  methods:{
    onClickLeft() {
      this.$router.push('/person')
    },
    paySubmit(){
       sessionStorage.setItem('orderid','')
       sessionStorage.setItem('ordertitle','')
       sessionStorage.setItem('totalprice','')
       sessionStorage.setItem('idlist','')
       sessionStorage.setItem('goodcountlist','')
       //调用支付接口，返回支付结果
       this.params = {}
       this.params.id = this.orderid
       this.params.idList = this.idlist
       this.params.goodCountList = this.goodcountlist
       // let that = this
       // this.$api.payOrder(this.params).then(function (response){
       //   that.paycode = response.data.code
       // })
       if(this.paycode === '2000') {
         this.$router.push("/success")
       }else {
         this.$router.push("/faild")
       }
    }
  }
}
</script>

<style lang="scss" scoped>
.orderPay{
  width: 100%;
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  background-color: #f6f6f6;
  .orderDetail{
    width: 100%;
    margin-top: 16px;
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
      width: 95%;
      font-size: 12px;
      color: #15c481;
      font-weight: 600;
      margin-top: 10px;
      display: flex;
      justify-content: flex-end;
    }
  }
  .van-radio-group{
    width: 100%;
    display: flex;
    flex-direction: column;
    margin-top: 40px;
    //margin-left: 5px;
    font-size: small;
    .van-radio{
      margin: 10px;
    }
  }
  .payFotter{
    width: 100%;
    height: 60px;
  }
}
</style>
