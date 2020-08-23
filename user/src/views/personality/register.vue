<template>
  <div>
    <van-nav-bar
            title="注册"
            left-arrow
            @click-left="onClickLeft"
    />
    <van-form @submit="onSubmit" ref="form">
      <van-field
              v-model="username"
              name="name"
              label="用户名"
              placeholder="用户名"
              :rules="[
                  { required: true, message: '请填写用户名' },
                  { pattern: patternUser, message: '用户名不超过十位数且不能有特殊字符' }
                  ]"
      />
      <van-field
              name="portrait"
              label="头像"
              :rules="[
                  { required: true, message: '请上传头像' },
                  ]"
      >
        <template #input>
          <van-uploader
                  v-model="portrait"
                  :max-count="1"
                  :after-read="afterRead"
          />
        </template>
      </van-field>
      <van-field label="性别" :rules="[{ required: true, message: '请选择性别' }]">
        <template #input>
          <van-radio-group v-model="sex" direction="horizontal">
            <van-radio name="1">男</van-radio>
            <van-radio name="2">女</van-radio>
          </van-radio-group>
        </template>
      </van-field>
      <van-field
              v-model="tel"
              name="tel"
              label="手机号"
              placeholder="手机号"
              :rules="[
                  { required: true, message: '请填写手机号' },
                  { pattern:patternPhone, message: '手机号格式错误' },
              ]"
      />
      <van-field
              v-model="password"
              :type="flag === true?'password':'text'"
              name="password"
              label="密码"
              clearable
              autocomplete
              placeholder="密码"
              :right-icon="flag === true?'eye-o':'closed-eye'"
              @click-right-icon="changeType"
              :rules="[{ required: true, message: '请填写密码' }]"
      />
      <van-field
              v-model="certainpassword"
              :type="certainflag === true?'password':'text'"
              name="certainpassword"
              label="确定密码"
              clearable
              autocomplete
              placeholder="请再次输入密码"
              :right-icon="certainflag === true?'eye-o':'closed-eye'"
              @click-right-icon="changeCertainType"
              :rules="[
                      { required: true, message: '请再次填写密码' },
                      { validator: validatorCertainPassword, message: '两次密码不一致' }
                  ]"
      />
      <van-field
              v-model="verifycode"
              center
              clearable
              label="验证码"
              placeholder="请输入验证码"
              :rules="[{ required: true, message: '请输入验证码' }]"
      >

        <div slot="button" >
          <van-button round block type="info" @click="getCode()" v-if="getcodeshow">
            获取
          </van-button>
          <van-button round block type="info" disabled v-else>
            获取中{{count}}s
          </van-button>
        </div>
      </van-field>
      <div style="margin: 16px;">
        <van-button round block type="info" native-type="submit">
          注册
        </van-button>
      </div>
    </van-form>
  </div>
</template>

<script>
    import { Dialog } from 'vant'
    // import axios from 'axios'
    import upLoaderImg from "../../api/upLoaderImg"
    export default {
        name: 'Login',
        data() {
            return {
                username: '',
                portrait:[],
                tel:'',
                sex:'',
                password: '',
                certainpassword:'',
                verifycode:'',
                flag: true,
                certainflag:true,
                action:'',
                // 操作面板
                show:false,
                actions: [{ name: '找回密码' }, { name: '短信验证通过' }],
                // 校验规则
                // 手机号校验
                patternPhone:/^1[3456789]\d{9}$/,
                patternUser:/^[\u4e00-\u9fffa-zA-Z0-9]{1,10}$/,
                getcodeshow:true,
                count: '',
                timer:null,
                params:{}
            }
        },
        mounted() {
        },
        methods: {
            getCode() {
                if (this.tel) {
                    Dialog.alert({
                        title: '已发送验证码',
                        message: '已向该手机发送验证码，请注意查收',
                    }).then(async () => {
                        const TIME_COUNT = 5;
                        if (!this.timer) {
                            this.getcodeshow=false
                            this.count = TIME_COUNT;
                            this.timer = setInterval(() => {
                                if (this.count > 1 && this.count <= TIME_COUNT) {
                                    this.count--;
                                } else {
                                    this.getcodeshow=true
                                    clearInterval(this.timer);
                                    this.timer = null;
                                }
                            }, 1000)
                        }
                        this.$api.getCode({userTel:this.tel})
                    });
                } else {
                    Dialog.alert({
                        message: '请先输入手机号',
                    }).then(() => {
                    });
                }
            },
            async afterRead(file) {
                // 此时可以自行将文件上传至服务器
                let fileparam = {picture: file.file}
                console.log('fileparam', fileparam)
                let uploadImg = await upLoaderImg(file.file)//使用上传的方法。file.file
                console.log(uploadImg.data)
                this.uploadImg = uploadImg.data
            },
            async onSubmit(values) {
                console.log('submit', values);
                this.params.userImg=this.uploadImg
                this.params.userName=values.name
                this.params.userPassword=this.password
                this.params.userSex=Number(this.sex)
                this.params.userTel=this.tel
                this.params.code=this.verifycode
                console.log('this.params', this.params)
                let { data } = await this.$api.signUp(this.params)
                console.log(196, data)
            },
            onClickLeft() {
                this.$router.back()
            },
            changeType() {
                this.flag = !this.flag
            },
            changeCertainType() {
              this.certainflag = !this.certainflag
            },
            validatorCertainPassword(val) {
                console.log('val', val)
                if (val === this.password) {
                    return true
                } else {
                    return false
                }
            },
        }
    }
</script>

<style scoped>


</style>
