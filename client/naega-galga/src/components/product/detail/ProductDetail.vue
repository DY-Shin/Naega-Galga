<template>
  <div class="image-list">
    <product-image-list></product-image-list>
  </div>
  <el-row>
    <el-col :span="16"></el-col>
    <el-col :span="8" class="align-right">
      <el-button type="info" size="large" @click="moveToEdit">수정</el-button>
      <el-button type="danger" size="large" @click="onClickDeleteProduct">
        삭제
      </el-button>
    </el-col>
  </el-row>
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
import { useRouter } from "vue-router";
import { deleteProduct } from "@/api/productApi";
import ResponseStatus from "@/api/responseStatus";

export default {
  components: {
    ProductImageList,
    ProductOptionList,
    RightSummaryBox,
    ProductInfo,
  },
  setup() {
    interface Product {
      productId: number;
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
      productId: 1,
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

    const router = useRouter();
    const moveToEdit = () => {
      router.push(`/product/edit/${product.productId}`);
    };
    const onClickDeleteProduct = async () => {
      if (!confirm("정말 삭제하시겠습니까?")) {
        return;
      }
      const status = await deleteProduct(product.productId);
      if (status === ResponseStatus.Ok) {
        alert("삭제되었습니다");
        router.back();
      }
      if (status === ResponseStatus.InternalServerError) {
        alert("서버 오류로 실행할 수 없습니다");
      }
    };

    return {
      product,
      summaryValue,
      moveToEdit,
      onClickDeleteProduct,
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
.align-right {
  display: flex;
  flex-direction: row;
  justify-content: end;
}
</style>
