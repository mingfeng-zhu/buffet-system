<template>
  <div>
    <van-nav-bar
            title="个人信息"
            left-arrow
            @click-left="onClickLeft"
    />
    <van-cell center title="头像">
      <template #right-icon>
        <van-uploader v-model="fileList" max-count="1" :deletable=false />
<!--        <van-icon name="arrow" />-->
      </template>
    </van-cell>
    <van-cell center title="昵称" :value = "name">
    </van-cell>
    <van-button type="primary" size="large" color="linear-gradient(to right, #ff6034, #ee0a24)" @click="changeInfo">修改个人信息</van-button>
  </div>
</template>

<script>
    export default {
        name: "personInfo",
        data(){
            return{
                name:'',
                fileList:[{ url: '' }]
            }
        },
        mounted() {
            this.getUserInfo()
        },
        methods: {
            async getUserInfo() {
                let { data } = await this.$api.getUserInfo()
                this.name = data.data.userName
                if (data.data.userImg) {
                    this.fileList[0].url = data.data.userImg
                }
                else {
                    this.fileList[0].url = 'https://img.yzcdn.cn/vant/cat.jpeg'
                }
            },
            onClickLeft() {
                this.$router.back()
            },
            changeInfo() {
                this.$router.push('/changeInfo')
            }
        }
    }
</script>

<style scoped>

</style>
