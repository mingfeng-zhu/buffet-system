<template>
  <div>
  <van-nav-bar
          title="修改密码"
          left-arrow
          @click-left="onClickLeft"
  />
  <van-field
          v-model="password"
          label="密码"
          type="password"
          placeholder="请输入密码"
          :rules="[{ required: true, message: '请输入密码' }]"
  />
    <van-button :disabled="!Boolean(password)" type="primary" size="large" color="linear-gradient(to right, #ff6034, #ee0a24)" @click="changePassword">修改密码</van-button>
  </div>
</template>
<script>
    import md5 from 'js-md5'
    import {Toast} from 'vant';
    export default {
        name: "changepassword",
        data(){
            return{
                password:'',
            }
        },
        mounted() {

        },
        methods: {
            onClickLeft() {
                this.$router.back()
            },
           async changePassword() {
                let param = {
                    userPassword: md5(this.password),
                }
                await this.$api.updateUser(param)
               Toast('修改密码成功')
               this.$router.push('/')
               let user = JSON.parse(localStorage.getItem('userPo'))
               user.userPassword = this.password
               localStorage.setItem('userPo', JSON.stringify(user))
            }
        }
    }
</script>
