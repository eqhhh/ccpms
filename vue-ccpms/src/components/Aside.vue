<template>
  <div>
    <el-menu
        style="width: 200px;height: calc(100vh - 70px)"
        :default-active="$route.path"
        router
        active-text-color="#ffd04b"
        background-color="#545c64"
        class="el-menu-vertical-demo"
        default-active="2"
        text-color="#fff"
    >
      <div
          v-for="m in user.permissions" :key="m.id">
        <el-menu-item
            :index="m.path"
            v-if="m.name !== 'Person' && m.name !== 'Password' && m.name !== 'QueryPile'&& m.name !== 'UsePile'"
        >
          <el-icon><component :is="m.icon"></component></el-icon>  {{ m.comment }}
        </el-menu-item>
      </div>
    </el-menu>
  </div>
</template>

<script>

export default {
  name: "Aside.vue",
  data() {
    return {
      user: {}
    }
  },
  created() {
    let userStr = sessionStorage.getItem("user") || "{}"
    this.user = JSON.parse(userStr)

    // 请求服务端，确认当前登录用户的 合法信息
    // request.get("/user/" + this.user.id).then(res => {
    //   if (res.code === '0') {
    //     this.user = res.data
    //   }
    // })
  }
}
</script>

<style scoped>

</style>