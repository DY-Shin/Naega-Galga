<template>
  <h1>찜 목록</h1>
  <hr />
  <el-row :gutter="20">
    <wish-list-item v-for="wish in wishList" :key="wish.id" :wish="wish" />
  </el-row>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import WishListItem from "./WishListItem.vue";

export default defineComponent({
  name: "WishList",
  components: {
    WishListItem,
  },
  setup() {
    const wishList: any = [
      {
        id: 1,
        cost: "월세 1000/50",
        floor: "2층",
        cost2: "3만원",
        area: "27㎡",
      },
      {
        id: 2,
        cost: "전세 8000",
        floor: "5층",
        cost2: "1억원",
        area: "45㎡",
      },
      {
        id: 2,
        cost: "전세 8000",
        floor: "5층",
        cost2: "1억원",
        area: "45㎡",
      },
      {
        id: 2,
        cost: "전세 8000",
        floor: "5층",
        cost2: "1억원",
        area: "45㎡",
      },
    ];
    return {
      wishList,
    };
  },
});
</script>

<style></style>
