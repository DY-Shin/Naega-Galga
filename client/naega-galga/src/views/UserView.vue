<template>
  <div class="wrapper">
    <user-side-bar class="userSideBar" />
    <main class="main">
      <router-view />
    </main>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import UserSideBar from "@/components/user/mypage/UserSideBar.vue";

export default defineComponent({
  name: "UserView",
  components: {
    UserSideBar,
  },
});
</script>

<style scoped>
.wrapper {
  display: flex;
  justify-content: center;

  margin-top: 1rem;
  column-gap: 5rem;
}

.userSideBar {
  min-width: 180px;
  width: 10%;
}

.main {
  min-width: 540px;
  width: 40%;
}
</style>
