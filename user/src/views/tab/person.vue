<template>
  <div>
    <van-cell-group class = "namecell">
      <van-cell @click="personInform">
        <template #title>
          <van-image
                  round
                  width="50px"
                  height="50px"
                  :src="img"
          />
          <div class="name">{{name}}</div>
<!--          <div class="yue" >-->
<!--            <van-grid :border =false style="height: 28px;">-->
<!--              <van-grid-item style="height: 20px;">-->
<!--                <span>{{money}}</span>-->
<!--              </van-grid-item>-->
<!--            </van-grid>-->
<!--            <p>余额·充值</p>-->
<!--          </div>-->
        </template>
        <template #right-icon>
          <van-icon name="arrow" style="top:20px"/>
        </template>
      </van-cell>
      <div class="title">我的订单</div>
      <van-grid>
        <van-grid-item icon="balance-o" text="待支付" @click="$router.push('/unpaid')"/>
        <van-grid-item icon="envelop-o" text="待收货" @click="$router.push('/unreceived')"/>
<!--        <van-grid-item icon="photo-o" text="待评价" @click="$router.push('/unevaluated')"/>-->
        <van-grid-item icon="todo-list-o" text="全部订单" @click="$router.push('/allorder')"/>
      </van-grid>
    </van-cell-group>
    <div class="title">服务帮助</div>
    <van-grid>
      <van-grid-item icon="location-o" text="地址管理" @click="$router.push('/address')"/>
    </van-grid>
    <van-button type="primary" v-if="user" size="large" color="linear-gradient(to right, #ff6034, #ee0a24)" @click="loginout">退出登录</van-button>
  </div>
</template>
<script>
    import { Dialog } from 'vant'
    export default {
        name: 'person',
        data() {
            return {
                name: '',
                money: 3000,
                user:{},
                img:''
            }
        },
        mounted() {
            this.user = JSON.parse(localStorage.getItem('userPo'))
            this.name = this.user.userName
            if (this.user.userImg) {
                this.img = 'http://121.199.49.199:8082'+this.user.userImg
            } else {
                this.img = 'https://img.yzcdn.cn/vant/cat.jpeg'
            }
        },
        methods:{
            personInform() {
                this.$router.push('/personInfo')
            },
            loginout() {
                Dialog.confirm({
                    message: '确定退出登录吗',
                })
                    .then(() => {
                        localStorage.removeItem('token')
                        localStorage.removeItem('userPo')
                        this.$router.push('/login')
                    })
                    .catch(() => {
                        // on cancel
                    });
            }
        }
    }
</script>
<style scoped>
  .namecell {
    position: relative;
  }
  .name {
    font-size: 16px;
    font-weight: 600;
    position: absolute;
    top: 26px;
    left: 100px;
  }
  .yue {
    position: absolute;
    top: 8px;
    left: 300px;
  }
  .title {
    font-size: 16px;
    font-weight: 600;
    margin: 10px;
  }
</style>
