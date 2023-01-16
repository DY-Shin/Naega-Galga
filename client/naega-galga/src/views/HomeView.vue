<template>
  <div class="common-layout">
    <login-home v-if="isLog"></login-home>
    <logout-home v-else></logout-home>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import LoginHome from "@/components/home/login/LoginHome.vue";
import LogoutHome from "@/components/home/logout/LogoutHome.vue";
export default defineComponent({
  data() {
    return { isLog: true };
  },
  components: { LoginHome, LogoutHome },
});
</script>
<style scoped>
.el-container {
  /* height: 100vh; */
}
</style>
