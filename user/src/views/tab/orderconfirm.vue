<template>
  <div class="orderConfirm">
    <van-nav-bar
        title="订单确认"
        left-arrow
        @click-left="onClickLeft"
    />
    <van-radio-group v-model="picked">
      <van-row gutter="40">
        <van-col >
          <van-radio name="1" label-disabled>堂食</van-radio>
        </van-col>
        <van-col >
          <van-radio name="2" label-disabled>外送</van-radio>
        </van-col>
      </van-row>
    </van-radio-group>

    <div class="address" v-if="picked ==='2'">
      <div class="noAddress" v-if="address.isSelected === 'false'" @click="selectAddress()">请选择配送地址</div>
      <div class="addSelected" v-else @click="selectAddress()">
        配送地址:
        <br>
        {{address.address}}
        <br>
        {{address.name}} {{address.tel}}</div>
    </div>

    <div class="cartgoodsList">
      <ul>
        <van-swipe-cell>
          <li v-for="item in cartgoods" :key="item.id">
            <div class="cartgoodDetail">
              <div class="detailImg">
                <img :src="item.productimg"/>
              </div>
              <div class="detailText">
                <div class="cartTitle van-multi-ellipsis--l2">{{ item.title }}</div>
                <div class="specification">
                  <span
                      v-for="(item2, index) in Object.values(JSON.parse(item.specification))"
                      :key="index"
                  >
                     {{item2}}
                  </span>
                </div>
                <div class="cartpriceNum">
                  <p class="cartPrice">
                    ¥{{ item.price }}
                  </p>
                  <p class="cartNum">
                    数量:{{ item.num }}
                  </p>
                </div>
                <div class="cartitemPrice">
                  ￥{{item.cartprice}}
                </div>
              </div>
            </div>
          </li>
        </van-swipe-cell>
      </ul>
    </div>
    <div class="cartFotter">
      <van-submit-bar button-text="提交订单" @submit="orderSubmit()">
        <div class="cartPrice">
          <p class="p1">合计</p>
          <p class="p2">¥{{ totalprice }}</p>
        </div>
      </van-submit-bar>
    </div>
  </div>
</template>

<script>
import Vue from 'vue';
import { RadioGroup, Radio } from 'vant';
import { Col, Row } from 'vant';
Vue.use(Radio);
Vue.use(RadioGroup);
Vue.use(Col);
Vue.use(Row);
export default {
  name: "orderconfirm",
  data(){
    return{
       picked:'1', //配送方式
       totalprice:0,
       orderid:'666',//订单id
       address: {
         isSelected:'false'
       },
      cartgoods:[],
    }
  },
  mounted() {
    let info = JSON.stringify(this.address)
    sessionStorage.setItem('address',info)
  },
  activated() {
    this.getdata()
    this.count()
    this.address = JSON.parse(sessionStorage.getItem('address'))
  },
  methods:{
    onClickLeft() {
      this.$router.back()
    },
    //选择地址
    async selectAddress(){
      sessionStorage.setItem('selected','1')
      await this.$router.push('/address')
      this.address.isSelected = 'true'
    },
    //订单提交
    async orderSubmit(){
      //调用订单生成接口  post方法 返回订单id
      this.params = {}
      if (this.picked === '1'){
        this.params.addressId = '0';
      }else {
        this.params.addressId = this.address.id
      }
      this.params.totalMoney = this.totalprice
      let idlist = []
      let goodcountlist = []
      let totalmoneylist = []
      let shopCartIdList = []
      let ordertitle = ''
      this.cartgoods.forEach(function (item,index){
        idlist.push(item.id)
        goodcountlist.push(item.num)
        totalmoneylist.push(item.cartprice)
        shopCartIdList.push(item.shopcartid)
        if (index > 0 ){
          if(item.num>1) {
            ordertitle += '+' + item.title + '*' + item.num
          }else{
            ordertitle += '+' + item.title
          }
        }else{
          if(item.num>1) {
            ordertitle += item.title + '*' + item.num
          }else{
            ordertitle += item.title
          }
        }
      })
      this.params.idList = idlist
      this.params.goodCountList = goodcountlist
      this.params.totalMoneyList = totalmoneylist
      this.params.shopCartIdList = shopCartIdList
      let that = this
      await this.$api.creatOrder(this.params).then(function (response){
        that.orderid = response.data.data
      })
      sessionStorage.setItem('orderid',this.orderid)
      sessionStorage.setItem('ordertitle',ordertitle)
      sessionStorage.setItem('totalprice',this.totalprice)
      sessionStorage.setItem('idlist',JSON.stringify(idlist))
      sessionStorage.setItem('goodcountlist',JSON.stringify(goodcountlist))
      this.$router.push("/pay")
    },
    // 计算价格
    count() {
      let totalPrice = 0   //临时总价
      let cartGoods = []
      let cartPrice = 0   //每个商品总价
      this.cartgoods.forEach(function(item) {
          cartPrice = Number(item.num) * Number(item.price);
          cartPrice=Number(cartPrice.toFixed(2))
          cartGoods.push({id:item.id,price:item.price,title:item.title,num:item.num,cartprice:cartPrice,
            productimg:item.productimg,specification:item.specification,shopcartid:item.shopcartid})
          totalPrice += cartPrice; //累计总价
          totalPrice=Number(totalPrice.toFixed(2))
      })
      this.cartgoods = cartGoods
      this.totalprice = totalPrice
    },
    async getdata(){
      this.cartgoods = JSON.parse(sessionStorage.getItem('cartgoods'))
    },
  }
}
</script>

<style lang="scss" scoped>
.orderConfirm{
  width: 100%;
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  background-color: #f6f6f6;
  .van-radio-group{
    width: 100%;
    display: flex;
    flex-direction: column;
    align-items: center;
    margin-top: 16px;
    font-size: small;
  }
  .address{
    margin-top: 16px;
    margin-left: 10px;
    font-size: small;
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
        height: 120px;
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
            height: 60%;
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
            .specification{
              margin-top: 5px;
               font-size: 10px;
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
  .cartFotter{
    width: 100%;
    height: 60px;
    .van-submit-bar__bar{
      height: 60px;
      font-size: 16px;
      .cartPrice{
        padding-right: 8px;
        text-align: right;
        display: flex;
        flex-direction: column;
        .p1{
          font-size: 10px;
          color: #001410;
        }
        .p2{
          font-size: 12px;
          color: #15c481;
          margin-top: 4px;
        }
      }
    }
  }
}
</style>
