<template>
  <div id="mypage">
    <user-side-bar />
    <main>
      <div id="mainpage">
        <router-view />
      </div>
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

<style>
#mypage {
  height: flex;

  margin-top: 1rem;
  margin-left: 15rem;
  margin-right: 15rem;

  display: grid;
  grid-template-columns: 1fr 3fr;
  column-gap: 1rem;
}

#mainpage {
  margin: 2rem;
}

main {
  border: 2px solid;
  border-radius: 0.5rem;
}
</style>
