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
            let {data} =await this.$api.getAddressById({id:this.$route.query.id})
            console.log(31, data.data)
            this.addressInfo.name = data.data.receiverName
            this.addressInfo.tel = data.data.receiverPhone
            this.addressInfo.areaCode = data.data.areaCode
            this.addressInfo.addressDetail = data.data.houseNumber
            let userInfo = await this.$api.getUserInfo()
            console.log('userInfo.data.defaultAddressId',  this.$route.query.id,userInfo.data.data.defaultAddressId,this.$route.query.id === userInfo.data.data.defaultAddressId)
            if (this.$route.query.id == userInfo.data.data.defaultAddressId) {
                this.showDefault=false
            }
        },
        methods: {
            onClickLeft() {
                this.$router.back()
            },
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
            onDelete() {
                // Toast('delete');
            }
        },
    };
</script>
