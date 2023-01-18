<template>
  <h1>등록된 매물</h1>
  <hr />
  <el-row>
    <el-col
      id="product"
      v-for="(o, index) in 10"
      :key="o"
      :span="8"
      :offset="index > 10 ? 2 : 0"
    >
      <register-list-item :regist="regist" />
    </el-col>
  </el-row>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import RegisterListItem from "./RegisterListItem.vue";

export default defineComponent({
  name: "RegisterList",
  components: {
    RegisterListItem,
  },
  setup() {
    interface Regist {
      rent: string;
    }
    const regist: Regist = {
      rent: "월세 500/30",
    };
    return {
      regist,
    };
  },
});
</script>
