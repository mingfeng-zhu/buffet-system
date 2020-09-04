<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input
        v-model="listQuery.productName"
        placeholder="商品名"
        style="width: 200px;"
        class="filter-item"
        @keyup.enter.native="handleFilter"
      />
      <el-button
        v-waves
        class="filter-item"
        type="primary"
        style="margin-left: 10px;"
        icon="el-icon-search"
        @click="handleFilter"
      >搜索</el-button>
      <el-button
        class="filter-item"
        style="float: right;margin-left: 10px;"
        type="primary"
        icon="el-icon-edit"
        @click="showDialog(type = 'add')"
      >新增</el-button>
    </div>
    <el-table :data="productList" border style="width: 100%; margin-top:10px">
      <el-table-column fixed label="序号" width="50px" type="index" align="center" />
      <el-table-column fixed prop="productName" label="商品名" align="center" />
      <el-table-column fixed prop="productDesc" label="商品描述" align="center" />
      <el-table-column fixed prop="createTime" label="创建时间" align="center">
        <template scope="scope">
          {{ scope.row.createTime | parseTime }}
        </template>
      </el-table-column>
      <!-- <el-table-column fixed prop="updateTime" label="更新时间" align="center" >
        <template scope="scope">
          {{ scope.row.updateTime | parseTime }}
        </template>
      </el-table-column> -->
      <el-table-column fixed="right" label="操作" align="center">
        <template slot-scope="scope">
          <el-button type="primary" size="small" @click="showDialog(type = 'update', scope.$index)">修改</el-button>
          <el-popconfirm v-if="scope.row.status === 1" title="确定要上架吗？" @onConfirm="upProduct(scope.$index)">
            <el-button slot="reference" type="info" size="small" style="margin-left:10px">上架</el-button>
          </el-popconfirm>
          <el-popconfirm v-if="scope.row.status === 2" title="确定要下架吗？" @onConfirm="dropProduct(scope.$index)">
            <el-button slot="reference" type="warning" size="small" style="margin-left:10px">下架</el-button>
          </el-popconfirm>
          <el-popconfirm title="确定要删除吗？" @onConfirm="deleteProduct(scope.$index)">
            <el-button slot="reference" type="danger" size="small" style="margin-left:10px">删除</el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <pagination
      v-show="listQuery.total > 0"
      :total="listQuery.total"
      :page.sync="listQuery.pageNum"
      :limit.sync="listQuery.pageSize"
      @pagination="getProductList"
    />

    <el-dialog title="商品" loading="false" :visible.sync="dialogFormVisible" width="40%">
      <el-row>
        <el-col :span="11">
          <div style="font-size: 14px; font-weight: bold; margin: 0 0px 10px 20px">商品图片：</div>
          <el-upload
            class="avatar-uploader"
            action="http://localhost:9528/api/api/upload"
            name="picture"
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload">
            <img v-if="product.productPicturePath" :src="product.productPicturePath" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-col>
        <el-col :span="13">
          <el-form ref="ruleForm" :model="product" label="right" :rules="rules" size="mini" :inline="true">
            <el-form-item label="商品名称:" prop="productName">
              <el-input v-model="product.productName" placeholder="请输入商品名称" />
            </el-form-item>
            <el-form-item label="商品描述:" prop="productDesc">
              <el-input v-model="product.productDesc" type="textarea" placeholder="请输入商品描述" />
            </el-form-item>
            <el-form-item label="商品分类:" prop="productCategoryId">
              <el-select v-model="product.productCategoryId" placeholder="请选择商品分类">
                <el-option
                  v-for="item in productCategoryList"
                  :key="item.productCategoryId"
                  :label="item.categoryName"
                  :value="item.productCategoryId"
                />
              </el-select>
            </el-form-item>
          </el-form>
        </el-col>
      </el-row>
      <el-row>
        <el-button type="primary" size="mini" style="float: right" @click="handleSubmit('ruleForm')">确 定</el-button>
        <el-button style="float: right; margin-right:10px" type="mini" @click="dialogFormVisible = false">取 消</el-button>
      </el-row>
    </el-dialog>
  </div>
</template>

<style>
  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
    margin: 0 0 0 20px;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 200px;
    line-height: 178px;
    text-align: center;
  }
  .avatar {
    width: 178px;
    height: 200px;
    display: block;
  }
</style>

<script>
import { getProductCategoryList, getProductList, deleteProduct, dropProduct, upProduct } from '@/api/product/product'
import Pagination from '@/components/Pagination'
import waves from '@/directive/waves'
import { parseTime } from '@/utils'
export default {
  components: { Pagination },
  directives: { waves },
  filters: {
    parseTime(time) {
      var date = new Date(time)
      return parseTime(date, '{y}-{m}-{d} {h}:{i}')
    }
  },
  data() {
    return {
      productList: [],
      rules: {
        productName: [
          { required: true, message: '请输入商品名称', trigger: 'blur' }
        ],
        productDesc: [
          { required: true, message: '请输入商品描述', trigger: 'blur' }
        ],
        productCategoryId: [
          { required: true, message: '请选择商品分类', trigger: 'change' }
        ]
      },
      productCategoryList: [],
      product: {
        productCategoryId: 1,
        productDesc: 'product1',
        productId: null,
        productName: 'product1',
        productPicturePath: 'http://121.199.49.199:8082/upload/202008032221.jpg'
      },
      updateIndex: null,
      submitType: null,
      dialogFormVisible: false,
      listQuery: {
        total: 0,
        pageNum: 1,
        pageSize: 10,
        productName: ''
      }
    }
  },
  created() {
    this.init()
  },
  methods: {
    init() {
      this.getProductList()
      this.getProductCategoryList()
    },
    getProductList() {
      getProductList(this.listQuery).then(response => {
        this.productList = response.data.list
        this.listQuery.total = response.data.total
      })
    },
    getProductCategoryList() {
      getProductCategoryList({ pageSize: 0 }).then(response => {
        this.productCategoryList = response.data.list
      })
    },
    handleFilter() {
      this.listQuery.pageNum = 1
      this.getProductList()
    },
    deleteProduct(index) {
      deleteProduct(this.productList[index].productId).then(response => {
        this.productList.splice(index, 1)
        this.$message.success('删除商品成功！')
      })
    },
    dropProduct(index) {
      dropProduct(this.productList[index].productId).then(response => {
        // 更改状态
        this.productList[index].status = 1
        this.$message.success('下架商品成功！')
      })
    },
    upProduct(index) {
      upProduct(this.productList[index].productId).then(response => {
        // 更改状态
        this.productList[index].status = 2
        this.$message.success('上架商品成功！')
      })
    },
    showDialog(type, index) {
      this.submitType = type
      if (type === 'add') {
        this.product = {
          productId: null,
          productName: null,
          productDesc: null,
          productCategoryId: null,
          productPicturePath: null
        }
      }

      if (type === 'update') {
        this.updateIndex = index
        this.product = Object.assign({}, this.productList[index])
      }
      this.dialogFormVisible = true
    },
    handleAvatarSuccess(res, file) {
      console.log(res)
      if (res.code === 2000) {
        this.product.productPicturePath = 'http://localhost:8082' + res.data;
      }
    },
    beforeAvatarUpload(file) {
      const isJPG = file.type === 'image/jpeg';
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG 格式!');
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!');
      }
      return isJPG && isLt2M;
    }
  }
}
</script>
