<template>
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
        step="0.1"
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
    <el-col :span="6" class="text-align"><span>주소</span></el-col>
    <el-col :span="18" class="text-align">
      <div>
        <el-input
          v-model="productInfo.roadAddress"
          placeholder="도로명 주소"
          class="el-full-width margin-right-small margin-bottom-small"
        ></el-input>
        <el-input
          v-model="productInfo.jibunAddress"
          placeholder="지번 주소"
          class="el-full-width margin-right-small margin-bottom-small"
        ></el-input>
        <el-input
          v-model="productInfo.detailAddress"
          placeholder="상세주소"
          class="el-full-width margin-right-small"
          type="info"
        ></el-input>
      </div>
      <address-search-button
        @getRoadAddress="setRoadAddress"
        @getJibunAddress="setJibunAddress"
      ></address-search-button>
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
        <el-button type="info" @click="addProduct">등록</el-button>
      </div>
      <div v-else>
        <el-button type="info" @click="editProduct">수정</el-button>
        <el-button type="warning" @click="cancelEditProduct">취소</el-button>
      </div>
    </el-col>
  </el-row>
</template>

<script lang="ts">
import { computed, defineComponent, reactive } from "@vue/runtime-core";
import AddressSearchButton from "@/components/common/AddressSearchButton.vue";
import { useRoute } from "vue-router";
import router from "@/router";

export default defineComponent({
  components: {
    AddressSearchButton,
  },
  setup() {
    interface Product {
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
      roadAddress: string;
      jibunAddress: string;
      detailAddress: string;
      selectedOptionList: Array<string>;
    }
    //mode
    const route = useRoute();
    const isEditMode = route.params.id ? true : false;

    //월세, 전세 처리
    const productInfo: Product = reactive({
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
      roadAddress: "",
      jibunAddress: "",
      detailAddress: "",
      selectedOptionList: [],
    });

    //id값이 있으면 서버에서 product값을 받아온다
    if (isEditMode) {
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
      floorArray.forEach(item => item.replaceAll("층", ""));
      productInfo.deposit = floorArray[0];
      productInfo.price = floorArray[1];
      productInfo.maxFloor = "10";
      productInfo.productFloor = "3";
      productInfo.selectedProductType = "원룸";
      productInfo.parking = 1;
      productInfo.canAnimalRadio = "가능";
      productInfo.roadAddress = "구미시 진평길 13-3";
      productInfo.jibunAddress = "구미시 진평동 182";
      productInfo.detailAddress = "싸피빌라 302호";
      productInfo.selectedOptionList = ["세탁기", "인덕션"];
    }
    // const depositAndPrice = computed(() => `${deposit.value}/${price.value}`);

    const numberInputDefaultValue = computed(value => {
      value === 0 ? "" : value;
    });

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
    // const computedFloor = computed(
    //   () => `${maxFloor.value}층/${productFloor.value}층`
    // );

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
    const setJibunAddress = (address: string) => {
      productInfo.jibunAddress = address;
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

    const addProduct = () => {
      //등록
    };

    const editProduct = () => {
      //수정 요청
    };

    const cancelEditProduct = () => {
      router.back();
    };

    return {
      //전, 월세
      productInfo,
      isMonth,
      numberInputDefaultValue,
      roomDirectionOptions,
      productTypeList,
      setRoadAddress,
      setJibunAddress,
      optionList,
      isEditMode,
      addProduct,
      editProduct,
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

.margin-bottom-small {
  margin-bottom: 10px;
}

.el-col.text-align {
  display: flex;
  flex-direction: row;
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
