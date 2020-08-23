<template>
  <div>
    <van-sticky>
    <van-nav-bar
            v-if="headershow"
            style="background: #E62F2D"
    >
      <template #left>
        <van-icon name="location" color="#fff"/>
        <span style="color: #fff">商家名称</span>
      </template>
      <template #right>
        <van-icon name="manager" @click="person()" color="#fff"/>
      </template>
    </van-nav-bar>
<!--    <van-popup-->
<!--         v-model="show"-->
<!--         position="right"-->
<!--         :style="{ height: '100%',width: '100%' }"-->
<!--         closeable-->
<!--    >-->
<!--    </van-popup>-->
    </van-sticky>
    <router-view></router-view>
    <div class="footer">
      <van-tabbar v-model="active" placeholder="true">
        <van-tabbar-item icon="home-o" @click="$router.push('/')">首页</van-tabbar-item>
<!--        <van-tabbar-item icon="apps-o">分类</van-tabbar-item>-->
        <van-tabbar-item icon="shopping-cart-o" :info="info" @click="$router.push('/cart')">购物车</van-tabbar-item>
        <van-tabbar-item icon="user-o" @click="person()">个人中心</van-tabbar-item>
      </van-tabbar>
    </div>
  </div>
</template>
<script>
    export default {
        name: 'layOut',
        data() {
            return {
                // show: false,
                active: '首页',
                info: null,
                headershow: true
            }
        },
        mounted() {
            this.getinfo()
            if (this.$route.name === 'person') {
               this.headershow =false
            }
        },
        methods: {
            // 获取购物车总数
            getinfo() {
                if (window.sessionStorage.getItem('num') == 0) {
                    this.info = null
                } else {
                    this.info = window.sessionStorage.getItem('num')
                }
            },
            // showPopup() {
                // if (!this.username){
                //   this.$router.push('/login')
                // } else {
                //     this.show = true;
                // }
            // },
            person() {
                this.$router.push('/login')
                // window.sessionStorage.setItem('token', 123)
                // let token = window.sessionStorage.getItem('token')
                // if (token) {
                //     this.$router.push('/person')
                // } else {
                //     this.$router.push('/login')
                // }
            }
        }
    }
</script>
<style scoped>
  .footer {
    position: fixed;
  }
</style>
