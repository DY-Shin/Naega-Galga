<template>
  <div class="image-list">
    <product-image-list></product-image-list>
  </div>
  <el-row>
    <el-col :span="16"></el-col>
    <el-col :span="8" class="align-right">
      <el-button type="primary" size="large" @click="moveToEdit">
        수정
      </el-button>
      <el-button type="danger" size="large" @click="onClickDeleteProduct">
        삭제
      </el-button>
    </el-col>
  </el-row>
  <div>
    <right-summary-box :summary-value="summaryValue"></right-summary-box>
    <h1 class="margin-bottom-large semi-bold">매물 정보</h1>
    <product-info :info="product"></product-info>
    <h1 class="margin-top-large margin-bottom-large semi-bold">옵션</h1>
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
import { computed, onMounted } from "@vue/runtime-core";
import { useRoute, useRouter } from "vue-router";
import { deleteProduct, getProduct } from "@/api/productApi";
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
      productIndex: number;
      sellerIndex: number;
      productType: string;
      price: string;
      managePrice: number;
      roomSize: number;
      roomDirection: string;
      floor: string;
      roomType: string;
      parking: number;
      animal: string;
      elevator: boolean;
      sellerId: string;
      roadAddress: string;
      jibunAddress: string;
      buildingName: string;
      roomHo: string;
      options: Array<string>;
      isWish: boolean;
      explanationDate: string;
    }
    const router = useRouter();
    const route = useRoute();

    const product: Product = {
      productIndex: 1,
      sellerIndex: 1,
      productType: "월세",
      price: "1000/30",
      managePrice: 5,
      roomSize: 29.5,
      roomDirection: "남",
      floor: "10층/3층",
      roomType: "원룸",
      parking: 0,
      animal: "가능",
      elevator: false,
      sellerId: "싸피부동산",
      roadAddress: "경상북도 구미시 진평동 13",
      jibunAddress: "겅상북도 구미시 진평길 13-2",
      buildingName: "싸피빌라",
      roomHo: "302",
      options: ["에어컨", "냉장고"],
      isWish: false,
      explanationDate: "2023.1.20",
    };

    const userIndex = 1;

    onMounted(async () => {
      const productId = parseInt(route.params.id[0]);
      const response = await getProduct(productId);
      if (response.status === ResponseStatus.Ok) {
        //product 값 갱신
      }
      if (response.status === ResponseStatus.InternalServerError) {
        alert("서버 오류로 실행할 수 없습니다");
      }
    });

    const summaryValue = computed(() => ({
      productIndex: product.productIndex,
      productType: product.productType,
      price: product.price,
      floor: product.floor,
      managePrice: product.managePrice,
      sellerId: product.sellerId,
      sellerIndex: product.sellerIndex,
      explanationDate: product.explanationDate,
      isWish: product.isWish,
    }));

    const moveToEdit = () => {
      router.push(`/product/edit/${product.productIndex}`);
    };
    const onClickDeleteProduct = async () => {
      if (!confirm("정말 삭제하시겠습니까?")) {
        return;
      }
      const status = await deleteProduct(product.productIndex);
      if (status === ResponseStatus.Ok) {
        alert("삭제되었습니다");
        router.back();
      }
      if (status === ResponseStatus.NoContent) {
        alert("잘못된 요청입니다");
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
      isMine: product.sellerIndex === userIndex,
    };
  },
};
</script>

<style scoped>
div > h2:nth-child(2) {
  margin-top: 10px !important;
}

.margin-top-large {
  margin-top: 60px;
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

.semi-bold {
  font-weight: 600;
}
</style>
