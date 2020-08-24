<template>
  <div class="app-container">
    <el-button type="primary" size="small" style="margin: 10px 0" @click="showDialog(type = 'add')">新增</el-button>
    <el-table :data="categoryList" border style="width: 100%">
      <el-table-column fixed prop="productCategoryId" label="分类ID" />
      <el-table-column fixed prop="categoryName" label="分类名" />
      <el-table-column fixed prop="categoryDesc" label="分类描述" />
      <el-table-column fixed prop="createTime" label="创建时间" />
      <el-table-column fixed prop="updateTime" label="更新时间" />
      <el-table-column fixed="right" label="操作">
        <template slot-scope="scope">
          <el-button
            type="primary"
            size="small"
            @click="showDialog(type = 'update', scope.$index)"
          >修改</el-button>
          <el-popconfirm title="确定要删除吗？" @onConfirm="deleteCategory(scope.$index)">
            <el-button slot="reference" type="danger" size="small">删除</el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog title="创建商品分类" :visible.sync="dialogFormVisible" width="30%">
      <el-form ref="ruleForm" :model="category" :rules="rules">
        <el-form-item label="分类名称" prop="categoryName">
          <el-input v-model="category.categoryName" />
        </el-form-item>
        <el-form-item label="分类描述" prop="categoryDesc">
          <el-input v-model="category.categoryDesc" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" style="float: right" @click="handleSubmit('ruleForm')">确 定</el-button>
          <el-button style="float: right" @click="dialogFormVisible = false">取 消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>

export default {
  data() {
    return {
      categoryList: [
        {
          productCategoryId: 1,
          categoryName: '分类1',
          categoryDesc: '分类描述1',
          createTime: '2019-09-18 02:54:35',
          updateTime: '2019-09-18 02:54:35'
        },
        {
          productCategoryId: 2,
          categoryName: '分类2',
          categoryDesc: '分类描述2',
          createTime: '2019-09-18 02:54:35',
          updateTime: '2019-09-18 02:54:35'
        }
      ],
      rules: {
        categoryName: [
          { required: true, message: '请输入分类名称', trigger: 'blur' },
          { min: 2, max: 20, message: '长度在2~10个字符', trigger: 'blur' }
        ],
        categoryDesc: [
          { required: true, message: '请输入分类描述', trriger: 'blur' },
          { max: 100, message: '不能超过100个字符', trriger: 'blur' }
        ]
      },
      category: {
        productCategoryId: null,
        categoryName: null,
        categoryDesc: null,
        createTime: null,
        updateTime: null

      },
      visible: false,
      dialogFormVisible: false,
      updateIndex: null,
      submitType: null
    }
  },
  created() {
    this.init()
  },
  methods: {
    init() {
      console.log('init')
    },
    addCategory(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          console.log('submit!')
          this.categoryList.push(this.category)
          this.$message.success('新建商品分类成功！')
        } else {
          // console.log('error submit!!')
          this.$message.error('新建商品分类失败！')
        }
        this.dialogFormVisible = false
      })
    },
    updateCategory() {
      this.$set(this.categoryList, this.updateIndex, this.category)

      this.dialogFormVisible = false
    },
    deleteCategory(index) {
      console.log('shanchu' + this.categoryList[index].productCategoryId)
      this.categoryList.splice(index, 1)
      this.$message.success('删除成功！')
      this.visible = false
    },
    handleSubmit(formName) {
      if (this.submitType === 'add') {
        this.addCategory(formName)
      }
      if (this.submitType === 'update') {
        this.updateCategory()
      }
    },
    showDialog(type, index) {
      this.submitType = type
      if (type === 'add') {
        this.category.productCategoryId = null
        this.category.categoryName = null
        this.category.categoryDesc = null
        this.category.createTime = null
        this.category.updateTime = null
      }
      if (type === 'update') {
        this.updateIndex = index
        this.category = JSON.parse(JSON.stringify(this.categoryList[index]))
      }
      this.dialogFormVisible = true
      // console.log('type: ' + type + ', index: ' + index)
    }
  }
}
</script>
