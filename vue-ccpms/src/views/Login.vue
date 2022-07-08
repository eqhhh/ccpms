<template>
  <div class="homepage-hero-module">
    <div class="video-container">
      <div :style="fixStyle" class="filter">

        <div style="float: right">
          <el-button type="text" @click="$router.push('/userLogin')">前往用户登录 >> </el-button>
        </div>
        <div style="width: 400px; margin: 100px auto">
          <div style="font-size: 30px; text-align: center; padding: 30px 0; color: #333">欢迎登录</div>

          <el-form ref="form" :model="form" size="normal" :rules="rules">
            <el-card>
            <el-form-item prop="username" style="padding-top:10px;">
              <el-input prefix-icon="el-icon-user-solid"
                  v-model="form.username"
                  placeholder="请输入用户名"
              >
              </el-input>
            </el-form-item>
            <el-form-item prop="password">
              <el-input prefix-icon="el-icon-lock"
                        v-model="form.password"
                        show-password placeholder="请输入密码"
              >
              </el-input>
            </el-form-item>
            <el-form-item>
              <div style="display: flex">
                <el-input prefix-icon="el-icon-key"
                          v-model="form.validCode"
                          style="width: 50%"
                          placeholder="请输入验证码"
                          ></el-input>
                <ValidCode @input="createValidCode" />
              </div>
            </el-form-item>
            <!--            <el-form-item>-->
            <!--              <el-radio v-model="form.role" :label="1" style="color: white">管理员</el-radio>-->
            <!--              <el-radio v-model="form.role" :label="2" style="color: white">普通用户</el-radio>-->
            <!--            </el-form-item>-->
            <el-form-item>
              <el-button style="width: 100%" type="primary" @click="login" @keyup.enter="login">登 录</el-button>
            </el-form-item>
            </el-card>
            <el-form-item style="padding-top:10px;"><el-button type="text" @click="$router.push('/register')">前往注册 >> </el-button></el-form-item>

          </el-form>

        </div>
      </div>


<!--
     背景视频

     <video :style="fixStyle" autoplay loop muted class="fillWidth" v-on:canplay="canplay">
        <source src="../assets/sea.mp4" type="video/mp4"/>
        浏览器不支持 video 标签，建议升级浏览器。
      </video>
      -->
    </div>
  </div>

</template>

<script>
import request from "@/utils/request";
import ValidCode from "@/components/ValidCode";
import {activeRouter} from "@/utils/permission";
export default {
  name: "Login.vue",
  components: {
    ValidCode,
  },
  data() {
    return {
      vedioCanPlay: false,
      fixStyle: '',
      form: {role: 1},
      rules: {
        username: [
          {required: true, message: '请输入用户名', trigger: 'blur'},
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'},
        ],
      },
      validCode: ''
      // 加背景图片
      // bg: {
      //   backgroundImage: "url(" + require("@/assets/bg.jpg") + ")",
      //   backgroundRepeat: "no-repeat",
      //   backgroundSize: "100% 100%"
      // }
    }
  },
  mounted() {

    // 绑定监听事件
    window.addEventListener("keydown", this.keyDown)
    sessionStorage.removeItem("user")

    window.onresize = () => {
      const windowWidth = document.body.clientWidth
      const windowHeight = document.body.clientHeight
      const windowAspectRatio = windowHeight / windowWidth
      let videoWidth
      let videoHeight
      if (windowAspectRatio < 0.5625) {
        videoWidth = windowWidth
        videoHeight = videoWidth * 0.5625
        this.fixStyle = {
          height: windowWidth * 0.5625 + 'px',
          width: windowWidth + 'px',
          'margin-bottom': (windowHeight - videoHeight) / 2 + 'px',
          'margin-left': 'initial'
        }
      } else {
        videoHeight = windowHeight
        videoWidth = videoHeight / 0.5625
        this.fixStyle = {
          height: windowHeight + 'px',
          width: windowHeight / 0.5625 + 'px',
          'margin-left': (windowWidth - videoWidth) / 2 + 'px',
          'margin-bottom': 'initial'
        }
      }
    }
    window.onresize()
  },
  methods: {
    canplay() {
      this.vedioCanPlay = true
    },
    // 接收验证码组件提交的 4位验证码
    createValidCode(data) {
      this.validCode = data
    },
    login() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          if (!this.form.validCode) {
            this.$message.error("请填写验证码")
            return
          }
          if (this.form.validCode.toLowerCase() !== this.validCode.toLowerCase()) {
            this.$message.error("验证码错误")
            return
          }
          request.post("/user/login", this.form).then(res => {
            if (res.code === '0') {
              this.$message({
                type: "success",
                message: "登录成功"
              })
              sessionStorage.setItem("user", JSON.stringify(res.data))  // 缓存用户信息

              // 登录成功的时候更新当前路由
              activeRouter()
              this.$router.push("/")  //登录成功之后进行页面的跳转，跳转到主页

            } else {
              this.$message({
                type: "error",
                message: res.msg
              })
            }
          })
        }
      })

    },
    // 点击回车键登录
    keyDown(e)
    {
      // 回车则执行登录方法 enter键的ASCII是13
      if (e.keyCode === 13) {
        this.login(); // 定义的登录方法
      }
    }
  },
  destroyed() {
    // 销毁事件
    window.removeEventListener("keydown", this.keyDown, false);
  },
}
</script>

<style scoped>
.homepage-hero-module,
.video-container {
  position: relative;
  height: 100vh;
  overflow: hidden;
}

.video-container .poster img{
  z-index: 0;
  position: absolute;
}

.video-container .filter {
  z-index: 1;
  position: absolute;
  /*background: rgba(0, 0, 0, 0.4);*/
  width: 100%;
}

.fillWidth {
  width: 100%;
}
</style>
