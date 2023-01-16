<template>
  <product-image-input></product-image-input>
</template>

<script lang="ts">
import { defineComponent } from "@vue/runtime-core";
import ProductImageInput from "@/components/product/input/ProductImageInput.vue";

export default defineComponent({
  components: { ProductImageInput },
});
</script>
