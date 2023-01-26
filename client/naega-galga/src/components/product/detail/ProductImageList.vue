<template>
  <div>
    <el-carousel
      :interval="5000"
      trigger="click"
      autoplay="false"
      type="card"
      class="carousel-width"
    >
      <el-carousel-item
        v-for="image in imageList"
        :key="image"
        initial-index="0"
      >
        <el-image
          class="justify-center full-width"
          :src="image.src"
          fit="scale-down"
        />
      </el-carousel-item>
    </el-carousel>
  </div>
</template>

<script lang="ts">
import { ref } from "@vue/reactivity";

export default {
  setup() {
    const imageList = ref([
      {
        src: "https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg",
      },
      {
        src: "https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg",
      },
      {
        src: "https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg",
      },
    ]);

    return {
      imageList,
    };
  },
};
</script>

<style scoped>
.carousel-width {
  width: 50vw;
}
</style>
