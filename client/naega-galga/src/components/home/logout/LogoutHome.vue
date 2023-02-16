<template>
  <div class="common-layout">
    <home-intro></home-intro>
  </div>
</template>
<script lang="ts">
import { defineComponent } from "vue";
import HomeIntro from "@/components/home/logout/HomeIntro.vue";

export default defineComponent({
  name: "HomeView",
  data() {
    return {};
  },
  components: { HomeIntro },
});
</script>
<style scoped>
.el-container {
  /* height: 100vh; */
}
</style>
