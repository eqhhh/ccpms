<template>
  <div>
    <!--    头部-->
    <Header :user="user"/>

    <!--    主体-->
    <div style="display: flex">
      <!--      侧边栏-->
      <Aside />
      <!--      内容区域-->
      <el-scrollbar style="width: calc(100vw - 200px)">
        <router-view style="flex: 1;height: calc(100vh - 70px)" @userInfo="refreshUser"/>
      </el-scrollbar>
    </div>
  </div>
</template>

<script>

import Header from "@/components/Header";
import Aside from "@/components/Aside";
import request from "@/utils/request";

export default {
  name: "Layout.vue",
  components: {
    Header,
    Aside
  },
  data() {
    return {
      user: {}
    }
  },
  created() {
    this.refreshUser()
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
    }
  }
}
</script>

<style scoped>

</style>