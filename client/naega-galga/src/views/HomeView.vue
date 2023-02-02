<template>
  <div class="common-layout">
    <login-home v-if="isLog"></login-home>
    <logout-home v-else></logout-home>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref, computed } from "vue";
import LoginHome from "@/components/home/login/LoginHome.vue";
import LogoutHome from "@/components/home/logout/LogoutHome.vue";
import { useStore } from "vuex";
export default defineComponent({
  components: { LoginHome, LogoutHome },

  setup() {
    const isLog = ref(true);

    const store = useStore();
    isLog.value = computed(() => store.getters["userStore/isLogin"]).value;
    return { isLog };
  },
});
</script>
<style scoped></style>
