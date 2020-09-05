<template>
  <div>
    <van-nav-bar
            center
            title="修改个人信息"
            left-arrow
            @click-left="onClickLeft"
    />
    <van-field
            name="portrait"
            input-align="right"
            :rules="[
                  { required: true, message: '请上传头像' },
                  ]"
    >
      <template #label>
        <span style="line-height: 70px">头像</span>
      </template>
      <template #input>
        <van-uploader
                v-model="fileList"
                :max-count="1"
                :after-read="afterRead"
        />
      </template>
    </van-field>
    <van-field
            v-model="name"
            label="昵称"
            placeholder="请输入"
            input-align="right"
            :rules="[{ required: true, message: '请输入昵称' }]"
    />
    <van-button type="primary" size="large" color="linear-gradient(to right, #ff6034, #ee0a24)" @click="certain">确认修改</van-button>
  </div>
</template>

<script>
    import upLoaderImg from "../../api/upLoaderImg";

    export default {
        name: "personInfo",
        data(){
            return{
                name:'',
                fileList:[{ url: '' }],
                uploadImg:''
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
            async certain() {
                console.log(this.name, this.uploadImg)
                let param = {
                    userImg: this.uploadImg,
                    userName: this.name,
                }
                await this.$api.updateUser(param)
            },
            async afterRead(file) {
                let uploadImg = await upLoaderImg(file.file)//使用上传的方法。file.file
                this.uploadImg = uploadImg.data
            },
        }
    }
</script>

<style scoped>

</style>
