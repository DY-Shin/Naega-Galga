<template>
  <product-image-input
    @fileAdd="fileAdd"
    @fileDelete="fileDelete"
    :fileList="fileList"
  ></product-image-input>
  <h1>매물 정보 입력</h1>
  <div class="guid-text">가격은 만원 단위로 입력해주세요</div>
  <!-- 월세 전세 라디오 -->
  <hr />
  <el-row>
    <el-col :span="6">
      <div class="mb-2 flex items-center text-sm">
        <el-radio-group
          v-model="productInfo.productContractTypeRadio"
          class="ml-4"
        >
          <el-radio :label="'월세'" size="large">월세</el-radio>
          <el-radio :label="'전세'" size="large">전세</el-radio>
        </el-radio-group>
      </div>
    </el-col>
    <el-col :span="18"></el-col>
  </el-row>
  <el-row>
    <div class="flex-vertical">
      <el-input
        v-model="productInfo.deposit"
        placeholder="보증금"
        type="number"
      />
      <span v-if="isMonth" class="slash"> / </span>
      <el-input
        v-if="isMonth"
        v-model="productInfo.price"
        placeholder="월세"
        type="number"
      />
    </div>
  </el-row>
  <hr />
  <!-- 방 크기 -->
  <el-row>
    <el-col :span="6" class="text-align"><span>방 크기</span></el-col>
    <el-col :span="18">
      <el-input
        v-model="productInfo.roomSize"
        type="number"
        class="margin-right-small"
      >
      </el-input>
      <span>m<sup>2</sup></span>
    </el-col>
  </el-row>
  <hr />
  <!-- 방 방향 -->
  <el-row>
    <el-col :span="6" class="text-align"><span>방 방향</span></el-col>
    <el-col :span="18">
      <el-select
        v-model="productInfo.selectedRoomDirection"
        class="m-2"
        placeholder="선택하기"
      >
        <el-option
          v-for="option in roomDirectionOptions"
          :key="option"
          :value="option"
        />
      </el-select>
    </el-col>
  </el-row>
  <hr />
  <!--층수-->
  <el-row>
    <el-col :span="6" class="text-align"><span>층수</span></el-col>
    <el-col :span="18">
      <div class="flex-vertical">
        <el-input
          v-model="productInfo.maxFloor"
          placeholder="전체 층수"
          type="number"
        />
        <span class="slash"> / </span>
        <el-input
          v-if="isMonth"
          v-model="productInfo.productFloor"
          placeholder="매물의 층수"
          type="number"
        />
      </div>
    </el-col>
  </el-row>
  <hr />
  <!-- 매물 종류 -->
  <el-row>
    <el-col :span="6" class="text-align"><span>매물 종류</span></el-col>
    <el-col :span="18">
      <el-select
        v-model="productInfo.selectedProductType"
        class="m-2"
        placeholder="선택하기"
      >
        <el-option
          v-for="option in productTypeList"
          :key="option"
          :value="option"
        />
      </el-select>
    </el-col>
  </el-row>
  <hr />
  <!-- 주차 -->
  <el-row>
    <el-col :span="6" class="text-align"><span>주차</span></el-col>
    <el-col :span="18">
      <el-input
        v-model="productInfo.parking"
        class="margin-right-small"
        type="number"
      />
      <span>대</span>
    </el-col>
  </el-row>
  <hr />
  <!-- 반려동물 -->
  <el-row>
    <el-col :span="6" class="text-align"><span>반려동물</span></el-col>
    <el-col :span="18">
      <div class="mb-2 flex items-center text-sm">
        <el-radio-group v-model="productInfo.canAnimalRadio" class="ml-4">
          <el-radio :label="'가능'" size="large">가능</el-radio>
          <el-radio :label="'불가능'" size="large">불가능</el-radio>
          <el-radio :label="'확인필요'" size="large">확인필요</el-radio>
        </el-radio-group>
      </div>
    </el-col>
  </el-row>
  <hr />
  <el-row>
    <el-col :span="6" class="text-align"><span>엘리베이터</span></el-col>
    <el-col :span="18">
      <div class="mb-2 flex items-center text-sm">
        <el-radio-group v-model="productInfo.elevatorRadio" class="ml-4">
          <el-radio :label="'있음'" size="large">있음</el-radio>
          <el-radio :label="'없음'" size="large">없음</el-radio>
        </el-radio-group>
      </div>
    </el-col>
  </el-row>
  <hr />
  <el-row>
    <el-col :span="6" class="text-align"><span>주소</span></el-col>
    <el-col :span="18">
      <el-row>
        <el-col :span="10">
          <el-input
            v-model="productInfo.roadAddress"
            placeholder="도로명 주소"
            class="padding-right-small el-width-100"
          ></el-input>
        </el-col>
        <el-col :span="10">
          <el-input
            v-model="productInfo.jibunAddress"
            placeholder="지번 주소"
            class="padding-right-small el-width-100"
          ></el-input>
        </el-col>
        <el-col :span="4">
          <address-search-button
            @getRoadAddress="setRoadAddress"
          ></address-search-button>
        </el-col>
      </el-row>
      <el-row style="margin-top: 10px">
        <el-input
          v-model="productInfo.buildingName"
          placeholder="건물 이름"
          class="margin-right-small"
          type="info"
        ></el-input>
        <el-input
          v-model="productInfo.productHo"
          placeholder="방 호수"
          class="margin-right-small"
          type="info"
        ></el-input>
      </el-row>
    </el-col>
  </el-row>
  <hr />
  <!-- 옵션 -->
  <el-row>
    <el-col :span="6" class="text-align"><span>옵션</span></el-col>
    <el-col :span="18">
      <el-checkbox-group v-model="productInfo.selectedOptionList">
        <el-checkbox v-for="option in optionList" :key="option" :label="option">
        </el-checkbox>
      </el-checkbox-group>
    </el-col>
  </el-row>
  <hr />
  <el-row>
    <el-col :span="14"></el-col>
    <el-col :span="10" class="align-right">
      <div v-if="!isEditMode">
        <el-button type="info" @click="onClickAdd">등록</el-button>
      </div>
      <div v-else>
        <el-button type="info" @click="onClickEdit">수정</el-button>
        <el-button type="warning" @click="cancelEditProduct">취소</el-button>
      </div>
    </el-col>
  </el-row>
</template>

<script lang="ts">
import { computed, defineComponent, reactive } from "@vue/runtime-core";
import ProductImageInput from "@/components/product/input/ProductImageInput.vue";
import AddressSearchButton from "@/components/common/AddressSearchButton.vue";
import { useRoute } from "vue-router";
import router from "@/router";
import type { UploadFile } from "element-plus";
import { addProduct, editProduct } from "@/api/productApi";
import ResponseStatus from "@/api/responseStatus";

export default defineComponent({
  components: {
    ProductImageInput,
    AddressSearchButton,
  },
  setup() {
    const fileList: Array<UploadFile> = reactive([]);

    const fileAdd = (file: UploadFile): void => {
      fileList.push(file);
    };
    const fileDelete = (fileName: string): void => {
      //파일 이름을 찾아서 삭제
      fileList.forEach((file, index) => {
        if (fileName === file.name) {
          fileList.splice(index, 1);
        }
      });
    };

    interface Product {
      imageList: Array<UploadFile>;
      productContractTypeRadio: string;
      deposit: string;
      price: string;
      managePrice: string;
      roomSize: number;
      selectedRoomDirection: string;
      maxFloor: string;
      productFloor: string;
      selectedProductType: string;
      parking: number;
      canAnimalRadio: string;
      elevatorRadio: string;
      roadAddress: string;
      jibunAddress: string;
      buildingName: string;
      productHo: string;
      selectedOptionList: Array<string>;
    }
    //mode
    const route = useRoute();
    const isEditMode = route.params.id ? true : false;

    //월세, 전세 처리
    const productInfo: Product = reactive({
      imageList: [],
      productContractTypeRadio: "월세",
      deposit: "",
      price: "",
      managePrice: "",
      roomSize: 0,
      selectedRoomDirection: "남향",
      maxFloor: "",
      productFloor: "",
      selectedProductType: "원룸",
      parking: 0,
      canAnimalRadio: "가능",
      elevatorRadio: "있음",
      roadAddress: "",
      jibunAddress: "",
      buildingName: "싸피빌라",
      productHo: "302",
      selectedOptionList: [],
    });

    //id값이 있으면 서버에서 product값을 받아온다
    if (isEditMode) {
      //productInfo.imageList = [값];
      productInfo.productContractTypeRadio = "월세";
      const priceValue = "1000/50";
      const priceArray = priceValue.split("/");
      productInfo.deposit = priceArray[0];
      productInfo.price = priceArray[1];
      productInfo.managePrice = "5";
      productInfo.roomSize = 20.5;
      productInfo.selectedRoomDirection = "남향";
      const floorValue = "10층/3층";
      const floorArray = floorValue.split("/");
      productInfo.maxFloor = floorArray[0].replace("층", "");
      productInfo.productFloor = floorArray[1].replace("층", "");
      productInfo.selectedProductType = "원룸";
      productInfo.parking = 1;
      productInfo.canAnimalRadio = "가능";
      productInfo.elevatorRadio = "있음";
      productInfo.roadAddress = "구미시 진평길 13-3";
      productInfo.jibunAddress = "구미시 진평동 182";
      productInfo.buildingName = "싸피빌라";
      productInfo.productHo = "302";
      productInfo.selectedOptionList = ["세탁기", "인덕션"];
    }
    const depositAndPrice = computed(
      () => `${productInfo.deposit}/${productInfo.price}`
    );

    const numberInputDefaultValue = computed(value => {
      value === 0 ? "" : value;
    });

    const stringToBooleanInt = (trueValue, value): number =>
      value === trueValue ? 1 : 0;

    //월세면 true 전세면 false
    const isMonth = computed(() =>
      productInfo.productContractTypeRadio === "월세" ? true : false
    );

    //방 방향
    const roomDirectionOptions = [
      "북",
      "북동",
      "동",
      "동남",
      "남",
      "남서",
      "서",
      "북서",
    ];

    //매물 종류
    const productTypeList = [
      "원룸",
      "투룸",
      "아파트",
      "주택",
      "오피스텔",
      "미니투룸",
    ];
    const setRoadAddress = (address: string) => {
      productInfo.roadAddress = address;
    };

    //옵션
    const optionList = [
      "냉장고",
      "에어컨",
      "세탁기",
      "가스레인지",
      "인덕션",
      "전자레인지",
      "와이파이",
      "책상",
      "옷장",
      "싱크대",
      "침대",
    ];

    const makeObjForRequest = (): FormData => {
      // eslint-disable-next-line @typescript-eslint/no-explicit-any
      const formData: any = new FormData();

      const options: string[] = [];
      productInfo.selectedOptionList.forEach(item => options.push(item));
      const files: Array<UploadFile> = [];
      fileList.forEach((item: UploadFile) => files.push(item));

      const floor = `${productInfo.maxFloor}층/${productInfo.productFloor}층`;

      const product = {
        productPhoto: files,
        productType: productInfo.productContractTypeRadio,
        productPrice: depositAndPrice,
        productManageCost: productInfo.managePrice,
        productSize: productInfo.roomSize,
        productDirection: productInfo.selectedRoomDirection,
        productFloor: floor,
        productRooms: productInfo.selectedProductType,
        productAnimal: productInfo.canAnimalRadio,
        productDetail: productInfo.productHo,
      };
      formData.append("product", product);

      const building = {
        buildingParking: productInfo.parking,
        buildingRoadAddress: productInfo.roadAddress,
        buildingJibunAddress: productInfo.jibunAddress,
        buildingName: productInfo.buildingName,
        buildingElevator: stringToBooleanInt("있음", productInfo.elevatorRadio),
      };
      formData.append("building", building);

      const option = {
        optionAirConditioner: 0,
        optionFridge: 0,
        optionWashingMachine: 0,
        optionGasStove: 0,
        optionInduction: 0,
        optionMicrowave: 0,
        optionDesk: 0,
        optionWifi: 0,
        optionCloset: 0,
        optionBed: 0,
      };
      formData.append("option", option);

      return formData;
    };

    const onClickAdd = async () => {
      const data = makeObjForRequest();
      data.append("userIndex", "1");
      //등록
      const status = await addProduct(data);
      if (status === ResponseStatus.Ok) {
        router.back();
      }
      if (status === ResponseStatus.InternalServerError) {
        alert("서버 오류로 처리할 수 없습니다");
      }
    };

    const onClickEdit = async () => {
      //수정 요청
      const data = makeObjForRequest();
      const productId: number = parseInt(route.params.id[0]);
      const status = await editProduct(data, productId);
      if (status === ResponseStatus.Ok) {
        router.back();
      }
      if (status === ResponseStatus.InternalServerError) {
        alert("서버 오류로 처리할 수 없습니다");
      }
    };

    const cancelEditProduct = () => {
      router.back();
    };

    return {
      fileList,
      fileAdd,
      fileDelete,
      productInfo,
      isMonth,
      numberInputDefaultValue,
      roomDirectionOptions,
      productTypeList,
      setRoadAddress,
      optionList,
      isEditMode,
      onClickAdd,
      onClickEdit,
      cancelEditProduct,
    };
  },
});
</script>

<style scoped>
.flex-vertical {
  display: flex;
  flex-direction: row;
  align-items: center;
}

.el-input {
  width: 150px;
}
.el-full-width {
  width: 300px;
}

.el-width-100 {
  width: 100%;
}

.slash {
  padding: 0 10px;
  font-size: var(--el-font-size-large);
}

.guid-text {
  font-size: var(--el-font-size-extra-small);
  margin-bottom: 40px;
}

hr {
  background-color: var(--el-color-info-light-9);
  opacity: 0.3;
  margin: 20px 0;
}

.margin-right-small {
  margin-right: 10px;
}

.padding-right-small {
  padding-right: 10px;
}

.el-col.text-align {
  display: flex;
  flex-direction: row;
  align-items: center;
}

.flex-column {
  display: flex;
  flex-direction: column;
  align-items: center;
}
span {
  align-self: center;
}

.el-col > span {
  font-size: var(--el-font-size-small);
}

.align-right {
  display: flex;
  flex-direction: row;
  justify-content: end;
}
</style>
