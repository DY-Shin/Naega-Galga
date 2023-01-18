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
import { computed } from "@vue/runtime-core";
import MainSide from "@/components/home/login/MainSide.vue";
import KaKaoMap from "@/components/home/login/KaKaoMap.vue";
import ChatIcon from "@/components/common/ChatWindow.vue";

export default defineComponent({
  components: { MainSide, KaKaoMap, ChatIcon },
  setup() {
    interface Product {
      productType: string;
      productName: string;
      price: string;
      managePrice: number;
      floor: string;
      roomSize: number;
      address: string;
      roomDirection: string;
      animal: boolean;
      seller?: string;
      explanationDate: string | null;
      parking: number;
      options: Array<string>;
      isFavorite: boolean;
    }
    const product: Product = {
      productType: "월세",
      productName: "싸피빌라",
      price: "1000/30",
      managePrice: 5,
      floor: "10층/3층",
      roomSize: 29.5,
      address: "경상북도 구미시 진평동 13",
      roomDirection: "남향",
      animal: false,
      seller: "싸피부동산",
      explanationDate: "2023.1.30",
      parking: 0,
      options: ["에어컨", "냉장고"],
      isFavorite: false,
    };
    const summaryValue = computed(() => ({
      productType: product.productType,
      price: product.price,
      floor: product.floor,
      managePrice: product.managePrice,
      seller: product.seller,
      explanationDate: product.explanationDate,
    }));

    return {
      product,
      summaryValue,
    };
  },
});
</script>
<style scoped></style>
