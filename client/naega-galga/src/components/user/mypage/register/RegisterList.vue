<template>
  <div style="display: flex; justify-content: flex-start">
    <h1 style="margin-right: auto">등록한 매물</h1>
    <el-button @click="addProduct" type="primary" style="margin-top: 42px"
      >매물 등록하기</el-button
    >
  </div>

  <hr />
  <el-row :gutter="20">
    <register-list-item
      v-for="regist in registList.all"
      :key="regist.productIndex"
      :regist="regist"
    />
  </el-row>
</template>

<script lang="ts">
import { useRouter } from "vue-router";
import { getRegistProduct } from "@/api/userApi";
import ResponseStatus from "@/api/responseStatus";
import { defineComponent, reactive } from "vue";
import RegisterListItem from "./RegisterListItem.vue";

export default defineComponent({
  name: "RegisterList",
  components: {
    RegisterListItem,
  },
  setup() {
    const router = useRouter();

    const makeRegistProduct = async () => {
      const response = await getRegistProduct();
      const data = response.data;

      if (response.status === ResponseStatus.Ok) {
        registList.all = data;
      }
    };
    makeRegistProduct();

    const registList = reactive({
      all: [],
    });

    const addProduct = () => {
      router.push({ path: "/product/add" });
    };

    return {
      addProduct,
      makeRegistProduct,
      registList,
    };
  },
});
</script>

<style scoped>
#product_set {
  padding-top: 20px;
  padding-bottom: 10px;

  padding-left: 15px;
  padding-right: 15px;
}
</style>
