<template>
  <div style="padding: 10px;width: 100%">

    <el-card>
    <!--    功能区域-->
    <div style="margin: 10px 0">
      <el-button @click="">刷新</el-button>
    <!--
    <el-button type="primary" @click="add">新增</el-button>
      <el-upload
          action="http://localhost:9090/user/import"
          :on-success="handleUploadSuccess"
          :show-file-list=false
          :limit="1"
          accept='.xlsx'
          style="display: inline-block; margin: 0 10px"
      >
        <el-button type="primary">导入</el-button>
      </el-upload>
      -->
      <el-button type="primary" @click="exportUser">导出</el-button>
    </div>

    <!--    搜索区域-->
    <div style="margin: 10px 0">
      <el-input v-model="search" placeholder="请输入关键字(用户名)" style="width: 20%" clearable></el-input>
      <el-button type="primary" style="margin-left: 5px" @click="load">查询</el-button>
    </div>
    <el-table
        v-loading="loading"
        :data="tableData"
        border
        stripe
        style="width: 100%">
      <el-table-column
          prop="id"
          label="ID"
          sortable
      >
      </el-table-column>
      <el-table-column
          prop="userId"
          label="用户id">
      </el-table-column>
      <el-table-column
          prop="username"
          label="用户名"
          width="120"
      >
      </el-table-column>

      <el-table-column
          prop="startTime"
          label="开始时间"
          width="200"
          sortable
      >
      </el-table-column>
      <el-table-column
          prop="state"
          label="状态"
      >
      </el-table-column>
      <el-table-column
          prop="endTime"
          label="结束时间"
          width="200"
          sortable
      >
      </el-table-column>
      <el-table-column
          prop="pileName"
          label="充电桩名"
          width="120"
      >
      </el-table-column>

      <el-table-column
          prop="payTime"
          label="支付时间"
          width="200"
          sortable
      >
      </el-table-column>

      <el-table-column
          prop="payChannel"
          label="支付渠道"
          width="120"
      >
      </el-table-column>

      <el-table-column
          prop="amount"
          label="金额">
      </el-table-column>

      <el-table-column
          prop="remark"
          label="备注">
      </el-table-column>

      <el-table-column fixed="right" label="操作" width="200">
        <template #default="scope">

          <el-button size="mini" type="primary" plain @click="handleEdit(scope.row)">编辑</el-button>
          <el-popconfirm title="确定删除吗？" @confirm="handleDelete(scope.row.id)">
            <template #reference>
              <el-button size="mini" type="danger">删除</el-button>
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <div style="margin: 10px 0">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="currentPage"
          :page-sizes="[5, 10, 20]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>
    </div>

    <el-dialog title="提示" v-model="dialogVisible" width="30%">
      <el-form :model="form" label-width="120px">
        <el-form-item label="状态：">
          <el-radio v-model="form.state" label="已完成">已完成</el-radio>
          <el-radio v-model="form.state" label="充电中">充电中</el-radio>
          <el-radio v-model="form.state" label="已预约">已预约</el-radio>
          <el-radio v-model="form.state" label="未知">未知</el-radio>
        </el-form-item>
        <el-form-item label="备注：">
          <el-input type="textarea" v-model="form.remark" style="width: 80%"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
          <span class="dialog-footer">
            <el-button @click="dialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="save">确 定</el-button>
          </span>
      </template>
    </el-dialog>
    </el-card>
  </div>
</template>

<script>


import request from "@/utils/request";

export default {
  name: 'Order',
  components: {},
  data() {
    return {
      loading: true,
      form: {},
      dialogVisible: false,
      search: '',
      currentPage: 1,
      pageSize: 10,
      total: 0,
      tableData: [],
    }
  },
  created() {
    this.load()
  },
  methods: {
    load() {
      this.loading = true
      request.get("/order", {
        params: {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          search: this.search
        }
      }).then(res => {
        this.loading = false
        this.tableData = res.data.records
        this.total = res.data.total
      })
    },
    handleUploadSuccess(res) {
      if (res.code === "0") {
        this.$message.success("导入成功")
        this.load()
      }
    },
    exportUser() {
      location.href = "http://" + window.server.filesUploadUrl + ":9090/smallTableExportCsv?table=charging_order";
    },
    add() {
      this.dialogVisible = true
      this.form = {}
    },
    save() {
      if (this.form.id) {  // 更新
        request.put("/order", this.form).then(res => {
          console.log(res)
          if (res.code === '0') {
            this.$message({
              type: "success",
              message: "更新成功"
            })
          } else {
            this.$message({
              type: "error",
              message: res.msg
            })
          }
          this.load() // 刷新表格的数据
          this.dialogVisible = false  // 关闭弹窗
        })
      } else {  // 新增
        request.post("/order", this.form).then(res => {
          console.log(res)
          if (res.code === '0') {
            this.$message({
              type: "success",
              message: "新增成功"
            })
          } else {
            this.$message({
              type: "error",
              message: res.msg
            })
          }

          this.load() // 刷新表格的数据
          this.dialogVisible = false  // 关闭弹窗
        })
      }

    },
    handleEdit(row) {
      this.form = JSON.parse(JSON.stringify(row))
      this.dialogVisible = true
    },
    handleDelete(id) {
      console.log(id)
      request.delete("/order/" + id).then(res => {
        if (res.code === '0') {
          this.$message({
            type: "success",
            message: "删除成功"
          })
        } else {
          this.$message({
            type: "error",
            message: res.msg
          })
        }
        this.load()  // 删除之后重新加载表格的数据
      })
    },
    handleSizeChange(pageSize) {   // 改变当前每页的个数触发
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(pageNum) {  // 改变当前页码触发
      this.currentPage = pageNum
      this.load()
    }
  }
}
</script>
