<template>
  <h1>등록된 매물</h1>
  <hr />
  <!-- <div>{{ registList }}</div> -->
  <button @click="getRegistProduct">매물목록을 가져오는 버튼</button>
  <el-row :gutter="20">
    <register-list-item
      v-for="regist in registList.all"
      :key="regist.productIndex"
      :regist="regist"
    />
  </el-row>
</template>

<script lang="ts">
import apiTokenInstance from "@/api/apiTokenInstance";
import { defineComponent, reactive } from "vue";
import RegisterListItem from "./RegisterListItem.vue";

export default defineComponent({
  name: "RegisterList",
  components: {
    RegisterListItem,
  },
  setup() {
    const getRegistProduct = () => {
      apiTokenInstance
        .get(`api/users/me/products`)
        .then(res => {
          registList.all = res.data;
          console.log(res.data);
          console.log(registList.all);
        })
        .catch(err => {
          console.log(err);
        });
    };

    const registList = reactive({
      all: [],
    });

    return {
      getRegistProduct,
      registList,
    };
  },
});
</script>

<style>
#product_set {
  padding-top: 20px;
  padding-bottom: 10px;

  padding-left: 15px;
  padding-right: 15px;
}
</style>
