<template>
  <div>
    <!--    头部-->

    <UserHeader :user="user"/>

    <!--    主体-->
    <div style="display: flex">
      <!--      内容区域-->
      <el-scrollbar style="width: 100vw">
        <div style="flex: 1;height: calc(100vh - 70px)" @userInfo="refreshUser">
            <div style="padding: 10px;width: 100%">
              <el-card >
                <div style="margin: 10px 0">
                  <el-button type="primary" @click="$router.push('/userQueryPile')">充电桩定位</el-button>
                  <el-button type="primary" @click="$router.push('/userUsePile')">充电服务</el-button>
                  <el-button type="primary" @click="recharge">账户充值</el-button>
                </div>
                <el-card style="margin: 10px 0"
                         shadow ="never"
                >
                  <el-row>
                    <el-col :span="12">
                      <div >当前空闲充电桩总数量: {{ idleTotal }} </div>

                      <el-table
                          v-loading="loading"
                          :data="tableData"
                          border
                          stripe
                          style="width: 80%; padding-top: 20px">
                        <el-table-column
                            prop="stationId"
                            label="充电站ID"
                            sortable
                        >
                        </el-table-column>

                        <el-table-column
                            prop="stationName"
                            label="充电站名"
                            sortable
                        >
                        </el-table-column>

                        <el-table-column
                            prop="count"
                            label="空闲充电桩数量"
                            sortable
                        >
                        </el-table-column>

                      </el-table>
                    </el-col>

                    <el-col :span="12">

                    </el-col>
                  </el-row>

                </el-card>

                <el-dialog title="提示" v-model="dialogVisible" width="30%">
                  <el-tag type="warning" style="font-size: 100%">账户充值内容还未完善：可通过模拟充值进行充值</el-tag>
                  <el-row style="padding-top: 20px">

                    <el-input v-model="rechargeAmount" style="width: 80%" placeholder="请输入充值金额"></el-input>
                  </el-row>
                  <template #footer>
          <span class="dialog-footer">
            <el-button @click="dialogVisible = false">取消</el-button>
            <el-button type="primary" @click="rechargeSave(rechargeAmount)">确 定</el-button>
          </span>
                  </template>
                </el-dialog>
              </el-card>



            </div>
        </div>
      </el-scrollbar>
    </div>
  </div>
</template>

<script>

import Home from "@/views/Home";
import request from "@/utils/request";
import UserHeader from "@/views/user/UserHeader";

export default {
  name: "UserHome.vue",
  components: {
    UserHeader,
  },
  data() {
    return {
      user: {},
      rechargeAmount: '',
      loading: true,
      search: '',
      tableData: [],
      stations: [],
      idleTotal: 0,
      personForm: {},
      form: {},
      dialogVisible: false,
    }
  },
  created() {
    this.refreshUser();
    let str = sessionStorage.getItem("user") || "{}"
    this.personForm = JSON.parse(str)
    this.load()
  },
  methods: {
    refreshUser() {
      let userJson = sessionStorage.getItem("user");
      if (!userJson) {
        return
      }
      let userId = JSON.parse(userJson).id
      // 从后台取出更新后的最新用户信息
      request.get("/user/" + userId).then(res => {
        this.user = res.data
      })
    },
    load() {
      this.loading = true
      request.get("/station/all").then(res => {
        this.stations = res.data
      })
      request.get("/pile/idle").then(res => {
        this.idlePiles = res.data
      })
      request.get("/pile/idleTotal").then(res => {
        this.idleTotal = res.data
      })

      request.get("/station/countIdlePile").then(res => {
        this.tableData = res.data
      })
      this.loading = false

    },
    recharge() {
      this.dialogVisible = true
    },
    rechargeSave(rechargeAmount){

      if(rechargeAmount!=='') {
        request.put("/user/recharge/" + this.personForm.id + "/" + rechargeAmount).then(res => {
          console.log(res)
          if (res.code === '0') {
            this.$message({
              type: "success",
              message: "充值成功",
            })

            request.get("/user/" + this.personForm.id).then(res => {
              this.personForm.account = res.data.account
              console.log(res.data.account)
              console.log(this.personForm.account)

              sessionStorage.setItem("user", JSON.stringify(this.personForm))
              // 触发Layout更新用户信息
              this.$emit("userInfo")
            })


          } else {
            this.$message({
              type: "error",
              message: res.msg
            })
          }
        })
      }


      this.dialogVisible = false  // 关闭弹窗

    },
  }
}
</script>

<style scoped>

</style>