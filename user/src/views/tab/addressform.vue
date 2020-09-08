<template>
  <div>
    <van-nav-bar
            title="新增收货地址"
            left-arrow
            @click-left="onClickLeft"
    />
   <van-address-edit
     :area-list="areaList"
     show-set-default
     show-search-result
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
      };
    },
    methods: {
        onClickLeft() {
            this.$router.back()
        },
        async onSave(content) {
            console.log(content)
            let address = {
                receiverName: content.name,
                receiverPhone: content.tel,
                houseNumber: content.addressDetail,
                areaCode: content.areaCode,
                address: content.province + ' ' + content.city + ' ' + content.county,
                defaultAddress:content.isDefault
            }
            await this.$api.insert(address)
            Toast('新增地址成功')
            this.$router.push('/address')
        },
        onDelete() {
            // Toast('delete');
        }
    },
  };
</script>
