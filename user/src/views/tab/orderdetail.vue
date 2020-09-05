
<template>
  <div class="orderDetail">
    <van-nav-bar
        title="订单详情"
        left-arrow
        @click-left="onClickLeft"
    />
    <div class="order">
      <div class="detail">
        <div class="orderId">订单号:{{orderid}}</div>
        <div class="orderTitle van-multi-ellipsis">{{ordertitle}}</div>
        <div class="orderPrice">￥{{totalprice}}</div>
      </div>
      <div class="orderStatus">
        <div class="status">
          <div class="orderStatus" v-if="status === 0">已取消</div>
          <div class="orderStatus" v-if="status === 1">待支付</div>
          <div class="orderStatus" v-if="status === 2">待接单</div>
          <div class="orderStatus" v-if="status === 3">制作中</div>
          <div class="orderStatus" v-if="status === 4">派送中，待收货</div>
          <div class="orderStatus" v-if="status === 5">已确认收货，待评价</div>
          <div class="orderStatus" v-if="status === 6">已评价</div>
          <div class="orderStatus" v-if="status === 7">取消中</div>
        </div>
        <van-button v-if="status === 1" round type="danger" text="取消订单" @click="cancle()"/>
        <van-button v-if="status === 1" round type="danger" text="去支付" @click="pay()"/>
        <van-button v-if="status === 4" round type="danger" text="确认收货" @click="receive()"/>
        <van-button v-if="status === 5" round type="danger" text="去评价" @click="evaluate()"/>
      </div>
    </div>
    <div class="cartgoodsList">
      <ul>
        <van-swipe-cell>
          <li v-for="item in cartgoods" :key="item.id">
            <div class="cartgoodDetail">
              <div class="detailImg">
                <img
                    :src="item.img"
                />
              </div>
              <div class="detailText">
                <div class="cartTitle van-multi-ellipsis--l2">{{ item.title }}</div>
                <div class="cartpriceNum">
                  <p class="cartPrice">
                    ¥{{ item.price }}
                  </p>
                  <p class="cartNum">
                    数量:{{ item.num }}
                  </p>
                </div>
                <div class="cartitemPrice">
                  ￥:{{item.cartprice}}
                </div>
              </div>
            </div>
          </li>
        </van-swipe-cell>
      </ul>
    </div>
  </div>
</template>

<script>
export default {
  name: "detail",
  data(){
    return{
      orderid:'',
      ordertitle:'',
      totalprice:'',
      status:1,
      cartgoods:[
        {
          id:0,
          price: '20.00',
          title:'标题1',
          num:4,
          cartprice:80
        },
        {
          id:1,
          price: '30.00',
          title:'标题2',
          num:4,
          cartprice:120
        }
      ],
    }
  },
  mounted() {
    this.orderid = sessionStorage.getItem('orderid')
    this.ordertitle = sessionStorage.getItem('ordertitle')
    this.totalprice = sessionStorage.getItem('totalprice')
    this.status = parseInt(sessionStorage.getItem('status'))
    sessionStorage.setItem('orderid','')
    sessionStorage.setItem('ordertitle','')
    sessionStorage.setItem('totalprice','')
    sessionStorage.setItem('status','')
    this.getOrders()
  },
  methods:{
    onClickLeft() {
      this.$router.back()
    },
    cancle(){
      //调用订单取消接口
      this.params = {}
      this.params.id = this.orderid
      // this.$api.cancelOrder(this.params)
      location.reload()
      location.reload()
    },
    pay(){
      sessionStorage.setItem('orderid',this.orderid)
      sessionStorage.setItem('ordertitle',this.ordertitle)
      sessionStorage.setItem('totalprice',this.totalprice)
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
    getOrders(){
      //调用订单获取接口
      this.params = {}
      this.params.id = this.orderid
      // let that = this
      // this.$api.getOrder(this.params).then(function (response){
      //   that.order = response.data.data
      // })
    },
  }
}
</script>

<style lang="scss" scoped>
.orderDetail{
  width: 100%;
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  background-color: #f6f6f6;
  .order{
    width: 100%;
    display: flex;
    flex-direction: column;
    margin-top: 16px;
    .detail{
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
  .cartgoodsList{
    width: 100%;
    display: flex;
    flex-direction: column;
    align-items: center;
    margin-top: 16px;
    ul{
      width: 100%;
      display: flex;
      flex-direction: column;
      margin-bottom: 100px;
      li{
        width: 100%;
        height: 96px;
        background-color: #fff;
        display: flex;
        flex-direction: row;
        margin-bottom: 12px;
        .cartgoodDetail{
          width: 100%;
          display: flex;
          flex-direction: row;
          align-items: center;
          margin-left: 13px;
          .detailImg{
            width: 20%;
            height: 70%;
            background: rgba(165, 165, 165, 1);
            border-radius: 4px;
            img {
              width: 100%;
              height: 100%;
              border-radius: 4px;
            }
          }
          .detailText{
            width: 75%;
            height: 80%;
            display: flex;
            flex-direction: column;
            margin-left: 8px;
            margin-top: 10px;
            position: relative;
            .cartTitle{
              width: 180px;
              text-align: justify;
              font-size: 20px;
              color: #212121;
              line-height: 20px;
            }
            .cartpriceNum{
              width: 100%;
              display: flex;
              flex-direction: row;
              justify-content: space-between;
              margin-top: 5px;
              .cartPrice{
                font-size: 12px;
                color: #212121;
                font-weight: 400;
              }
              .cartNum{
                font-size: 12px;
                color: #212121;
                font-weight: 400;
              }
            }
            .cartitemPrice{
              width: 100%;
              font-size: 12px;
              color: #15c481;
              font-weight: 600;
              margin-top: 10px;
              display: flex;
              justify-content: flex-end;
            }
          }
        }
      }
    }
  }
}
</style>
