<template>
  <div>
    <van-nav-bar
            title="通过验证码登录"
            left-arrow
            @click-left="onClickLeft"
    />
    <van-form @submit="onSubmit">
      <van-field
              v-model="tel"
              name="tel"
              label="手机号"
              placeholder="手机号"
              type="number"
              :rules="[{ validator, message: '请输入正确内容' }]"
      />
      <van-field
              v-model="sms"
              center
              name="code"
              clearable
              type="number"
              label="短信验证码"
              placeholder="请输入短信验证码"
              :rules="[{ required: true, message: '请输入验证码' }]"
      >
        <template #button>
          <van-button size="small" type="info" v-if = "flag === false" @click="changeFlag()">发送验证码</van-button>
          <van-button size="small" type="info" v-if = "flag === true" disabled>验证码已发送{{count}}s</van-button>
        </template>
      </van-field>
      <div style="margin: 16px;">
        <van-button round block type="info" native-type="submit">
          确定
        </van-button>
      </div>
    </van-form>
  </div>
</template>

<script>
    import {Dialog} from "vant";

    export default {
        name: 'LoginByTel',
        data() {
            return {
                tel: '',
                sms: '',
                flag: false,
                count: '',
                timer: null,
                params:{}
            }
        },
        methods: {
            // 校验函数返回 true 表示校验通过，false 表示不通过
            validator(val) {
                return /^1[3456789]\d{9}$/.test(val)
            },
            // 通过手机号登录
            async onSubmit(values) {
                this.params.userTel=values.tel
                this.params.code=values.code
                let { data } = await this.$api.loginByPhoneNumber(this.params)
                this.$router.push('/')
            },
            // 返回上一级
            onClickLeft() {
                this.$router.back()
            },
            // 发送验证码
            changeFlag() {
                // 如果已经输入手机号
                if (this.validator(this.tel)) {
                    Dialog.alert({
                        title: '已发送验证码',
                        message: '已向该手机发送验证码，请注意查收',
                    }).then(async () => {
                        const TIME_COUNT = 5;
                        if (!this.timer) {
                            this.getcodeshow = false
                            this.count = TIME_COUNT;
                            this.timer = setInterval(() => {
                                if (this.count > 1 && this.count <= TIME_COUNT) {
                                    this.count--;
                                } else {
                                    this.getcodeshow = true
                                    clearInterval(this.timer);
                                    this.timer = null;
                                }
                            }, 1000)
                        }
                        this.$api.getCode({userTel: this.tel})
                    })
                } else {
                    // 如果尚未输入手机号
                    Dialog.alert({
                        message: '请先输入手机号',
                    }).then(() => {
                    });
                }
            }
        }
    }
</script>

<style scoped>


</style>
