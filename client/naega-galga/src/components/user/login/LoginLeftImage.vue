<template>
  <div>
    <el-image class="imagebox" fit:fill :src="url" />
  </div>
</template>
<script lang="ts">
import { defineComponent } from "vue";

export default defineComponent({
  name: "LoginLeftImage",

  setup() {
    const url =
      "https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg";

    return { url };
  },
});
</script>
<style>
.imagebox {
  width: 400px;
  height: 400px;

  border: 1px solid black;
}
</style>
