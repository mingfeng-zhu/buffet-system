<template>
  <div>
    <van-search
            v-model="value"
            show-action
            placeholder="请输入搜索关键词"
            @search="onSearch"
    >
      <template #action>
        <van-button round type="danger" size="small" @click="onSearch">搜索</van-button>
      </template>
    </van-search>
    <van-list
            v-model="loading"
            :finished="finished"
            @load="load_more_items"
            finished-text="没有更多了"
    >
      <!--          :value="item.created_at"-->
      <van-card
              v-for="item in dataList"
              :key="item.productId"
              :price="item.minPrice.toFixed(2)"
              :desc="item.productDesc"
              :title="item.productName"
              :thumb="item.productPicturePath"
      >
        <template #footer>
          <div v-if="item.specificationNumber === 1" class="specification">
            <svg class="icon" aria-hidden="true" @click="sub(item)" v-if="item.productNumOfCart>0">
              <use xlink:href="#iconsub"></use>
            </svg>
            <span v-if="!item.productNumOfCart">0</span>
            <span v-else>{{item.productNumOfCart}}</span>
            <svg class="icon" aria-hidden="true" @click="add(item)">
              <use xlink:href="#iconadd"></use>
            </svg>
          </div>
          <div v-if="item.specificationNumber > 1" class="specification">
            <van-button round type="info" size="mini" @click="selectSpecification(item)">选规格</van-button>
          </div>
          <van-popup v-model="specificationShow" position="bottom" :style="{ height: '60%' }"  closeable >
            <van-cell-group>
              <van-cell>
                <template #title>
                  <van-image style="float:left;margin-left: 25px" width="100px" height="100px"  :src="image" />
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
                    <van-button size="small" v-if ="!item.selected||item2.productAttributeValue!==item.selected" @click="select(item.productAttributeId,item.productAttributeName, item2)">{{item2.productAttributeValue}}</van-button>
                    <van-button size="small" style="background: pink" v-if ="item.selected&&item2.productAttributeValue===item.selected" @click="select(item.productAttributeId,item.productAttributeName, item2)">{{item2.productAttributeValue}}</van-button>
                  </p>
                </template>
              </van-cell>
            </van-cell-group>
            <div>
              <van-stepper v-model="num" style="float: left;margin-left: 20px;margin-top: 14px" :disable-plus="num===item.productStorage"/>
              <van-button round type="danger" @click="onAddCartClicked(productId)" style="margin-top: 10px;margin-right:20px;float:right;">加入购物车</van-button>
            </div>
          </van-popup>
        </template>
      </van-card>
    </van-list>
  </div>
</template>
<script>
    import Vue from 'vue';
    import {Dialog, Lazyload, Toast} from 'vant';
    Vue.use(Lazyload);
    export default {
        name: 'search',
        data() {
            return {
                value: '',
                list: [],
                dataList: [],
                finished: false,
                loading: false,
                // 一页条数
                pageSize: 10,
                // 第几页
                pageNum: 1,
                specificationShow:false,
                price:'',
                storage:'',
                attributeList:[],
                image:'',
                selectColor:'#12212f8c',
                selectList:[],
                // 根据商品分类id获取列表
                id:0,
                selectall:false,
                num:1,
                productId:'',
                user:''
            }
        },
        mounted() {
            this.dataList = []
            this.value = this.$route.query.value
            this.user = JSON.parse(localStorage.getItem('userPo'))
            // this.load_more_items()
        },
        methods:{
            selectSpecification(item) {
                this.productId = item.productId
                this.specificationShow = true
                this.getProductAttributeList(item.productId)
                this.price = item.minPrice
                this.storage = item.productStorage
                this.image = item.productPicturePath
            },
            async getProductAttributeList(id) {
                // 商品id
                this.specificationShow=true
                let { data } = await this.$api.getProductAttributeListByProductId(id)
                this.attributeList=data.data
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
                    await this.$api.addShopCartRecord(param)
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
            load_more_items: async function() {
                   let {data} = await this.$api.getProductListByProductName({productName:this.value,pageSize:this.pageSize, pageNum:1})
                    let total = data.data.total
                    this.dataList = []
                    for (let i=1;i<= Math.ceil(total / 10);i++) {
                        let { data } = await this.$api.getProductListByProductName({productName:this.value,pageSize:this.pageSize, pageNum:i})
                        this.list = data.data.list
                        this.dataList = this.dataList.concat(this.list)
                    }
                    this.finished = true
                    this.loading = false
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
               await this.$api.addShopCartRecord(param)
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
                       await this.$api.addShopCartRecord(param)
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
            async onSearch() {
                this.load_more_items()
                // let { data } = await this.$api.getProductListByProductName({productName:this.value})
                // if (data.data) {
                //     let { data } = await this.$api.getProductListByProductName({productName:this.value})
                //     let total = data.data?data.data.total:0
                //     this.dataList = []
                //     for (let i=1;i<= Math.ceil(total / 10);i++) {
                //         let { data } = await this.$api.getProductListByProductName({productName:this.value,pageSize:10,pageNum:i})
                //         this.list = data.data.list
                //         this.dataList = this.dataList.concat(this.list)
                //     }
                //     this.finished = true
                //     this.loading = false
                // } else {
                //         this.dataList = []
                //         this.list = []
                //         this.finished = true
                //     }
            }
        }
    }
</script>
<style scoped>
  .icon {
    /*height: 1.2em;*/
    color: #E62F2D;
  }
  .specification{
    position: absolute;
    right: 20px;
    bottom: 8px;
  }
  /deep/.van-cell__title {
    width: 105px;
  }
</style>
