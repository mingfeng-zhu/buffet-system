<template>
  <div>
    <van-nav-bar
            title="新增收货地址"
            left-arrow
            @click-left="onClickLeft"
    />
   <van-address-edit
     :area-list="areaList"
     show-delete
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
        onSave(content) {
            // Toast('save');
            console.log(content)
        },
        onDelete() {
            // Toast('delete');
        }
    },
  };
</script>
