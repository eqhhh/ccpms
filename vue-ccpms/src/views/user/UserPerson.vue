<template>
  <div>
    <!--    头部-->
      <UserHeader :user="user"/>

      <!--    主体-->
      <div style="display: flex">
        <!--      内容区域-->
        <el-scrollbar style="width: 100vw">
          <Person style="flex: 1;height: calc(100vh - 70px)" @userInfo="refreshUser"/>
        </el-scrollbar>
      </div>
  </div>
</template>

<script>

import Person from "@/views/Person";
import request from "@/utils/request";
import UserHeader from "@/views/user/UserHeader";

export default {
  name: "UserPerson.vue",
  components: {
    UserHeader,
    Person,
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