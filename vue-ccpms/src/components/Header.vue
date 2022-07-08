<template>
  <div
      style="height: 70px;
      line-height: 50px;
      padding-top:10px ;
      border-bottom: 1px solid #ccc;
      display: flex"
  >
    <div style="width: 500px; padding-left: 30px; font-size: 150% ; font-weight: bold; color: dodgerblue">校园充电桩管理系统</div>
    <div style="flex: 1"></div>
    <div style="padding-right: 30px;">
      <el-dropdown>
        <span class="el-dropdown-link">
          <el-avatar :size="30" :src="user.avatar" style="position: relative; top: 10px"></el-avatar>
           {{ user.nickName }}
          <el-icon><ArrowDown /></el-icon>
        </span>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item @click="$router.push('/person')">个人信息</el-dropdown-item>
            <el-dropdown-item @click="$router.push('/password')">修改密码</el-dropdown-item>
            <el-dropdown-item @click="$router.push('/login')">退出系统</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
  </div>
</template>

<script>
export default {
  name: "Header.vue",
  props: ['user'],
  data() {
    return {

    }
  },
  created() {
  }
}
</script>

<style scoped>

</style>