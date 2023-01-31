<template>
  <div class="common-layout">
    <el-container>
      <main-side @addr_idx="sendIdx" @productList="sendList"></main-side>
      <el-container>
        <ka-kao-map
          :GetIdx="idxProxy"
          :GetList="listProxy"
          @chatProduct="sendProduct"
        ></ka-kao-map>
      </el-container>
    </el-container>
    <chat-window :GetProductIndex="productProxy"></chat-window>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref } from "vue";
import MainSide from "@/components/home/login/MainSide.vue";
import KaKaoMap from "@/components/home/login/KaKaoMap.vue";
import ChatWindow from "@/components/common/ChatWindow.vue";

export default defineComponent({
  name: "addressInfo",
  components: { MainSide, KaKaoMap, ChatWindow },

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
    };
    const productProxy = ref();
    const sendProduct = (productIndex: number) => {
      console.log(productIndex);
      productProxy.value = productIndex;
    };

    return {
      sendIdx,
      idxProxy,
      sendList,
      listProxy,
      sendProduct,
      productProxy,
    };
  },
});
</script>

<style scoped></style>
