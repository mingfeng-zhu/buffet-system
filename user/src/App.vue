<template>
  <div id="app">
    <router-view></router-view>
  </div>
</template>

<script>

    export default {
        name: 'App',
        data() {
            return {
                // 购物车总数
                num: 1
            }
        },
        mounted() {
            this.setNum()
        },
        methods: {
           // 获取购物车中的数量将数量存到内存
            setNum() {
                window.sessionStorage.setItem('num', this.num)
            }
        }
    }
</script>

<style>
@import './assets/css/app.scss'

</style>
