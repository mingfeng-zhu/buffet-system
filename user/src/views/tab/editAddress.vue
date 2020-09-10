<template>
  <div>
    <van-nav-bar
            title="编辑收货地址"
            left-arrow
            @click-left="onClickLeft"
    />
    <van-address-edit
            :area-list="areaList"
            show-delete
            :show-set-default = "showDefault"
            show-search-result
            :address-info = "addressInfo"
            :search-result="searchResult"
            :area-columns-placeholder="['请选择', '请选择', '请选择']"
            @save="onSave"
            @delete="onDelete"
    />
  </div>
</template>
<script>
    import areaList from "../../assets/area";
    import {Toast} from "vant";
    export default {
        data() {
            return {
                areaList,
                searchResult: [],
                addressInfo:{
                    name:'',
                    tel:'',
                    addressDetail:'',
                    areaCode:'',
                    isDefault:false,
                },
                showDefault:true
            };
        },
        async mounted() {
            // 回显地址信息
            let {data} =await this.$api.getAddressById({id:this.$route.query.id})
            this.addressInfo.name = data.data.receiverName
            this.addressInfo.tel = data.data.receiverPhone
            this.addressInfo.areaCode = data.data.areaCode
            this.addressInfo.addressDetail = data.data.houseNumber
            let userInfo = await this.$api.getUserInfo()
            if (this.$route.query.id == userInfo.data.data.defaultAddressId) {
                this.showDefault=false
            }
        },
        methods: {
            // 返回上一级
            onClickLeft() {
                this.$router.back()
            },
            // 确定修改
            async onSave(content) {
                let address = {
                    receiverName: content.name,
                    receiverPhone: content.tel,
                    houseNumber: content.addressDetail,
                    areaCode: content.areaCode,
                    address: content.province + ' ' + content.city + ' ' + content.county,
                    defaultAddress:content.isDefault,
                    id:this.$route.query.id
                }
                await this.$api.update(address)
                Toast('编辑地址成功')
                this.$router.push('/address')
            },
            // 删除地址
            onDelete() {
                this.$api.deleteAddress(this.$route.query.id)
                Toast('删除地址成功')
                this.$router.push('/address')
            }
        },
    };
</script>
