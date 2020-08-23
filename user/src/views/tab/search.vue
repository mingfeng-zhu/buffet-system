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
            </van-cell-group>
            <div style="float: left;margin-left: 20px">数量<van-stepper v-model="num" style="float:left;margin-left: 40px" :disable-plus="num===item.productStorage"/></div>
            <van-button round type="danger" @click="onAddCartClicked">加入购物车</van-button>
          </van-popup>
        </template>
      </van-card>
    </van-list>
  </div>
</template>
<script>
    import Vue from 'vue';
    import {Lazyload, Toast} from 'vant';
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
                num:1
            }
        },
        mounted() {
            this.value = this.$route.query.value
        },
        methods:{
            selectSpecification(item) {
                console.log('item', item)
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
            },
            load_more_items: function() {
                this.onSearch();
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
            async onSearch() {
                let { data } = await this.$api.getProductListByProductName({productName:this.value})
                this.list = data.data.list
                console.log(1, this.list)
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
        }
    }
</script>
