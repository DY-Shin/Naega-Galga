<template>
  <div class="common-layout">
    <login-home v-if="isLog"></login-home>
    <logout-home v-else></logout-home>
  </div>
</template>

<script lang="ts">
import { defineComponent, computed } from "vue";
import LoginHome from "@/components/home/login/LoginHome.vue";
import LogoutHome from "@/components/home/logout/LogoutHome.vue";
import { useStore } from "vuex";
export default defineComponent({
  components: { LoginHome, LogoutHome },

  setup() {
    const isLog = computed(() => store.getters["userStore/isLogin"]);
    const store = useStore();
    return { isLog };
  },
});
</script>
<style scoped></style>
