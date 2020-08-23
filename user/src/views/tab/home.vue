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
          <img v-lazy="image" />
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
          >
            <template #footer>
              <div v-if="item.specificationNumber === 1" class="specification">
                <svg class="icon" aria-hidden="true" @click="sub(item)" v-if="item.productNumOfCart>0">
                  <use xlink:href="#iconsub"></use>
                </svg>
                <span>{{item.productNumOfCart}}</span>
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
<!--                  <van-cell>-->
<!--                    <template #title>-->
<!--                      <div>数量</div>-->
<!--                    </template>-->
<!--                    <template #default>-->
<!--                      <van-stepper-->
<!--                              integer-->
<!--                              min=0-->
<!--                              v-model="num"-->
<!--                              style="float:left;"-->
<!--                              :disable-plus="num===item.productStorage"/>-->
<!--                    </template>-->
<!--                  </van-cell>-->
<!--                  <van-cell>-->
<!--                    <template #default>-->
<!--                      <van-button round type="danger" style="margin-left: 120px" @click="onAddCartClicked">加入购物车</van-button>-->
<!--                    </template>-->
<!--                  </van-cell>-->
                </van-cell-group>
                <span >数量</span><van-stepper v-model="num" style="display:inline-block;margin-left: 40px" :disable-plus="num===item.productStorage"/>
                <van-button round type="danger" @click="onAddCartClicked" style="display: block;margin-left: 80px">加入购物车</van-button>
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
    export default {
        name: 'home',
        data() {
            return {
                images: [
                    // img1,
                    // 'https://img.yzcdn.cn/vant/apple-1.jpg',
                    // 'https://img.yzcdn.cn/vant/apple-2.jpg',
                ],
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
                num:1
            }
        },
        watch:{
            // 根据分类获取列表
            active: {
                handler: function (newValue) {
                    this.id=this.items[newValue].id
                    this.fetch_items(this.id)
                }
            }
        },
        created() {
            // 获取分类
            this.getProductCategoryList()
            this.fetch_items(this.id)
        },
        methods: {
            onSearch() {
                console.log('sousuo', this.searchValue)
                this.$router.push({name: 'search', query: {value: this.searchValue}})
            },
            sub(item) {
                if (item.productNumOfCart>0){
                    item.productNumOfCart--
                }
            },
            add(item) {
                if (item.productNumOfCart<item.productStorage){
                    item.productNumOfCart++
                } else {
                    Toast(`该商品仅剩${item.productStorage}件`);
                }
            },
            // 懒加载
            load_more_items: function() {
                this.fetch_items();
            },
            // 商品列表
            async fetch_items(){
                console.log('active', this.active)
                if (this.active===0){
                    let { data } = await this.$api.getAllProductList({pageSize:this.pageSize, pageNum:this.pageNum})
                    this.list = data.data.list
                    this.images=[]
                    for (let i=0;i<4;i++) {
                        this.images.push(this.list[i].productPicturePath)
                    }
                    if (this.pageNum > 1) {
                        this.dataList = this.list.concat(this.dataList)
                        // this.dateList = [...this.dateList, ...this.list]
                    } else {
                        this.dataList = this.list
                    }
                    // 如果当前页数 = 总页数，则已经没有数据
                    if (this.pageNum === Math.ceil(data.data.total / 10)) {
                        this.finished = true
                    }
                    // 如果总页数大于当前页码，页码+1
                    if (Math.ceil(data.data.total / 10) > this.pageNum) {
                        this.pageNum++
                    }
                    this.loading = false
                }
                else {
                    this.pageSize = 10
                    this.pageNum=1
                    let { data } = await this.$api.getProductListByProductCategoryId({productCategoryId:this.id,pageSize:this.pageSize, pageNum:this.pageNum})
                    if (data.data) {
                        this.list = data.data.list
                        if (this.pageNum > 1) {
                            this.dataList = this.list.concat(this.dataList)
                            this.dateList = [...this.dateList, ...this.list]
                        } else {
                            this.dataList = this.list
                        }
                        // 如果当前页数 = 总页数，则已经没有数据
                        if (this.pageNum === Math.ceil(data.data.total / 10)) {
                            this.finished = true
                        }
                        // 如果总页数大于当前页码，页码+1
                        if (Math.ceil(data.data.total / 10) > this.pageNum) {
                            this.pageNum++
                        }
                        this.loading = false
                    } else {
                        this.list
                        this.dataList = []
                        this.finished = true
                    }
                }
            },
            // 获取分类
            async getProductCategoryList() {
                let { data } = await this.$api.getProductCategoryList()
                console.log('分类列表')
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
                console.log('3333', data.data)
                // this.attributeList = data.data
                // console.log('规格列表', this.attributeList)
            },
            onAddCartClicked() {
                console.log('加入购物车')
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
                    console.log('this.queryspe', this.queryspe)
                    let { data } = await this.$api.getSpecificationByProductIdAndSpecification({productId:id,productSpecification:this.queryspe})
                    console.log('data273', data.data)
                    this.price=data.data.productPrice
                    this.storage = data.data.productStorage
                    this.image = data.data.productSpecificationPicture
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
  }
  /deep/.van-cell__title {
    width: 105px;
  }
</style>
