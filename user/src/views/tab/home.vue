<template>
  <div>
    <van-search
            v-model="searchValue"
            shape="round"
            background="#E62F2D"
            placeholder="请输入搜索关键词"
            @search="onSearch"
    >
    </van-search>
<!--    轮播-->
    <div class="swipe">
      <van-swipe :autoplay="3000" width="300px" height="300px">
        <van-swipe-item v-for="(image, index) in images" :key="index">
          <img v-lazy="image.img" @click="showDetail(image.id)" />
        </van-swipe-item>
      </van-swipe>
    </div>
<!--    树选择-->
    <van-tree-select :items="items" :main-active-index.sync="active">
      <template #content>
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
                  @click-thumb = "showDetail(item.productId)"
          >
            <template #footer>
              <div v-if="item.specificationNumber === 1" class="specification">
                <svg class="icon" aria-hidden="true" @click="sub(item)" v-if="item.productNumOfCart>0">
                  <use xlink:href="#iconsub"></use>
                </svg>
                <span v-if="!item.productNumOfCart">0</span>
                <span v-else style=" display: inline-block;min-width: 18px;text-align: center;">{{item.productNumOfCart}}</span>
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
            </template>
          </van-card>
        </van-list>
      </template>
    </van-tree-select>
  </div>
</template>
<script>
    import Vue from 'vue';
    import { Lazyload } from 'vant';
    import { Toast } from 'vant';
    Vue.use(Lazyload);
    Vue.use(Toast)
    import { Dialog } from 'vant';
    export default {
        name: 'home',
        data() {
            return {
                productId:'',
                images: [],
                searchValue:'',
                // 懒加载内容
                list: [],
                dataList: [],
                finished: false,
                loading: false,
                // 一页条数
                pageSize: 10,
                // 第几页
                pageNum: 1,
                // 左侧
                active: 0,
                items: [],
                // 规格数据
                // 规格框
                specificationShow:false,
                // 规格列表
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
                user:''
            }
        },
        watch:{
            // 根据分类获取列表
            active: {
                handler: function (newValue) {
                    this.id=this.items[newValue].id
                    // this.fetch_items
                    this.load_more_items()
                }
            }
        },
        created() {
            // 获取分类
            this.getProductCategoryList()
            this.user = JSON.parse(localStorage.getItem('userPo'))
        },
        methods: {
            async showDetail(id) {
                this.$router.push({name: 'detail', query: {id: id}})
            },
            onSearch() {
                this.$router.push({name: 'search', query: {value: this.searchValue}})
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
            // 懒加载
            load_more_items: async function() {
                if (this.active===0){
                    let {data} = await this.$api.getAllProductList({pageSize:this.pageSize, pageNum:1})
                    this.images=[]
                    for (let i=0;i<4;i++) {
                        this.images.push({
                            img:data.data.list[i].productPicturePath,
                            id:data.data.list[i].productId
                        })
                    }
                    let total = data.data.total
                    this.dataList = []
                    for (let i=1;i<= Math.ceil(total / 10);i++) {
                        let { data } = await this.$api.getAllProductList({pageSize:this.pageSize, pageNum:i})
                        this.list = data.data.list
                        this.dataList = this.dataList.concat(this.list)
                    }
                    this.finished = true
                    this.loading = false
                }
                else {
                    let { data } = await this.$api.getProductListByProductCategoryId({productCategoryId:this.id,pageSize:this.pageSize, pageNum:1})
                    let total = data.data?data.data.total:0
                    this.dataList = []
                    for (let i=1;i<= Math.ceil(total / 10);i++) {
                        let { data } = await this.$api.getProductListByProductCategoryId({productCategoryId:this.id,pageSize:this.pageSize, pageNum:i})
                        this.list = data.data.list
                        this.dataList = this.dataList.concat(this.list)
                    }
                    this.finished = true
                    this.loading = false
                }
            },
            // 获取分类
            async getProductCategoryList() {
                let { data } = await this.$api.getProductCategoryList()
                data.data.forEach(item => {
                    this.items.push({
                        id:item.productCategoryId,
                        text:item.categoryName
                    })
                })
                this.items.unshift({
                    id: 0,
                    text: '全部'
                })
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
    font-size: 14px;
  }
  /deep/.van-cell__title {
    width: 105px;
  }
</style>
