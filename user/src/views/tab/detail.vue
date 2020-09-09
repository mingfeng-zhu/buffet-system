<template>
  <div>
    <van-image width="100%"  :src="detail.productPicturePath" />
    <van-card>
      <template #title>
        <h2>{{detail.productName}}</h2>
      </template>
      <template #desc>
        <span style="display: block">商品描述：{{detail.productDesc}}</span>
      </template>
      <template #price>
        <span v-if="detail.salesVolume!==null" style="display: block">销量&nbsp;{{detail.salesVolume}}</span>
        <span v-else style="display: block">销量&nbsp;0</span>
<!--        <span class="red">￥<span class="price">{{detail.minPrice}}&nbsp;</span>起</span>-->
<!--        <span class="red">￥<span class="price">{{detail.minPrice}}&nbsp;</span>起</span>-->
      </template>
      <template #num>
        <div class="num">
        <span class="red">￥<span class="price">{{detail.minPrice}}&nbsp;</span>起</span>
        <div v-if="detail.specificationNumber === 1" class="specification">
          <svg class="icon" aria-hidden="true" @click="sub(detail)" v-if="detail.productNumOfCart>0">
            <use xlink:href="#iconsub"></use>
          </svg>
          <span v-if="!detail.productNumOfCart">0</span>
          <span v-else style="display: inline-block;min-width: 18px;text-align: center;">{{detail.productNumOfCart}}</span>
          <svg class="icon" aria-hidden="true" @click="add(detail)">
            <use xlink:href="#iconadd"></use>
          </svg>
        </div>
        <div v-if="detail.specificationNumber > 1" class="specification">
          <van-button round type="info" size="mini" @click="selectSpecification(detail)">选规格</van-button>
        </div>
        <van-popup v-model="specificationShow" position="bottom" :style="{ height: '60%' }"  closeable >
          <van-cell-group>
            <van-cell>
              <template #title>
                <van-image style="float:left;margin-left: 25px" width="100px" height="100px"  :src="image"/>
              </template>
              <template #default>
                <p style="float: left">￥{{price}}</p><br>
                <p style="float:left;">剩余{{storage}}件</p><br>
                <div style="float:left;width: 100%">
                      <span v-for="item in attributeList" :key="item.productAttributeId">
                      <span style="float: left" v-if="!item.selected">请选择</span>
                      <span  style="float: left" v-if="!item.selected">{{item.productAttributeName}}</span>
                      <span style="float: left" v-if="item.selected">已选择</span>
                      <span  style="float: left" v-if="item.selected">{{item.selected}}</span>
                      </span>
                </div>
              </template>
            </van-cell>
            <van-cell  v-for="item in attributeList" :key="item.productAttributeId">
              <template #default>
                <p style="float: left">{{item.productAttributeName}}</p><br>
                <p style="float:left;" v-for="item2 in item.productAttributeValueDTOList" :key="item2.productAttributeValueId">
                  <van-button size="small" v-if ="!item.selected||item2.productAttributeValue!==item.selected" @click="select(item.productId,item.productAttributeName, item2)">{{item2.productAttributeValue}}</van-button>
                  <van-button size="small" style="background: pink" v-if ="item.selected&&item2.productAttributeValue===item.selected" @click="select(item.productId,item.productAttributeName, item2)">{{item2.productAttributeValue}}</van-button>
                </p>
              </template>
            </van-cell>
          </van-cell-group>
          <div>
            <van-stepper
                    v-model="num"
                    style="float: left;margin-left: 20px;margin-top: 14px"
                    :disable-plus="num===storage || !selectall"
                    integer
                    :max="storage"
            />
            <van-button
                    round
                    type="danger"
                    :disabled ="!selectall"
                    @click="onAddCartClicked(productId)"
                    style="margin-top: 10px;margin-right:20px;float:right;">加入购物车</van-button>
          </div>
        </van-popup>
        </div>
      </template>
    </van-card>
    <div class="comment">
      <h2 style="font-size: 20px;margin-top: 10px;margin-left: 10px;font-weight: 500;margin-bottom: 10px;" v-if="commentList.length>0">用户评价</h2>
      <div v-for="(item, index) in commentList" :key="index">
        <div style="display:inline-block;font-size: 16px;font-weight: 500;width: 100px;margin-left: 20px;position: relative">
          <van-image
                  v-if="item.userPO.userImg"
                  style="position: absolute;top: -20px;left: 11px;"
                  round
                  width="50px"
                  height="50px"
                  :src="'http://121.199.49.199:8082'+item.userPO.userImg"
          />
          <van-image
                  v-else
                  round
                  style="position: absolute;top: -20px;left: 11px;"
                  width="50px"
                  height="50px"
                  src="https://img.yzcdn.cn/vant/cat.jpeg"
          />
          <span style="position: absolute;left: 12px;top: 34px;width: 50px;text-align: center;">{{item.userPO.userName}}</span>
        </div>
        <van-rate v-model="item.commentStar" readonly	/>
        <span style="font-size: 12px;margin-left: 122px;display: block">{{item.changeTime}}</span>
        <textarea autosize v-model="item.commentDescription" class="textarea" ></textarea>
<!--        <van-field-->
<!--                style="margin-left: 106px;padding-right: 58px;"-->
<!--                readonly-->
<!--                v-model="item.commentDescription"-->
<!--                autosize-->
<!--                type="textarea"-->
<!--        >-->
<!--        <template #label>-->
<!--          <span style="font-size: 16px;font-weight: 500;">评价</span>-->
<!--        </template>-->
<!--        </van-field>-->
      </div>
    </div>
  </div>
</template>
<script>
    import {Dialog, Toast} from "vant";

    export default {
        name: 'detail',
        data() {
            return {
                id:0,
                detail:{},
                commentList:{},
                specificationShow:false,
                // 规格列表
                price:'',
                storage:'',
                attributeList:[],
                image:'',
                selectColor:'#12212f8c',
                selectList:[],
                selectall:false,
                num:1,
            }
        },
        mounted() {
            this.id = this.$route.query.id
            this.getDetail()
            this.user = JSON.parse(localStorage.getItem('userPo'))
        },
        methods: {
           async getDetail() {
                let { data } = await this.$api.getProductDetailAndCommentList(this.id)
                this.detail = data.data[0]
               this.commentList =  this.detail.commentList
            },
            async sub(item) {
                if (item.productNumOfCart>0){
                    item.productNumOfCart--
                    let { data } = await this.$api.getSpecificationByProductIdAndSpecification({productId:item.productId,productSpecification:{}})
                    let productSpecificationId = data.data.productSpecificationId
                    let param = {
                        productSpecificationId: productSpecificationId,
                        goodCount: item.productNumOfCart
                    }
                    let { postdata } = await this.$api.addShopCartRecord(param)
                }
            },
            async add(item) {
                if (this.user) {
                    if (item.productNumOfCart<item.productStorage){
                        item.productNumOfCart++
                        let { data } = await this.$api.getSpecificationByProductIdAndSpecification({productId:item.productId,productSpecification:{}})
                        let productSpecificationId = data.data.productSpecificationId
                        let param = {
                            productSpecificationId: productSpecificationId,
                            goodCount: item.productNumOfCart
                        }
                        let { postdata } = await this.$api.addShopCartRecord(param)
                    } else {
                        Toast(`该商品仅剩${item.productStorage}件`);
                    }
                } else {
                    Dialog.confirm({
                        title: '尚未登录',
                        message: '跳转到登录页？',
                    })
                        .then(() => {
                            this.$router.push('/login')
                        })
                        .catch(() => {

                        });
                }
            },
            // 选规格
            selectSpecification(item) {
                this.productId = item.productId
                this.specificationShow = true
                this.getProductAttributeList(item.productId)
                this.price = item.minPrice
                this.storage = item.productStorage
                this.image = item.productPicturePath
            },
            // 根据id获取规格
            async getProductAttributeList(id) {
                // 商品id
                this.specificationShow=true
                let { data } = await this.$api.getProductAttributeListByProductId(id)
                this.attributeList=data.data
            },
            async select(id, name, item) {
                this.attributeList.forEach(item2 => {
                    if (item2.productAttributeName === name) {
                        this.$set(item2,'selected',item.productAttributeValue)
                    }
                    this.selectList.push(item.productAttributeValue)
                })
                this.selectall = this.attributeList.every(item=>item.selected)
                if (this.selectall) {
                    this.queryspe = {}
                    this.attributeList.forEach(item2 => {
                        this.queryspe[item2.productAttributeName]=item2.selected
                    })
                    let { data } = await this.$api.getSpecificationByProductIdAndSpecification({productId:id,productSpecification:this.queryspe})
                    if (data.data.numberOfCart) {
                        this.num = data.data.numberOfCart
                    } else {
                        this.num = 1
                    }
                    this.price=data.data.productPrice
                    this.storage = data.data.productStorage
                    this.image = data.data.productSpecificationPicture
                    this.productSpecification = data.data.productSpecification
                }
            },
            async onAddCartClicked(item) {
                if (this.user) {
                    let {data} = await this.$api.getSpecificationByProductIdAndSpecification({
                        productId: item,
                        productSpecification: this.productSpecification
                    })
                    let productSpecificationId = data.data.productSpecificationId
                    let param = {
                        productSpecificationId: productSpecificationId,
                        goodCount: this.num
                    }
                    let {postdata} = await this.$api.addShopCartRecord(param)
                    this.$router.push('/cart')
                }
                else {
                    Dialog.confirm({
                        title: '尚未登录',
                        message: '跳转到登录页？',
                    })
                        .then(() => {
                            this.$router.push('/login')
                        })
                        .catch(() => {

                        });
                }
            },
        }
    }
</script>
<style scoped>
/deep/.van-card__content h2{
  font-size: 20px;
  font-weight: 700;
}
  .price {
    font-size: 20px;
    font-weight: 700;
  }
  .red {
    color: red;
  }
  .comment {
    padding-bottom: 35px;
  }
  .textarea {
    border: none;
    font-size: 14px;
    margin-left: 125px;
    width: 62%;
  }
  .num {
    position: absolute;
    right: 19px;
    bottom: 9px;
  }
.icon {
  /*height: 1.2em;*/
  color: #E62F2D;
}
.specification{
  padding-left: 20px;
  padding-top: 15px;
  font-size: 16px;
}
/deep/.van-cell__title {
  width: 105px;
}
</style>
