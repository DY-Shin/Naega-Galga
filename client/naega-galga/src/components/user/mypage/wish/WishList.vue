<template>
  <h1>찜 목록</h1>
  <div>{{ allProduct }}</div>
  <div>{{ allProduct.productIndex }}</div>
  <button @click="getProduct">유저정보내놔</button>
  <hr />
  <el-row :gutter="20">
    <wish-list-item v-for="wish in wishList" :key="wish.id" :wish="wish" />
  </el-row>
</template>

<script lang="ts">
import { defineComponent, reactive } from "vue";
import WishListItem from "./WishListItem.vue";
import apiTokenInstance from "@/api/apiTokenInstance";

export default defineComponent({
  name: "WishList",
  components: {
    WishListItem,
  },
  setup() {
    const getProduct = () => {
      apiTokenInstance
        .get(`api/users/me/products`)
        .then(res => {
          allProduct.productIndex = res.data[0].productIndex;
        })
        .catch(err => {
          console.log(err);
        });
    };

    const allProduct = reactive({
      productIndex: "",
    });

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
      getProduct,
      allProduct,
    };
  },
});
</script>

<style></style>
