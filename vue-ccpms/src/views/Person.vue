<template>
  <div style="padding: 10px;width: 100%">

    <div style=" padding-left:5px; padding-top:7px; padding-bottom:5px">
      <el-breadcrumb
          style="font-size: 90%; "
          separator-icon="ArrowRight"
      >
        <el-breadcrumb-item :to="{ path: '/' }">主页</el-breadcrumb-item>
        <el-breadcrumb-item>个人信息</el-breadcrumb-item>
      </el-breadcrumb>
    </div>

    <el-card style="width: 40%; margin: 10px">
      <el-form ref="form" :model="form" label-width="80px">
        <el-form-item style="text-align: center" label-width="0">
          <el-upload
              class="avatar-uploader"
              action="http://localhost:9090/src/file"
              :show-file-list="false"
              :on-success="handleAvatarSuccess"
          >
            <img v-if="form.avatar" :src="form.avatar" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
        <el-form-item label="用户名：">
          <el-input v-model="form.username" disabled></el-input>
        </el-form-item>
        <el-form-item label="昵称：">
          <el-input v-model="form.nickName"></el-input>
        </el-form-item>
        <el-form-item label="性别：">
          <el-radio v-model="form.sex" label="男">男</el-radio>
          <el-radio v-model="form.sex" label="女">女</el-radio>
          <el-radio v-model="form.sex" label="未知">未知</el-radio>
        </el-form-item>
        <el-form-item label="年龄：">
          <el-input v-model="form.age" style="width: 10%"></el-input>
        </el-form-item>
        <el-form-item label="地址：">
          <el-input type="textarea" v-model="form.address"></el-input>
        </el-form-item>
        <el-form-item label="电话：">
          <el-input v-model="form.phone" ></el-input>
        </el-form-item>
        <el-form-item label="邮箱：">
          <el-input v-model="form.email" ></el-input>
        </el-form-item>
        <el-form-item label="余额(￥)：">
          <el-input v-model="form.account" disabled></el-input>
        </el-form-item>
        <el-form-item label="限额(￥)：">
          <el-input
              v-model="form.limitMoney"
          ></el-input>
        </el-form-item>
      </el-form>
      <div style="text-align: center">
        <el-button type="primary" @click="update">保存</el-button>
      </div>
    </el-card>

  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "Person",
  data() {
    return {
      form: {}
    }
  },
  created() {
    let str = sessionStorage.getItem("user") || "{}"
    this.form = JSON.parse(str)
    console.log(str)
  },
  methods: {
    handleAvatarSuccess(res) {
      this.form.avatar = res.data
      this.$message.success("上传成功")
      // this.update()
    },
    update() {
      request.put("/user", this.form).then(res => {
        console.log(res)
        if (res.code === '0') {
          this.$message({
            type: "success",
            message: "更新成功"
          })
          sessionStorage.setItem("user", JSON.stringify(this.form))
          // 触发Layout更新用户信息
          this.$emit("userInfo")
        } else {
          this.$message({
            type: "error",
            message: res.msg
          })
        }
      })
    }
  }
}
</script>

<style>
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 120px;
  height: 120px;
  line-height: 120px;
  text-align: center;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>
