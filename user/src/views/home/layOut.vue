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
    </van-sticky>
    <router-view></router-view>
    <div class="footer">
      <van-tabbar v-model="active" placeholder="true">
        <van-tabbar-item icon="home-o" @click="$router.push('/')">首页</van-tabbar-item>
        <van-tabbar-item icon="shopping-cart-o" :info="info" @click="toCart()">购物车</van-tabbar-item>
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
            // 获取用户信息
            this.getinfo()
            if (this.$route.name === 'person') {
               this.headershow =false
            }
        },
        methods: {
            // 跳转到购物车
            toCart() {
                this.user = JSON.parse(localStorage.getItem('userPo'))
                if (this.user) {
                    this.$router.push('/cart')
                } else {
                    this.$router.push('/login')
                }
            },
            // 获取购物车总数
            getinfo() {
                // if (window.sessionStorage.getItem('num') == 0) {
                //     this.info = null
                // } else {
                //     this.info = window.sessionStorage.getItem('num')
                // }
            },
            // 跳转到个人中心
            person() {
                this.user = JSON.parse(localStorage.getItem('userPo'))
                if (this.user) {
                    this.$router.push('/person')
                } else {
                    this.$router.push('/login')
                }
            }
        }
    }
</script>
<style scoped>
  .footer {
    position: fixed;
  }
</style>
