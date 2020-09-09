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
    <van-dialog v-model="show" title="请确认当前密码" show-cancel-button @confirm="confirm" :beforeClose="resetPassword">
      <van-field
              v-model="password"
              type="password"
              placeholder="请输入当前密码"
              :rules="[{ required: true, message: '请输入当前密码' }]"
      />
    </van-dialog>
    <van-button type="primary" size="large" color="linear-gradient(to right, #ff6034, #ee0a24)" @click="changePassword">修改密码</van-button>
    <van-button type="primary" size="large" color="linear-gradient(to right, #ff6034, #ee0a24)" @click="changeInfo">修改个人信息</van-button>
  </div>
</template>

<script>
    import {Toast} from "vant";
    import md5 from 'js-md5'
    export default {
        name: "personInfo",
        data(){
            return{
                name:'',
                fileList:[{ url: '' }],
                show:false,
                // 输入的原密码
                password:'',
                // 用户的密码
                userPassword:'',
            }
        },
        mounted() {
            this.getUserInfo()
        },
        methods: {
            async getUserInfo() {
                let { data } = await this.$api.getUserInfo()
                this.name = data.data.userName
                this.userPassword = data.data.userPassword
                if (data.data.userImg) {
                    this.fileList[0].url = 'http://121.199.49.199:8082'+data.data.userImg
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
            },
            changePassword() {
                this.show = true
            },
            confirm() {
                if (md5(this.password) === this.userPassword){
                    this.$router.push('/changePassword')
                } else {
                    Toast('输入的当前密码错误')
                    this.password = ''
                }
            },
            resetPassword(action, done) {
                this.password = ''
                done()
            }
        }
    }
</script>

<style scoped>

</style>
