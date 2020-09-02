<template>
  <div class="shopCart">
    <!-- <cartNav></cartNav> -->
    <div class="cartList">
      <ul v-if="goods.length > 0">
        <li v-for="(item, index) in goods" :key="index">
          <van-swipe-cell>
          <van-checkbox
                  :value="item.shopCart.id"
                  v-model="item.isChecked"
                  checked-color="#15C481"
                  @click="chooseChange(item)"
          ></van-checkbox>
          <div class="shopdetail">
            <div class="detailimg">
              <img
                      :src="item.productSpecificationDTO.productSpecificationPicture"
              />
            </div>
            <div class="detailtext">
              <div class="shoptitle van-multi-ellipsis--l2">{{ item.productSpecificationDTO.productPO.productName }}</div>
              <p class="stock">
                仅剩{{item.productSpecificationDTO.productStorage}}件
              </p>
              <div style="line-height: 1px">
                 <span
                         v-for="(item2, index) in Object.values(JSON.parse(item.productSpecificationDTO.productSpecification))"
                         :key="index"
                         style="font-size: 12px"
                 >
                {{item2}}
              </span>
              </div>
              <div class="shoppricenum">
                <p class="shopprice">
                  ¥{{ item.productSpecificationDTO.productPrice }}
                </p>
                <div class="shopnum">
                  <van-stepper
                          integer
                          min=0
                          :max="item.productSpecificationDTO.productStorage"
                          v-model="item.shopCart.goodCount"
                          @change="onChange(item)"
                          :disable-plus="item.shopCart.goodCount === item.productSpecificationDTO.productStorage"
                  />
                </div>
              </div>
            </div>
          </div>
            <template #right>
              <van-button square text="删除" type="danger" class="delete-button" @click="deleteCart(item)"/>
            </template>
          </van-swipe-cell>
        </li>
      </ul>
      <div class="nohaveshop" v-else>
        <van-icon name="shopping-cart-o" />
        <p class="p1">你的购物车空空如也~~</p>
        <p class="p2">快去采购吧!</p>
      </div>
    </div>
    <div class="cartfotter" v-if="goods.length > 0">
      <van-submit-bar button-text="去结算" @submit="onSubmit">
        <van-checkbox v-model="allchecked" checked-color="#15C481" @click="checkAll">全选</van-checkbox>
        <div class="buyprice">
          <p class="p1">合计</p>
          <p class="p2">¥{{ totalprice }}</p>
        </div>
      </van-submit-bar>
    </div>
  </div>
</template>

<script>
    import Vue from 'vue';
    import { Toast } from 'vant';
    Vue.use(Toast)
    export default {
        data() {
            return {
                goods: [],
                allchecked: false,
                selectedData: [],
                // 总价
                totalprice: 0,
                num: 0
            };
        },
        async mounted() {
            await this.listShopCartByUserId()
            this.setSelectedData()
            this.ifCheckAll()
            this.count()
        },
        computed: {},
        methods: {
            async deleteCart(value) {
                console.log('item99', value)
                let param = {
                    productSpecificationId: value.productSpecificationDTO.productSpecificationId,
                    goodCount: 0
                }
                await this.$api.addShopCartRecord(param)
                console.log('删除购物车')
                this.goods = this.goods.filter(item2=> {
                    return item2.shopCart.id !==value.shopCart.id
                })
            },
            async listShopCartByUserId() {
               const {data} = await this.$api.listShopCartByUserId({userId:1})
                this.goods=data.data
                this.goods.forEach(item=> {
                    item.isChecked=true
                })
            },
            // checkbox的change事件
            chooseChange(item) {
                //CheckBox自带改变isChecked
                // item.isChecked=!item.isChecked
                this.setSelectedData()
                this.ifCheckAll()
            },
            // 选中的数据id集合
            setSelectedData () {
              this.selectedData = []
              this.goods.forEach(item=>{
                  if(item.isChecked){
                    this.selectedData.push(item.shopCart.id)
                }
              })
                this.count()
                console.log('this.selectedData', this.selectedData)
            },
            ifCheckAll() {
                if (this.selectedData.length !== this.goods.length) {
                        this.allchecked = false;
                    } else {
                        this.allchecked = true;
                    }
            },
            // 商品数量改变
            async onChange(item) {
                let param = {
                    productSpecificationId: item.productSpecificationDTO.productSpecificationId,
                    goodCount: item.shopCart.goodCount
                }
                let { postdata } = await this.$api.addShopCartRecord(param)
                console.log('添加到购物车', postdata)
                // Toast(item.num);
                if (item.shopCart.goodCount === 0) {
                    this.goods = this.goods.filter(item2=> {
                        return item2.shopCart.id !==item.shopCart.id
                    })
                }
                // this.count();
            },
            // 计算价格
            count: function() {
                console.log('计算价格')
                let totalPrice = 0; //临时总价
                console.log('this.good', this.goods)
                this.goods.forEach(function(val) {
                    if (val.isChecked) {
                        // console.log(136, val.shopCart.goodCount, val.productSpecificationDTO.productPrice)
                        totalPrice += val.shopCart.goodCount * val.productSpecificationDTO.productPrice//累计总价
                        totalPrice=Number(totalPrice.toFixed(2))
                        // console.log('totalPrice', Number(totalPrice.toFixed(2)))
                    }
                });
                this.totalprice = totalPrice;
            },
            // 全选按钮
            checkAll() {
                if (this.selectedData.length!==this.goods.length){
                    this.allchecked = true
                    this.selectedData=[]
                    this.goods.forEach(item=>{
                        item.isChecked = true
                        this.selectedData.push(item.shopCart.id)
                    })
                } else {
                    console.log(222)
                    this.allchecked = false
                    this.selectedData=[]
                    this.goods.forEach(item=>{
                        item.isChecked = false
                    })
                }
                this.count()
            },
            // 去结算
            onSubmit() {
                // 选择购买的商品
                let cartgoods = [];
                this.goods.forEach(function(item) {
                    if (item.isChecked) {
                        cartgoods.push({ id:item.id,price:item.price,title:item.title,num:item.num});
                    }
                });
                if (cartgoods.length === 0) {
                    Toast("请选择商品购买");
                } else {
                    let info = JSON.stringify(cartgoods)
                    sessionStorage.setItem('cartgoods',info)
                    this.$router.push("/orderconfirm");
                }
                console.log(cartgoods);
            }
        }
    };
</script>

<style lang="scss" scoped>
  .shopCart {
    width: 100%;
    min-height: 100vh;
    display: flex;
    flex-direction: column;
    background-color: #f6f6f6;
    .cartList {
      width: 100%;
      display: flex;
      flex-direction: column;
      align-items: center;
      margin-top: 16px;
      ul {
        width: 100%;
        display: flex;
        flex-direction: column;
        align-items: center;
        margin-bottom: 100px;
        li {
          width: 100%;
          height: 96px;
          background-color: #fff;
          display: flex;
          flex-direction: row;
          align-items: center;
          margin-bottom: 12px;
          .van-checkbox {
            margin-left: 17px;
            display:inline-block;
            margin-top: 20px;
            ::v-deep .van-checkbox__icon {
              height: 14px;
              line-height: 14px;
              .van-icon {
                width: 14px;
                height: 14px;
                font-size: 12px;
                border: 1px solid #a5a5a5;
              }
            }
          }
          .shopdetail {
            display: flex;
            flex-direction: row;
            align-items: center;
            margin-left: 65px;
            margin-top: -50px;
            .detailimg {
              width: 64px;
              height: 64px;
              background: rgba(165, 165, 165, 1);
              border-radius: 4px;
              img {
                width: 100%;
                height: 100%;
                border-radius: 4px;
              }
            }
            .detailtext {
              width: 230px;
              height: 50px;
              display: flex;
              flex-direction: column;
              margin-left: 8px;
              margin-top: -14px;
              position: relative;
              .shoptitle {
                width: 180px;
                text-align: justify;
                font-size: 12px;
                color: #212121;
                line-height: 17px;
              }
              .stock {
                width: 180px;
                /*display: flex;*/
                text-align: justify;
                font-size: 10px;
                color: #e9854a;
                line-height: 14px;
              }
              .shoppricenum {
                width: 100%;
                display: flex;
                flex-direction: row;
                align-items: center;
                justify-content: space-between;
                position: absolute;
                bottom: -12px;
                .shopprice {
                  font-size: 12px;
                  color: #15c481;
                  font-weight: 600;
                  margin-top: 28px;
                }
                .shopnum {
                  display: flex;
                  ::v-deep .van-stepper {
                    button {
                      width: 14px;
                      height: 14px;
                      /*border: 1px solid #333333;*/
                      border-radius: 50px;
                      background-color: #fff;
                    }
                    .van-stepper__minus::before {
                      width: 8px;
                    }
                    .van-stepper__plus::before {
                      width: 8px;
                    }
                    .van-stepper__plus::after {
                      height: 8px;
                    }
                    .van-stepper__input {
                      font-size: 12px;
                      color: #333333;
                      background-color: #fff;
                      /*padding: 0px 12px;*/
                    }
                  }
                }
              }
            }
          }
        }
      }
      .nohaveshop {
        display: flex;
        flex-direction: column;
        align-items: center;
        margin-top: 100px;
        .van-icon {
          font-size: 60px;
          color: #666;
        }
        p {
          font-size: 14px;
          color: #999;
        }
        .p1 {
          margin-top: 20px;
        }
      }
    }
    .cartfotter {
      width: 100%;
      height: 60px;
      position: fixed;
      bottom: 0;
      left: 0;
      .van-submit-bar__bar {
        height: 60px;
        font-size: 16px;
        .van-checkbox {
          margin-left: 17px;
          ::v-deep .van-checkbox__icon {
            height: 14px;
            line-height: 14px;
            .van-icon {
              width: 14px;
              height: 14px;
              font-size: 12px;
              border: 1px solid #a5a5a5;
            }
          }
          ::v-deep .van-checkbox__label {
            font-size: 16px;
            color: #212121;
            margin-left: 9px;
          }
        }
        .buyprice {
          flex: 1;
          padding-right: 8px;
          text-align: right;
          display: flex;
          flex-direction: column;
          .p1 {
            font-size: 10px;
            color: #001410;
          }
          .p2 {
            font-size: 12px;
            color: #15c481;
            margin-top: 4px;
          }
        }
        .van-button--danger {
          width: 130px;
          height: 60px;
          /*background: rgba(21, 196, 129, 1);*/
          border: none;
          font-size: 16px;
          color: #ffffff;
        }
      }
    }
    .van-submit-bar {
      bottom: 50px;
    }
  }
  .van-swipe-cell__right {
    background: #fff;
  }
  .delete-button {
    height: 100%;
  }
</style>
