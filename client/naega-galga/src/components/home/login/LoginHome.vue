<template>
  <div class="common-layout">
    <el-container>
      <main-side @addr_idx="sendIdx" @productList="sendList"></main-side>
      <el-container>
        <ka-kao-map
          :getIdx="idxProxy"
          :getList="listProxy"
          @chatUserIndex="sendUserIndex"
          @chatUserName="sendUserName"
          @chatOpen="sendChatOpen"
        ></ka-kao-map>
      </el-container>
    </el-container>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref } from "vue";
import MainSide from "@/components/home/login/MainSide.vue";
import KaKaoMap from "@/components/home/login/KaKaoMap.vue";
import { useStore } from "vuex";

export default defineComponent({
  name: "addressInfo",
  components: { MainSide, KaKaoMap },

  setup() {
    const store = useStore();
    store.commit("chatStore/CHANGE_CHATROOM_STATUS", false);

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
    const chatUserIndexProxy = ref();
    const sendUserIndex = (product: number) => {
      chatUserIndexProxy.value = product;
    };

    const chatUserNameProxy = ref();
    const sendUserName = (product: string) => {
      chatUserNameProxy.value = product;
    };

    const chatOpenProxy = ref(false);
    const sendChatOpen = (isOpen: boolean) => {
      chatOpenProxy.value = isOpen;
    };

    return {
      sendIdx,
      idxProxy,
      sendList,
      listProxy,
      sendUserIndex,
      sendChatOpen,
      chatOpenProxy,
      chatUserIndexProxy,
      sendUserName,
      chatUserNameProxy,
    };
  },
});
</script>

<style scoped>
.common-layout {
  overflow-y: hidden;
}
</style>
