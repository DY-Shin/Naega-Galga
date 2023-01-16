<template>
  <div class="image-list">
    <product-image-list></product-image-list>
  </div>
  <div>
    <right-summary-box :summary-value="summaryValue"></right-summary-box>
    <h1 class="margin-bottom-large">매물 정보</h1>
    <product-info :info="product"></product-info>
    <h1 class="margin-top-large margin-bottom-large">옵션</h1>
    <product-option-list
      :options="product.options"
      class="margin-bottom-large"
    ></product-option-list>
  </div>
</template>

<script lang="ts">
import ProductImageList from "@/components/product/detail/ProductImageList.vue";
import ProductOptionList from "@/components/product/detail/ProductOptionList.vue";
import RightSummaryBox from "@/components/product/detail/RightSummaryBox.vue";
import ProductInfo from "@/components/product/detail/ProductInfo.vue";
import { computed } from "@vue/runtime-core";

export default {
  components: {
    ProductImageList,
    ProductOptionList,
    RightSummaryBox,
    ProductInfo,
  },
  setup() {
    interface Product {
      productType: string;
      productName: string;
      price: string;
      managePrice: number;
      floor: string;
      roomSize: number;
      address: string;
      roomDirection: string;
      animal: boolean;
      seller?: string;
      explanationDate: string | null;
      parking: number;
      options: Array<string>;
    }
    const product: Product = {
      productType: "월세",
      productName: "싸피빌라",
      price: "1000/30",
      managePrice: 5,
      floor: "10층/3층",
      roomSize: 29.5,
      address: "경상북도 구미시 진평동 13",
      roomDirection: "남향",
      animal: false,
      seller: "싸피부동산",
      explanationDate: "2023.1.30",
      parking: 0,
      options: ["에어컨", "냉장고"],
    };

    const summaryValue = computed(() => ({
      productType: product.productType,
      price: product.price,
      floor: product.floor,
      managePrice: product.managePrice,
      seller: product.seller,
      explanationDate: product.explanationDate,
    }));

    return {
      product,
      summaryValue,
    };
  },
};
</script>

<style scoped>
div > h2:nth-child(2) {
  margin-top: 10px !important;
}

.margin-top-large {
  margin-top: 100px;
}
.margin-top-medium {
  margin-top: 50px;
}

.margin-bottom-large {
  margin-bottom: 50px;
}
.image-list {
  display: flex;
  justify-content: center;
}
</style>
