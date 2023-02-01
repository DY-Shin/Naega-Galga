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
import { PropType, computed, toRef } from "vue";

export default {
  props: {
    imagePaths: {
      type: Array as PropType<string[]>,
    },
  },
  setup(props) {
    interface ImageData {
      src: string;
    }
    const imagePathsRef = toRef(props, "imagePaths");
    const imageList = computed(() => {
      const arr: ImageData[] = [];
      imagePathsRef.value?.forEach(path => {
        arr.push({
          src: `${process.env.VUE_APP_API_BASE_URL}image/display?path=${path}`,
        });
      });
      return arr;
    });

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
