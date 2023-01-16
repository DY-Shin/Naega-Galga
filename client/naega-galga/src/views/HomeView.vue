<template>
  <div class="common-layout">
    <el-container v-if="isLog">
      <home-side></home-side>
      <el-container :style="{ height: 'calc(100vh - 150px)' }">
        <ka-kao-map></ka-kao-map>
      </el-container>
    </el-container>
    <home-intro v-else></home-intro>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import HomeSide from "@/components/home/HomeSide.vue";
import KaKaoMap from "@/components/home/KaKaoMap.vue";
import HomeIntro from "@/components/home/HomeIntro.vue";

export default defineComponent({
  name: "HomeView",
  data() {
    return {
      isLog: true,
    };
  },
  components: { HomeSide, KaKaoMap, HomeIntro },
});
</script>
<style scoped>
.el-container {
  /* height: 100vh; */
}
</style>
