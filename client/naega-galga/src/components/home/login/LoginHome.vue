<template>
  <div class="common-layout">
    <el-container>
      <main-side @address="sendAddress" @productList="sendList"></main-side>
      <el-container :style="{ height: 'calc(100vh - 150px)' }">
        <ka-kao-map
          :GetAddress="addressProxy"
          :GetList="listProxy"
        ></ka-kao-map>
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
    const addressProxy = ref("");
    const sendAddress = (address: string) => {
      addressProxy.value = address;
    };

    let listProxy = ref([]);
    const sendList = (list: []) => {
      listProxy.value.splice(0);
      for (let i = 0; i < list.length; i++) {
        listProxy.value.push(list[i]);
      }
      for (let i = 0; i < list.length; i++) {
        console.log("listProxy length! " + listProxy.value.length);
      }
    };

    // interface Product {
    //   productType: string;
    //   productName: string;
    //   price: string;
    //   managePrice: number;
    //   floor: string;
    //   roomSize: number;
    //   address: string;
    //   roomDirection: string;
    //   animal: boolean;
    //   seller?: string;
    //   explanationDate: string | null;
    //   parking: number;
    //   options: Array<string>;
    //   isFavorite: boolean;
    // }

    return {
      sendAddress,
      addressProxy,
      sendList,
      listProxy,
    };
  },
});
</script>

<style scoped></style>
