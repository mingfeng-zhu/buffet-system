<template>
  <div>
    <van-nav-bar
            title="收货地址管理"
            left-arrow
            @click-left="onClickLeft"
    />
    <van-address-list
            v-model="chosenAddressId"
            :list="list"
            default-tag-text="默认"
            @add="onAdd"
            @edit="onEdit"
            @click-item="select"
    />
  </div>
</template>
<script>

    export default {
        data() {
            return {
                selected:'0',
                chosenAddressId: '1',
                list: [
                    {
                        id: '1',
                        name: '张三',
                        tel: '13000000000',
                        address: '浙江省杭州市西湖区文三路 138 号东方通信大厦 7 楼 501 室',
                        isDefault: true,
                    },
                    {
                        id: '2',
                        name: '李四',
                        tel: '1310000000',
                        address: '浙江省杭州市拱墅区莫干山路 50 号',
                    },
                ]
            };
        },
      mounted() {
            this.selected = sessionStorage.getItem('selected')
            sessionStorage.setItem('selected','0')
      },
        methods: {
            onClickLeft() {
                this.$router.back()
            },
            onAdd() {
                // Toast('新增地址');
                this.$router.push('/addressform')
            },
            onEdit(item, index) {
                console.log(12, item, index)
                this.$router.push({name: 'editAddress', query: {id: item.id}})
                // Toast('编辑地址:' + index);
            },
            select(){
              //只有从订单确认界面发起的才会返回地址
              if(this.selected === '1') {
                let info = JSON.stringify(this.list[this.chosenAddressId - 1])
                sessionStorage.setItem('address', info)
                this.$router.back()
              }
            }
        },
    };
</script>
