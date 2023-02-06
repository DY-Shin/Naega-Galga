<template>
  <div class="image-list">
    <product-image-list
      :imagePaths="productInfo.imagePaths"
    ></product-image-list>
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
    <product-info
      :product="productInfo.product"
      :building="productInfo.building"
      :seller="productInfo.seller"
    ></product-info>
    <h1 class="margin-top-large margin-bottom-large semi-bold">옵션</h1>
    <product-option-list
      :options="productInfo.options"
      class="margin-bottom-large"
    ></product-option-list>
  </div>
</template>

<script lang="ts">
import ProductImageList from "@/components/product/detail/ProductImageList.vue";
import ProductOptionList from "@/components/product/detail/ProductOptionList.vue";
import RightSummaryBox from "@/components/product/detail/RightSummaryBox.vue";
import ProductInfoComponent from "@/components/product/detail/ProductInfo.vue";
import { computed, onMounted, reactive } from "vue";
import { useRoute, useRouter } from "vue-router";
import { deleteProduct, getProduct } from "@/api/productApi";
import ResponseStatus from "@/api/responseStatus";
// import { useStore } from "vuex";

export default {
  components: {
    ProductImageList,
    ProductOptionList,
    RightSummaryBox,
    ProductInfo: ProductInfoComponent,
  },
  setup() {
    const router = useRouter();
    const route = useRoute();
    // const store = useStore();
    // const userIndex = store.getters["userStore/userIndex"];
    const userIndex = 1;

    const productIndex = parseInt(route.params.id[0]);
    //TODO : userStore 정리되면 적용
    const isMine = computed(() => productInfo.seller.userIndex === userIndex);

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

    onMounted(async () => {
      try {
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
      } catch (error) {
        alert("서버 오류로 실행할 수 없습니다\n잠시 후 다시 시도 해주세요");
      }
    });

    const summaryValue = computed(() => ({
      productIndex: productIndex,
      productType: productInfo.product.productType,
      price: productInfo.product.productPrice,
      floor: productInfo.product.productFloor,
      managePrice: productInfo.product.productManageCost,
      sellerIndex: productInfo.seller.userIndex,
      sellerId: productInfo.seller.userId,
      //TODO : 예약 완료하고 여기 값 넣을것
      explanationDate: "2022.01.31",
    }));

    const moveToEdit = () => {
      router.push(`/product/edit/${productIndex}`);
    };
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
      if (status === ResponseStatus.InternalServerError) {
        alert("서버 오류로 실행할 수 없습니다");
      }
    };

    return {
      productInfo,
      summaryValue,
      moveToEdit,
      onClickDeleteProduct,
      isMine,
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
