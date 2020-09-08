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
            @select="select"
    />
  </div>
</template>
<script>

    export default {
        data() {
            return {
                selected:'0',
                chosenAddressId: '1',
                list: []
            };
        },
      async mounted() {
          this.selected = sessionStorage.getItem('selected')
          let user = JSON.parse(localStorage.getItem('userPo'))
          let userId = user.userId
          let userInfo = await this.$api.getUserInfo()
          let id = userInfo.data.data.defaultAddressId
          console.log(34, id)
          // 数据回显
          let {data} = await this.$api.addressList({userId:userId})
          console.log(34, data.data)
          for (let i=0;i<data.data.length;i++) {
              this.list[i] ={}
              this.$set(this.list, '[i]', {})
              this.$set(this.list[i], 'address', data.data[i].address+data.data[i].houseNumber)
              this.$set(this.list[i], 'id', data.data[i].id)
              this.$set(this.list[i], 'name', data.data[i].receiverName)
              this.$set(this.list[i], 'tel', data.data[i].receiverPhone)
          }
          // 默认地址的处理
          this.list.forEach(item=> {
              if (item.id==id) {
                  item.isDefault = true
              }
          })
          // 将默认地址置顶
          this.list.forEach(item=> {
              if (item.isDefault) {
                  this.list.unshift(item)
              }
              this.list = Array.from(new Set(this.list));
          })
      },
        methods: {
            onClickLeft() {
                this.$router.back()
                sessionStorage.setItem('selected','0')
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
            select(item,index){
              //只有从订单确认界面发起的才会返回地址
              if(this.selected === '1') {
                let info = JSON.stringify(this.list[index])
                sessionStorage.setItem('address', info)
                sessionStorage.setItem('selected','0')
                this.$router.push('/orderconfirm')
              }
            }
        },
    };
</script>
