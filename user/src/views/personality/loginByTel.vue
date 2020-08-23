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
              name="手机号"
              label="手机号"
              placeholder="手机号"
              type="number"
              :rules="[{ validator, message: '请输入正确内容' }]"
      />
      <van-field
              v-model="sms"
              center
              clearable
              type="number"
              label="短信验证码"
              placeholder="请输入短信验证码"
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
    export default {
        name: 'LoginByTel',
        data() {
            return {
                tel: '',
                sms: '',
                flag: false,
                count: '',
                timer: null,
            }
        },
        methods: {
            // 校验函数返回 true 表示校验通过，false 表示不通过
            validator(val) {
                return /^1[3456789]\d{9}$/.test(val)
            },
            onSubmit(values) {
                console.log('submit', values);
            },
            onClickLeft() {
                this.$router.back()
            },
            changeFlag() {
                if (this.validator(this.tel)) {
                    // 验证码倒计时
                    const TIME_COUNT = 5;
                    if (!this.timer) {
                        this.count = TIME_COUNT;
                        this.flag = true;
                        this.timer = setInterval(() => {
                            if (this.count > 1 && this.count <= TIME_COUNT) {
                                this.count--;
                            } else {
                                this.flag = false;
                                clearInterval(this.timer);
                                this.timer = null;
                            }
                        }, 1000)
                    }
                } else {
                    console.log('weitg')
                }
            }
        }
    }
</script>

<style scoped>


</style>
