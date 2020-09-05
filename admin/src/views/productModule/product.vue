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
    <el-table :data="productList" border style="margin-top:10px">
      <el-table-column fixed label="序号" width="50px" type="index" align="center" />
      <el-table-column fixed prop="productName" width="200px" label="商品名" align="center" />
      <el-table-column fixed prop="productDesc" label="商品描述" align="center" />
      <el-table-column fixed prop="createTime" width="140px" label="创建时间" align="center">
        <template scope="scope">
          {{ scope.row.createTime | parseTime }}
        </template>
      </el-table-column>
      <el-table-column fixed="right" label="操作" align="center">
        <template slot-scope="scope">
          <el-button type="primary" size="small" @click="showDialog(type = 'update', scope.$index)">修改</el-button>
          <el-popconfirm v-if="scope.row.status === 1" title="确定要上架吗？" @onConfirm="upProduct(scope.$index)">
            <el-button slot="reference" type="info" size="small" style="margin-left:5px">上架</el-button>
          </el-popconfirm>
          <el-popconfirm v-if="scope.row.status === 2" title="确定要下架吗？" @onConfirm="dropProduct(scope.$index)">
            <el-button slot="reference" type="warning" size="small" style="margin-left:5px">下架</el-button>
          </el-popconfirm>
          <el-popconfirm title="确定要删除吗？" @onConfirm="deleteProduct(scope.$index)">
            <el-button slot="reference" type="danger" size="small" style="margin-left:5px">删除</el-button>
          </el-popconfirm>
          <el-button type="success" size="small" style="margin-left: 5px" @click="showAttr(scope.$index)">查看规格属性</el-button>
          <el-button size="small" type="primary" plain style="margin-left: 5px" @click="showSpeci(scope.$index)">查看规格</el-button>
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
          <el-form v-if="updateIndex == null" ref="ruleForm1" :model="productSpecification" label="right" :rules="rules1" size="mini" :inline="true">
            <el-form-item label="商品价格:" prop="productPrice">
              <el-input v-model="productSpecification.productPrice" placeholder="请输入商品价格" @change="inputPrice" />
            </el-form-item>  
            <el-form-item label="商品库存:" prop="productStorage">
              <el-input-number  v-model="productSpecification.productStorage" placeholder="请输入商品库存" />
            </el-form-item>                    
          </el-form>
        </el-col>
      </el-row>
      <el-row>
        <el-button type="primary" size="mini" style="float: right" @click="handleSubmit('ruleForm', 'ruleForm1')">确 定</el-button>
        <el-button style="float: right; margin-right:10px" type="mini" @click="dialogFormVisible = false">取 消</el-button>
      </el-row>
    </el-dialog>

    <el-dialog title="规格属性" loading="false" :visible.sync="dialogAttrVisible" width="40%">
      <template v-if="productAttrList != undefined  && productAttrList.length > 0">
        <el-card v-for="(item, index) in productAttrList" shadow="never" style="margin: 5px 0">
          <el-row>
            <el-col :span="3"><div style="font-weight: bold; margin: 5px auto">属性名：</div></el-col>
            <el-col :span="8">
              <el-input v-model="item.productAttributeName" size="mini"></el-input>
            </el-col>
            <el-col :span="3">
              <el-popconfirm title="确定要删除吗？" @onConfirm="deleteAttr(index)">
                <el-button slot="reference" type="danger" size="small" style="margin-left:10px">删除</el-button>
              </el-popconfirm>
            </el-col>
            <el-col :span="6">
              <el-button type="primary" size="small" style="margin-left: 10px" @click="handleSaveAttr(index)">保存</el-button>
            </el-col>
          </el-row>
          <el-row>
            <div class="tag-group">
              <el-tag
                v-for="(value, vindex) in item.productAttributeValuePOList"
                :key="vindex"
                closable
                style="margin: 5px 5px"
                type="success"
                @close="handleRemoveAttr(index, vindex)">
                {{ value.productAttributeValue }}
              </el-tag>

              <el-input
                class="input-new-tag"
                v-if="item.inputVisible"
                v-model="item.inputValue"
                :ref="'saveTagInput' + index"
                size="small"
                @keyup.enter.native="handleAddAttr(index)"
                @blur="handleAddAttr(index)"
              >
              </el-input>
              <el-button v-else class="button-new-tag" size="small" @click="showInput(index)">+ 新增规格</el-button>
            </div>
          </el-row>
        </el-card>
        <el-button type="primary" size="small" style="margin-left: 5px" @click="addAttr()">添加属性</el-button>
      </template >
      <template v-else>
        <div align="center" style="font-weight: bold">暂无属性</div>
        <br>
        <el-button type="primary" size="small" @click="addAttr()">添加规格属性</el-button>
      </template>
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
  .el-tag + .el-tag {
    margin-left: 10px;
  }
  .button-new-tag {
    margin-left: 10px;
    height: 32px;
    line-height: 30px;
    padding-top: 0;
    padding-bottom: 0;
  }
  .input-new-tag {
    width: 90px;
    margin-left: 10px;
    vertical-align: bottom;
  }
</style>

<script>
import { getProductCategoryList, 
getProductList, addProduct, updateProduct, deleteProduct, dropProduct, upProduct, 
getProductAttributeListByProductId, 
addProductAttribute, updateProductAttribute, deleteProductAttribute,
deleteProductAttributeValue, addProductAttributeValue,
getProductSpecificationByProductId } from '@/api/product/product'
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
      rules1: {
        productPrice: [
          { required: true, message: '请输入商品价格', trigger: 'change' }
        ],
        productStorage: [
          { required: true, message: '请输入商品库存', trigger: 'change' }
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
      productSpecification: {

      },
      updateIndex: null,
      submitType: null,
      dialogFormVisible: false,
      listQuery: {
        total: 0,
        pageNum: 1,
        pageSize: 10,
        productName: ''
      },
      dialogAttrVisible: false,
      productAttrList: [],
      productSpecificationPOList: [],
      switchValue: false
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
        this.productSpecification = {
          productSpecificationId: null,
          productSpecification: '{}',
          productStorage: null,
          productPrice: null
        }

        this.updateIndex = null
      }

      if (type === 'update') {
        // 获取商品规格
        getProductSpecificationByProductId(this.productList[index].productId).then(res => {
          this.productSpecificationPOList = res.data
          this.updateIndex = index
          this.product = Object.assign({}, this.productList[index])
          if (this.productSpecificationPOList.length == 1 && this.productSpecificationPOList[0].productSpecification === '{}') {
            this.switchValue = false
          } else {
            this.switchValue = true
          }
        })
      }      
      this.dialogFormVisible = true
    },
    handleSubmit(name, name1) {
      if (this.product.productPicturePath == null || this.product.productPicturePath == '') {
        this.$message.error('请先上传商品图片')
        return
      }
      this.$refs[name].validate((valid) => {        
        if (valid) {
          if (this.product.productId != null) {   // update
            updateProduct(this.product).then(res => {
              this.$set(this.productList, this.updateIndex, this.product)
              this.$message.success('修改成功')
              this.dialogFormVisible = false
            })
          } else {  // add
            this.$refs[name1].validate((valid) => {
              if (valid) {
                this.productSpecification.productSpecificationPicture = this.product.productPicturePath
                addProduct({product: this.product, productSpecification: this.productSpecification}).then(res => {
                  this.$message.success('添加成功')
                  this.dialogFormVisible = false
                })
              } else {
                this.$message.error('请先完善商品信息')
              }
            })
          }
        } else {
          this.$message.error('请先完善商品信息')
        }
      });
    },
    inputPrice (curValue, odlValue) {
      this.productSpecification.productPrice = this.productSpecification.productPrice.replace(/[^\d.]/g,"");  //清除“数字”和“.”以外的字符
      this.productSpecification.productPrice = this.productSpecification.productPrice.replace(/^\./g,"");  //验证第一个字符是数字而不是.
      this.productSpecification.productPrice = this.productSpecification.productPrice.replace(/\.{2,}/g,"."); //只保留第一个. 清除多余的.
      this.productSpecification.productPrice = this.productSpecification.productPrice.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
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
    },
    showAttr (index) {
      getProductAttributeListByProductId(this.productList[index].productId).then(res => {
        this.productAttrList = res.data
        for (let i = 0; i < this.productAttrList.length; i++) {
          this.$set(this.productAttrList[i], 'inputVisible', false)
          this.$set(this.productAttrList[i], 'inputValue', '')
        }
        this.updateIndex = index
        this.dialogAttrVisible = true
      })
    },
    addAttr() {
      let attr = {
        productAttributeId: null,
        productAttributeName: '',
        productAttributeValueDTOList: [], 
        productId: this.productList[this.updateIndex].productId,
        inputVisible: false,
        inputValue: ''
      }
      this.$set(this.productAttrList, this.productAttrList.length, attr)
    },
    handleSaveAttr(index) {
      if (this.productAttrList[index].productAttributeName == null || this.productAttrList[index].productAttributeName == '') {
        this.$message.error('请先输入属性名')
        return
      }
      if (this.productAttrList[index].productAttributeId == undefined) {  // 没有主键是添加
        addProductAttribute(this.productAttrList[index]).then(res => {
          this.$set(this.productAttrList, index, res.data)
          this.$message.success('保存商品属性成功')
        })
      } else {  // 有主键是修改
        updateProductAttribute(this.productAttrList[index]).then(res => {
          this.$set(this.productAttrList, index, res.data)
          this.$message.success('保存商品属性成功')
        })
      }
      
    },
    deleteAttr(index) {
      if (this.productAttrList[index].productAttributeId == undefined) {
        this.productAttrList.splice(index, 1)
      } else {
        deleteProductAttribute(this.productAttrList[index].productAttributeId).then(res => {
          this.productAttrList.splice(index, 1)
          this.$message.success('删除属性成功')
        })
      }
    },
    showInput(index) {
      this.productAttrList[index].inputVisible = true;
      this.$nextTick(_ => {
        // console.log(index, this.$refs)
        this.$refs['saveTagInput' + index][0].$refs.input.focus();
      });
    },
    handleAddAttr(index) {
      if (this.productAttrList[index].inputValue) {
        if (this.productAttrList[index].productAttributeId == undefined) {
          this.$message.error('请先保存属性，然后再添加属性值')
          return
        }

        let value = {
          productAttributeValueId: null,
          productAttributeId: this.productAttrList[index].productAttributeId,
          productAttributeValue: this.productAttrList[index].inputValue
        }
        // 调用新增属性值得接口新增
        addProductAttributeValue(value).then(res => {
          this.productAttrList[index].productAttributeValuePOList.push(res.data)
          this.$message.success('新增属性值成功')
        })
      }
      this.productAttrList[index].inputVisible = false
      this.productAttrList[index].inputValue = ''
    },
    handleRemoveAttr(index, vindex) {
      this.$confirm('删除属性值相应的商品规格信息也会删除，您确定要删除吗？', '确认信息', {
        distinguishCancelAndClose: true,
        confirmButtonText: '保存',
        cancelButtonText: '取消'
      })
      .then(() => {
        let body = {
          productId: this.productList[this.updateIndex].productId,
          productAttributeId: this.productAttrList[index].productAttributeId,
          productAttributeValueId: this.productAttrList[index].productAttributeValuePOList[vindex].productAttributeValueId
        }
        deleteProductAttributeValue(body).then(res => {
          this.productAttrList[index].productAttributeValuePOList.splice(vindex, 1)
          this.$message.success('删除属性值成功')
        })
      })
    }
  }
}
</script>
