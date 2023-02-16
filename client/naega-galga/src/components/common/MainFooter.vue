<template>
  <div v-if="isFooterShow" class="footer margin-top">
    <h3>SSAFY 8기 D106 공통 프로젝트</h3>
    <div class="margin-bottom font-small">기간 : 2023.01.03 ~ 2023.02.17</div>
    <div>
      <span>박재희</span>
      <span>백소원</span>
      <span>신동윤</span>
      <span>오민준</span>
      <span>이준</span>
      <span>이흥종</span>
    </div>
  </div>
</template>
<script lang="ts">
import { defineComponent, ref, computed } from "vue";
import { useStore } from "vuex";

export default defineComponent({
  setup() {
    const isFooterShow = ref(true);
    const store = useStore();
    const isLogin = computed(() => store.getters["userStore/isLogin"]);

    return {
      isFooterShow,
      isLogin,
    };
  },
  watch: {
    $route(to) {
      if (
        to.path === "/join" ||
        to.path === "/login" ||
        to.path.includes("/meeting") ||
        (to.path === "/" && this.isLogin)
      ) {
        this.isFooterShow = false;
        return;
      }
      this.isFooterShow = true;
    },
  },
});
</script>

<style scoped>
.footer {
  background-color: var(--el-color-primary-light-5);
  padding: 5vh;
  padding-right: 8vw;
  padding-left: 8vw;
  color: var(--el-color-black);
}

.footer span {
  margin-right: 15px;
  font-size: var(--el-font-size-large);
}

.font-small {
  font-size: var(--el-font-size-small);
}

.margin-bottom {
  margin-bottom: 15px;
}

.margin-top {
  margin-top: 10vh;
}
</style>
