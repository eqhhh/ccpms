<template>
  <div style="padding: 10px;width: 100%">
    <div style=" padding-left:5px; padding-top:7px; padding-bottom:5px">
      <el-breadcrumb
          style="font-size: 90%; "
          separator-icon="ArrowRight"
      >
        <el-breadcrumb-item :to="{ path: '/' }">主页</el-breadcrumb-item>
        <el-breadcrumb-item>充电服务</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <el-card style="margin: 10px 0">
      <!--    功能区域-->
      <div style="margin: 10px 0">
        <el-button type="primary" @click="reserve">预约充电</el-button>

        <el-tag
            style="margin-left: 20px"
            type="danger">请于
        <span>{{countDownTime}}</span>
        内开始充电</el-tag>

      </div>

      <!--    搜索区域-->
      <div style="margin: 10px 0">
        <el-input v-model="search" placeholder="请输入关键字" style="width: 20%" clearable></el-input>
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
            label="支付渠道">
        </el-table-column>

        <el-table-column
            prop="amount"
            label="金额">
        </el-table-column>
        <el-table-column
            prop="state"
            label="状态"
            sortable
        >
        </el-table-column>
        <el-table-column
            prop="remark"
            label="备注">
        </el-table-column>

        <el-table-column fixed="right" label="操作" width="120">
          <template #default="scope">

            <el-popconfirm title="确定结束吗？" @confirm="handleEnd(scope.row)">
              <template #reference>
                <el-button size="mini"
                           type="danger"
                           v-if="scope.row.state === '充电中'"
                >结束充电
                </el-button>
              </template>
            </el-popconfirm>

            <el-popconfirm title="确定开始吗？" @confirm="handleStart(scope.row)">
              <template #reference>
                <el-button size="mini"
                           type="success"
                           v-if="scope.row.state === '已预约'"
                >开始充电
                </el-button>
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

<!--老版的充电流程已经弃用-->
      <el-dialog title="开始充电" v-model="dialogVisible" width="30%">
        <el-form :model="form" label-width="120px">

<!--         <el-form-item label="选择充电站">
            <el-select  v-model="form.stationId" placeholder="请选择" style="width: 80%">
              <el-option v-for="item in stations" :key="item.id" :label="item.stationName" :value="item.id"></el-option>
            </el-select>
          </el-form-item>-->

          <el-form-item label="选择充电桩：">
            <el-select  v-model="form.pileId" placeholder="请选择" style="width: 80%">
              <el-option v-for="item in idlePiles" :key="item.id" :label="item.pileName" :value="item.id"></el-option>
            </el-select>
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


      <el-dialog title="预约充电" v-model="dialogVisible1" width="30%">
        <el-form :model="form1" label-width="120px">

          <!--         <el-form-item label="选择充电站">
                      <el-select  v-model="form.stationId" placeholder="请选择" style="width: 80%">
                        <el-option v-for="item in stations" :key="item.id" :label="item.stationName" :value="item.id"></el-option>
                      </el-select>
                    </el-form-item>-->

          <el-form-item label="选择充电桩：">
            <el-select  v-model="form1.pileId" placeholder="请选择" style="width: 80%">
              <el-option v-for="item in idlePiles" :key="item.id" :label="item.pileName" :value="item.id"></el-option>
            </el-select>
          </el-form-item>

          <el-form-item label="备注：">
            <el-input type="textarea" v-model="form1.remark" style="width: 80%"></el-input>
          </el-form-item>
        </el-form>
        <template #footer>
          <span class="dialog-footer">
            <el-button @click="dialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="pay">支 付</el-button>
          </span>
        </template>
      </el-dialog>

      <el-dialog title="支付模拟" v-model="dialogVisible2" width="40%">
        <el-form :model="form1" label-width="120px">

          <el-form-item>
            <div>
              <el-radio-group v-model="form1.payChannel" type="vertical">
                <el-radio-button label="账户支付" />
                <el-radio-button label="微信支付" />
                <el-radio-button label="支付宝支付" />
                <el-radio-button label="其它" />
              </el-radio-group>
            </div>
          </el-form-item>

          <el-form-item label="账户余额：">
            <el-input  v-model="personForm.account"
                       disabled
                       style="width: 80%"></el-input>
          </el-form-item>

          <el-form-item label="预付金额：">
            <el-input  v-model="form1.amount" style="width: 80%"></el-input>
          </el-form-item>
          <el-tag type="success" style="font-size: 100%;margin-top: 20px">预付金额默认为个人充电限额</el-tag>
        </el-form>
        <template #footer>

          <span class="dialog-footer">
            <el-button @click="dialogVisible = false">取消支付</el-button>
            <el-button type="primary" @click="reserveSave">支付完成</el-button>

          </span>
        </template>
      </el-dialog>

    </el-card>

  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "UsePile",
  data() {
    return {
      loading: true,
      form: {},
      form1: {},
      dialogVisible: false,
      dialogVisible1:false,
      dialogVisible2:false,
      divVisible:false,
      search: '',
      currentPage: 1,
      pageSize: 10,
      total: 0,
      tableData: [],
      stations: [],
      idlePiles: [],
      personForm: {},
      countDownTime: '',
      visibility: 'hidden'
    }
  },
  created() {
    let str = sessionStorage.getItem("user") || "{}"
    this.personForm = JSON.parse(str)
    this.load()
  },
  methods: {
    load() {
      this.loading = true
      request.get("/order", {
        params: {

          pageNum: this.currentPage,
          pageSize: this.pageSize,
          search: this.search,
          useId:this.personForm.id
        }
      }).then(res => {
        this.loading = false
        this.tableData = res.data.records
        this.total = res.data.total
      })

      request.get("/station/all").then(res => {
        this.stations = res.data
      })
      request.get("/pile/idle").then(res => {
        this.idlePiles = res.data
      })

    },

    reserve(){
      this.dialogVisible1 = true;
      this.form1 = {};
    },

    start() {
      this.dialogVisible = true
      this.form = {}
    },
    save() {
      this.form.userId = this.personForm.id
      this.form.username = this.personForm.username
      this.form.limitMoney = this.personForm.limitMoney
      if (this.form.id) {  // 更新,此种情况基本不存在
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
      } else {  // 新增订单
        request.post("/order/start", this.form).then(res => {
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
    pay(){
      this.dialogVisible2 = true;
      this.form1.amount = this.personForm.limitMoney;
      this.form1.payChannel = "账户支付";
    },
    reserveSave(){
      this.form1.userId = this.personForm.id
      this.form1.username = this.personForm.username
      if (this.form1.id) {  // 更新,此种情况基本不存在
        request.put("/order", this.form1).then(res => {
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
          this.dialogVisible2 = false  // 关闭弹窗
          this.dialogVisible1 = false  // 关闭弹窗
        })
      } else {

        // 新增订单
        request.post("/order/reserve", this.form1).then(res => {
          console.log(res)
          if (res.code === '0') {
            this.$message({
              type: "success",
              message: "新增成功"
            })
            this.personForm.account=res.data;
            sessionStorage.setItem("user", JSON.stringify(this.personForm))
            // 触发Layout更新用户信息
            this.$emit("userInfo")


          } else {
            this.$message({
              type: "error",
              message: res.msg
            })
          }

          this.load() // 刷新表格的数据
          let dead_line = (new Date()).getTime() + 180000;
          this.setEndTime(dead_line);
          this.dialogVisible2 = false  // 关闭弹窗
          this.dialogVisible1 = false  // 关闭弹窗
        })
      }
    },
    setEndTime(dead_line){
      let interval = setInterval(()=>{
        let date = dead_line - (new Date()).getTime();//获取剩余毫秒数
        console.log(date)
        if(date <= 0){
          // 预约时间结束，订单取消，返回金额，删除订单，修改充电桩状态
          request.delete("/order/cancel", this.form1).then(res => {
            console.log(res)
            if (res.code === '0') {
              this.$message({
                type: "success",
                message: "订单取消"
              })
              this.personForm.account=res.data;
              sessionStorage.setItem("user", JSON.stringify(this.personForm))
              // 触发Layout更新用户信息
              this.$emit("userInfo")


            } else {
              this.$message({
                type: "error",
                message: res.msg
              })
            }

            this.load() // 刷新表格的数据
            clearInterval(interval)
          })


        }else {
          let timeMinute,
              timeSecond;
          timeMinute = parseInt(date/1000/60% 60,10);
          timeMinute = "0"+timeMinute;
          timeSecond = parseInt(date/1000%60,10);
          if(timeSecond < 10){
            timeSecond = "0"+timeSecond;
          }
          this.countDownTime = timeMinute+":"+timeSecond;
        }
      },1000)
    },
    handleEdit(row) {
      this.form = JSON.parse(JSON.stringify(row))
      this.dialogVisible = true
    },

    handleEnd(row) {
      request.put("/order/end",row).then(res => {
        if (res.code === '0') {
          this.$message({
            type: "success",
            message: "更新状态成功"
          })
        } else {
          this.$message({
            type: "error",
            message: res.msg
          })
        }
        this.load()  // 更新状态之后重新加载表格的数据
      })
    },
    handleStart(row){
      request.put("/order/start",row).then(res => {
        if (res.code === '0') {
          this.$message({
            type: "success",
            message: "更新状态成功"
          })
        } else {
          this.$message({
            type: "error",
            message: res.msg
          })
        }
        this.load()  // 更新状态之后重新加载表格的数据
        clearInterval(this.interval)
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

<style scoped>

</style>