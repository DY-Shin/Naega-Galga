<template>
  <h1>매물 정보 입력</h1>
  <div class="guid-text">가격은 만원 단위로 입력해주세요</div>
  <!-- 월세 전세 라디오 -->
  <hr />
  <el-row>
    <el-col :span="6">
      <div class="mb-2 flex items-center text-sm">
        <el-radio-group v-model="productTypeRadio" class="ml-4">
          <el-radio :label="'월세'" size="large">월세</el-radio>
          <el-radio :label="'전세'" size="large">전세</el-radio>
        </el-radio-group>
      </div>
    </el-col>
    <el-col :span="18"></el-col>
  </el-row>
  <el-row>
    <div class="flex-vertical">
      <el-input v-model="deposit" placeholder="보증금" type="number" />
      <span v-if="isMonth" class="slash"> / </span>
      <el-input
        v-if="isMonth"
        v-model="price"
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
      <el-input v-model="roomSize" type="number" class="margin-right-small">
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
        v-model="selectedRoomDirection"
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
        <el-input v-model="maxFloor" placeholder="전체 층수" type="number" />
        <span class="slash"> / </span>
        <el-input
          v-if="isMonth"
          v-model="productFloor"
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
        v-model="selectedProductType"
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
        v-model="parking"
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
        <el-radio-group v-model="canAnimalRadio" class="ml-4">
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
      <el-input
        v-model="roadAddress"
        placeholder="주소"
        class="el-full-width margin-right-small"
      ></el-input>
      <el-input
        v-model="detailAddress"
        placeholder="상세주소"
        class="margin-right-small"
      ></el-input>
      <address-search-button
        @getRoadAddress="setRoadAddress"
      ></address-search-button>
    </el-col>
  </el-row>
  <hr />
  <!-- 옵션 -->
  <el-row>
    <el-col :span="6" class="text-align"><span>옵션</span></el-col>
    <el-col :span="18">
      <el-checkbox-group v-model="selectedOptionList">
        <el-checkbox v-for="option in optionList" :key="option" :label="option">
        </el-checkbox>
      </el-checkbox-group>
    </el-col>
  </el-row>
  <hr />
  <el-row>
    <el-col :span="20"></el-col>
    <el-col :span="4">
      <el-button type="info" size="large" @click="registProduct">
        등록
      </el-button>
    </el-col>
  </el-row>
</template>

<script lang="ts">
import { ref } from "vue";
import { computed, defineComponent } from "@vue/runtime-core";
import AddressSearchButton from "@/components/common/AddressSearchButton.vue";

export default defineComponent({
  components: {
    AddressSearchButton,
  },
  setup() {
    //월세, 전세 처리
    const productContractTypeRadio = ref("월세");
    const deposit = ref("");
    const price = ref("");
    // const depositAndPrice = computed(() => `${deposit.value}/${price.value}`);

    const numberInputDefaultValue = computed(value => {
      value === 0 ? "" : value;
    });

    //월세면 true 전세면 false
    const isMonth = computed(() =>
      productContractTypeRadio.value === "월세" ? true : false
    );

    //관리비
    const managePrice = ref("");

    //방 사이즈
    const roomSize = ref("");

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
    const selectedRoomDirection = ref("");

    //층수
    const maxFloor = ref();
    const productFloor = ref();
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
    const selectedProductType = ref("");

    //주차
    const parking = ref(0);

    //반려동물 여부
    const canAnimalRadio = ref("불가능");

    //주소
    const roadAddress = ref("");
    const detailAddress = ref("");
    const setRoadAddress = (address: string) => {
      roadAddress.value = address;
    };

    //옵션
    const selectedOptionList = ref([]);
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

    const registProduct = () => {
      //등록
    };

    return {
      //전, 월세
      productTypeRadio: productContractTypeRadio,
      deposit,
      price,
      isMonth,
      numberInputDefaultValue,
      //관리비
      managePrice,
      //방 크기
      roomSize,
      //방 방향
      roomDirectionOptions,
      selectedRoomDirection,
      //방 층수
      maxFloor,
      productFloor,
      //방 종류
      productTypeList,
      selectedProductType,
      //주차
      parking,
      //반려동물
      canAnimalRadio,
      //주소
      roadAddress,
      detailAddress,
      setRoadAddress,
      //옵션
      optionList,
      selectedOptionList,
      //등록 버튼
      registProduct,
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
</style>
