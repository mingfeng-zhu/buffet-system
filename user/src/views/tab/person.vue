<template>
  <div>
    <van-cell-group class = "namecell">
      <van-cell>
        <template #title>
          <van-image
                  round
                  width="50px"
                  height="50px"
                  src="https://img.yzcdn.cn/vant/cat.jpeg"
          />
          <div class="name">{{name}}</div>
          <div class="yue" >
            <van-grid :border =false style="height: 28px;">
              <van-grid-item style="height: 20px;">
                <span>{{money}}</span>
              </van-grid-item>
            </van-grid>
            <p>余额·充值</p>
          </div>
        </template>
      </van-cell>
      <div class="title">我的订单</div>
      <van-grid>
        <van-grid-item icon="balance-o" text="待支付" />
        <van-grid-item icon="envelop-o" text="待收货" />
        <van-grid-item icon="photo-o" text="待评价" />
        <van-grid-item icon="todo-list-o" text="全部订单" />
      </van-grid>
    </van-cell-group>
    <div class="title">服务帮助</div>
    <van-grid>
      <van-grid-item icon="location-o" text="地址管理" @click="$router.push('/address')"/>
    </van-grid>
  </div>
</template>
<script>
    export default {
        name: 'person',
        data() {
            return {
                name: '123',
                money: 3000
            }
        },
        mounted() {
        }
    }
</script>
<style scoped>
  .namecell {
    position: relative;
  }
  .name {
    position: absolute;
    top: 30px;
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
