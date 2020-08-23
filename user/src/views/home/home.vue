<template>
    <div class="content">
        <van-button type="primary" class="btn">地方</van-button>
    </div>
</template>
<script>
export default {
    data() {
        return {

        }
    },
    created(){
        this.initData()
    },
    methods: {
        async initData(){
            let { data } = await this.$api.getArticleList({type:2});
            console.log(123, data)
        }
    },
}
</script>
<style lang="scss">
    .content{
        width: 100%;
        height: 100%;
        .btn{
            width: 200px;
            height: 40px;
        }
    }
</style>
