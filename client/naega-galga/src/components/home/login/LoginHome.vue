<template>
  <div class="common-layout">
    <el-container>
      <main-side></main-side>
      <el-container :style="{ height: 'calc(100vh - 150px)' }">
        <ka-kao-map></ka-kao-map>
      </el-container>
    </el-container>
    <chat-icon></chat-icon>
  </div>
</template>
<script lang="ts">
import { defineComponent } from "vue";
import MainSide from "@/components/home/login/MainSide.vue";
import KaKaoMap from "@/components/home/login/KaKaoMap.vue";
import ChatIcon from "@/components/common/ChatWindow.vue";

export default defineComponent({
  data() {
    return {};
  },
  components: { MainSide, KaKaoMap, ChatIcon },
});
</script>
<style scoped></style>
