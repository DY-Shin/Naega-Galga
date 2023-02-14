<template>
  <h1>등록한 매물</h1>
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

    return {
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
