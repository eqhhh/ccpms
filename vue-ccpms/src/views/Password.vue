<template>
  <div style="padding: 10px;width: 100%">

    <div style=" padding-left:5px; padding-top:7px; padding-bottom:5px">
      <el-breadcrumb
          style="font-size: 90%; "
          separator-icon="ArrowRight"
      >
        <el-breadcrumb-item :to="{ path: '/' }">主页</el-breadcrumb-item>
        <el-breadcrumb-item>修改密码</el-breadcrumb-item>
      </el-breadcrumb>
    </div>


    <el-card style="width: 40%; margin: 10px;">
      <el-form ref="form" :model="form" label-width="80px" :rules="rules">
        <el-form-item
            label="原密码："
            prop="password"
            label-width="120px"
        >
          <el-input v-model="form.password" show-password></el-input>
        </el-form-item>
        <el-form-item
            label="新密码："
            prop="newPass"
            label-width="120px"
        >
          <el-input v-model="form.newPass" show-password></el-input>
        </el-form-item>
        <el-form-item
            label="确认新密码："
            prop="confirmPass"
            label-width="120px"
        >
          <el-input v-model="form.confirmPass" show-password></el-input>
        </el-form-item>
      </el-form>

      <div style="text-align: center">
        <el-button type="primary" @click="changePsss">保存</el-button>
      </div>
    </el-card>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "Password.vue",
  data() {
    return {
      form: {
        password: '',
        newPass: '',
        confirmPass: ''
      },
      rules: {
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'},
        ],
        newPass: [
          {required: true, message: '请输入新密码', trigger: 'blur'},
        ],
        confirmPass: [
          {required: true, message: '请确认新密码', trigger: 'blur'},
        ],
      },
    }
  },
  methods: {
    changePsss() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          if (!this.form.newPass === this.form.confirmPass) {
            this.$message.error('2次输入新密码必须一致')
            return
          }
          let user = JSON.parse(sessionStorage.getItem("user"))
          this.form.userId = user.id
          request.put("/user/pass", this.form).then(res => {
            if (res.code === '0') {
              this.$message.success('修改成功')
              this.$router.push("/login")
            } else {
              this.$message.error(res.msg)
            }
          })
        }
      })
    }
  }
}
</script>

<style scoped>

</style>
