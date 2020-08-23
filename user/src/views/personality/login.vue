<template>
  <div>
    <van-nav-bar
            title="登录"
            left-arrow
            @click-left="onClickLeft"
    />
    <van-form @submit="onSubmit">
      <van-field
              v-model="tel"
              name="手机号"
              label="手机号"
              placeholder="手机号"
              :rules="[{ required: true, message: '请填写手机号' }]"
      />
      <van-field
              v-model="password"
              :type="flag === true?'password':'text'"
              name="密码"
              label="密码"
              clearable
              autocomplete
              placeholder="密码"
              :right-icon="flag === true?'eye-o':'closed-eye'"
              @click-right-icon="changeType"
              :rules="[{ required: true, message: '请填写密码' }]"
      />
      <div style="margin: 16px;">
        <van-button round block type="info" native-type="submit">
          登录
        </van-button>
      </div>
    </van-form>
    <div class="footer">
      <van-tabbar v-model="action">
        <van-tabbar-item name="forget" @click="show = true" >忘记密码</van-tabbar-item>
        <van-tabbar-item name="register" @click="register">用户注册</van-tabbar-item>
      </van-tabbar>
      <van-action-sheet v-model="show" :actions="actions" @select="onSelect"  cancel-text="取消" />
    </div>
  </div>
</template>

<script>
    export default {
        name: 'Login',
        data() {
            return {
                username: '',
                tel:'',
                password: '',
                flag: true,
                action:'',
                // 操作面板
                show:false,
                actions: [{ name: '找回密码' }, { name: '短信验证通过' }],
            }
        },
        methods: {
            async onSubmit(values) {
                console.log('submit', values);
                this.params = {}
                // this.params.userImg=this.uploadImg
                // this.params.userName=this.username
                this.params.userPassword=this.password
                // this.params.userSex=Number(this.sex)
                this.params.userTel=this.tel
                // this.params.code=this.verifycode
                console.log('this.params', this.params)
                let { data } = await this.$api.login(this.params)
                console.log(69, data.data.token)
                localStorage.setItem('token', data.data.token)
            },
            onClickLeft() {
                this.$router.back()
            },
            changeType() {
                this.flag = !this.flag
            },
            onSelect(item) {
                console.log('item', item)
                // 默认情况下点击选项时不会自动收起
                // 可以通过 close-on-click-action 属性开启自动收起
                this.show = false
                if (item.name === '找回密码'){
                    console.log('找回密码')
                } else {
                    console.log('短信验证通过')
                    this.$router.push('/loginByTel')
                }
            },
            register() {
                console.log('注册')
                this.$router.push('/register')
            }
        }
    }
</script>

<style scoped>


</style>
