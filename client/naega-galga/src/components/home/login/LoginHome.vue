<template>
  <div class="common-layout">
    <el-container>
      <main-side @addr_idx="sendIdx" @productList="sendList"></main-side>
      <el-container>
        <ka-kao-map :GetIdx="idxProxy" :GetList="listProxy"></ka-kao-map>
      </el-container>
    </el-container>
    <chat-icon></chat-icon>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref } from "vue";
import MainSide from "@/components/home/login/MainSide.vue";
import KaKaoMap from "@/components/home/login/KaKaoMap.vue";
import ChatIcon from "@/components/common/ChatWindow.vue";

export default defineComponent({
  name: "addressInfo",
  components: { MainSide, KaKaoMap, ChatIcon },

  setup() {
    const idxProxy = ref();
    const sendIdx = (sendIdx: number) => {
      idxProxy.value = sendIdx;
    };
    let listProxy = ref([]);
    const sendList = (list: []) => {
      listProxy.value.splice(0);
      for (let i = 0; i < list.length; i++) {
        listProxy.value.push(list[i]);
      }

      for (let i = 0; i < list.length; i++) {
        console.log("listProxy " + listProxy[i]);
      }
    };

    return {
      sendIdx,
      idxProxy,
      sendList,
      listProxy,
    };
  },
});
</script>

<style scoped></style>
