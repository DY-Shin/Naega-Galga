<template>
  <div class="image-list">
    <product-image-list
      :imagePaths="productInfo.imagePaths"
    ></product-image-list>
  </div>
  <el-row>
    <el-col :span="16"></el-col>
    <el-col v-if="isMine" :span="8" class="align-right">
      <el-button type="danger" size="large" @click="onClickDeleteProduct">
        삭제
      </el-button>
    </el-col>
  </el-row>
  <div>
    <div class="sticky-box">
      <right-summary-box :summary-value="summaryValue"></right-summary-box>
      <explanation-box :productIndex="productIndex"></explanation-box>
    </div>
    <h1 class="margin-bottom-large semi-bold">매물 정보</h1>
    <product-info-component
      :product="productInfo.product"
      :building="productInfo.building"
      :seller="productInfo.seller"
    ></product-info-component>
    <h1 class="margin-top-large margin-bottom-large semi-bold">옵션</h1>
    <product-option-list
      :options="productInfo.options"
      class="margin-bottom-large"
    ></product-option-list>
  </div>
</template>

<script lang="ts">
import { computed, reactive } from "vue";
import { useRouter } from "vue-router";
import { useStore } from "vuex";

import { deleteProduct, getProduct } from "@/api/productApi";
import ResponseStatus from "@/api/responseStatus";

import ProductImageList from "@/components/product/detail/ProductImageList.vue";
import ProductOptionList from "@/components/product/detail/ProductOptionList.vue";
import RightSummaryBox from "@/components/product/detail/RightSummaryBox.vue";
import ProductInfoComponent from "@/components/product/detail/ProductInfo.vue";
import ExplanationBox from "@/components/product/detail/ExplanationBox.vue";

export default {
  components: {
    ProductImageList,
    ProductOptionList,
    RightSummaryBox,
    ProductInfoComponent,
    ExplanationBox,
  },
  setup() {
    const router = useRouter();

    //store
    const store = useStore();
    const userIndex = store.getters["userStore/userIndex"];
    const isMine = computed(() => productInfo.seller.userIndex === userIndex);

    //get product info
    const str = window.location.href;
    const strList = str.split("/");
    let productIndex = parseInt(strList[strList.length - 1]);
    async function getProductDetail() {
      const response = await getProduct(productIndex);
      if (response.status === ResponseStatus.Ok) {
        //product 값 갱신
        const data = response.data;
        productInfo.seller = data.seller;
        productInfo.product = data.product;
        productInfo.building = data.building;
        productInfo.options = data.options;
        productInfo.imagePaths = data.imagePaths;
      }

      if (response.status === ResponseStatus.NoContent) {
        alert("정보를 찾을 수 없습니다");
        router.back();
      }
    }

    let productInfo = reactive({
      seller: {
        userIndex: -1,
        userId: "",
        userName: "",
      },
      building: {
        buildingPark: 0,
        buildingRoadAddress: "",
        buildingJibunAddress: "",
        buildingName: "",
        buildingElevator: 0,
      },
      product: {
        productType: "",
        productPrice: "",
        productManageCost: 0,
        productSize: "",
        productDirection: "",
        productFloor: "",
        productRooms: "",
        productAnimal: "",
        productDetail: "",
      },
      options: {
        optionAirConditioner: 0,
        optionFridge: 0,
        optionWashingMachine: 0,
        optionGasStove: 0,
        optionInduction: 0,
        optionMicrowave: 0,
        optionWifi: 0,
        optionDesk: 0,
        optionCloset: 0,
        optionBed: 0,
      },
      imagePaths: [],
    });
    getProductDetail();

    const summaryValue = computed(() => ({
      productIndex: productIndex,
      productType: productInfo.product.productType,
      price: productInfo.product.productPrice,
      floor: productInfo.product.productFloor,
      managePrice: productInfo.product.productManageCost,
      sellerIndex: productInfo.seller.userIndex,
      sellerId: productInfo.seller.userId,
    }));

    //on click event
    const onClickDeleteProduct = async () => {
      if (!confirm("정말 삭제하시겠습니까?")) {
        return;
      }
      const status = await deleteProduct(productIndex);
      if (status === ResponseStatus.Ok) {
        alert("삭제되었습니다");
        router.replace("/");
      }
      if (status === ResponseStatus.NoContent) {
        alert("잘못된 요청입니다");
      }
    };

    return {
      productInfo,
      productIndex,
      summaryValue,
      onClickDeleteProduct,
      isMine,
    };
  },
};
</script>

<style scoped>
.sticky-box {
  display: flex;
  flex-direction: column;
  position: sticky;
  float: right;
  top: 50px;
}

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
