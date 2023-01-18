<template>
  <h1>찜 목록</h1>
  <hr />
  <el-row>
    <el-col
      id="card_set"
      v-for="(o, index) in 10"
      :key="o"
      :span="8"
      :offset="index > 10 ? 2 : 0"
    >
      <wish-list-item :wish="wish" />
    </el-col>
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
    interface Wish {
      cost: string;
      floor: string;
      cost2: string;
      area: string;
    }
    const wish: Wish = {
      cost: "월세 1000/50",
      floor: "2층",
      cost2: "3만원",
      area: "27㎡",
    };
    return {
      wish,
    };
  },
});
</script>

<style scoped>
#card_set {
  padding-top: 20px;
  padding-bottom: 10px;

  padding-left: 15px;
  padding-right: 15px;
}
</style>
