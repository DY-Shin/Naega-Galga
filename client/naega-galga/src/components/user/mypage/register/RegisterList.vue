<template>
  <h1>등록된 매물</h1>
  <hr />
  <el-row :gutter="20">
    <register-list-item
      v-for="regist in registList"
      :key="regist.id"
      :regist="regist"
    />
  </el-row>
  <button @click="getRegist">매물 목록 가져와짐?</button>
</template>

<script lang="ts">
import apiTokenInstance from "@/api/apiTokenInstance";
import { defineComponent } from "vue";
import RegisterListItem from "./RegisterListItem.vue";

export default defineComponent({
  name: "RegisterList",
  components: {
    RegisterListItem,
  },
  setup() {
    const registList: any = [
      {
        id: 1,
        rent: "월세 500/30",
      },
      {
        id: 2,
        rent: "2번",
      },
      {
        id: 3,
        rent: "33333",
      },
      {
        id: 4,
        rent: "4번",
      },
    ];

    const getRegist = () => {
      apiTokenInstance
        .post(`api/products`)
        .then(res => {
          console.log(res);
        })
        .catch(err => {
          console.log(err);
        });
    };

    return {
      registList,
      getRegist,
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
