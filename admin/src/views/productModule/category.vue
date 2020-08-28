<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input
        v-model="listQuery.categoryName"
        placeholder="类别名"
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
        >搜索</el-button
      >
      <el-button
        class="filter-item"
        style="margin-left: 10px;"
        type="primary"
        icon="el-icon-edit"
        @click="showDialog(type = 'add')"
        >新增</el-button
      >
    </div>
    <el-table :data="categoryList" border style="width: 100%; margin-top:10px">
      <el-table-column fixed prop="productCategoryId" label="分类ID" />
      <el-table-column fixed prop="categoryName" label="分类名" />
      <el-table-column fixed prop="categoryDesc" label="分类描述" />
      <el-table-column fixed prop="createTime" label="创建时间" />
      <el-table-column fixed prop="updateTime" label="更新时间" />
      <el-table-column fixed="right" label="操作">
        <template slot-scope="scope">
          <el-button
            class="el-icon-edit"
            type="primary"
            size="small"
            @click="showDialog(type = 'update', scope.$index)"
          >修改</el-button>
          <el-popconfirm title="确定要删除吗？" @onConfirm="deleteCategory(scope.$index)">
            <el-button slot="reference" class="el-icon-delete" type="danger" size="small" style="margin-left:10px">删除</el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <pagination
      v-show="listQuery.total > 0"
      :total="listQuery.total"
      :page.sync="listQuery.pageNum"
      :limit.sync="listQuery.pageSize"
      @pagination="getList"
    />
    <el-dialog title="创建商品分类" :loading="loading" :visible.sync="dialogFormVisible" width="30%">
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
import axios from 'axios'
import { getProductCategoryList } from '@/api/product/product'
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
      submitType: null,
      loading: false,
      listQuery: {
        total: 0,
        pageNum: 1,
        pageSize: 10,
        categoryName: ''
      }
    }
  },
  created() {
    this.init()
  },
  methods: {
    init() {
      this.getList()
    },
    /**
     * 获取商品分类列表
     */
    getList(){
      getProductCategoryList(this.listQuery).then(response => {
        this.categoryList = response.data.list
        this.listQuery.total = response.data.total
        console.log(response)
      })
    },
    handleFilter() {
      this.listQuery.pageNum = 1
      this.getList()
    },
    addCategory(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          const self = this
          axios.put('http://localhost:8082/admin/product/productCategory', self.category)
            .then(res => {
              if (res.data.code === 2000) {
                self.categoryList.push(res.data.data)
                self.$message.success('新建商品分类成功！')
              }
            }).catch(err => {
              console.log(err)
            })
        } else {
          this.$message.error('新建商品分类失败！')
        }
        this.dialogFormVisible = false
      })
    },
    updateCategory() {
      const self = this
      axios.post('http://localhost:8082/admin/product/productCategory', self.category)
        .then(res => {
          if (res.data.code === 2000) {
            self.$set(self.categoryList, self.updateIndex, res.data.data)
            self.$message.success('修改商品分类成功！')
            self.dialogFormVisible = false
          } else {
            self.$message.error(res.data.message)
          }
        }).catch(err => {
          console.log(err)
        })
    },
    deleteCategory(index) {
      const self = this
      axios.delete(`http://localhost:8082/admin/product/productCategory/${self.categoryList[index].productCategoryId}`)
        .then(res => {
          if (res.data.code === 2000) {
            self.categoryList.splice(index, 1)
            self.$message.success('删除成功！')
            self.visible = false
          } else {
            self.$message.error(res.data.message)
          }
        }).catch(err => {
          console.log(err)
        })
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
