<template>
  <product-image-input
    @fileAdd="fileAdd"
    @fileDelete="fileDelete"
    :fileList="fileList"
  ></product-image-input>
  <product-info-input />
</template>

<script lang="ts">
import { defineComponent, reactive } from "@vue/runtime-core";
import ProductImageInput from "@/components/product/input/ProductImageInput.vue";
import ProductInfoInput from "@/components/product/input/ProductInfoInput.vue";
import type { UploadFile } from "element-plus";

export default defineComponent({
  components: {
    ProductImageInput,
    ProductInfoInput,
  },
  setup() {
    const fileList: Array<UploadFile> = reactive([]);

    const fileAdd = (file: UploadFile): void => {
      fileList.push(file);
      console.log(fileList);
    };
    const fileDelete = (fileName: string): void => {
      //파일 이름을 찾아서 삭제
      fileList.forEach((file, index) => {
        if (fileName === file.name) {
          fileList.splice(index, 1);
        }
      });
      console.log(fileList);
    };
    return {
      fileList,
      fileAdd,
      fileDelete,
      ProductImageInput,
      ProductInfoInput,
    };
  },
});
</script>
