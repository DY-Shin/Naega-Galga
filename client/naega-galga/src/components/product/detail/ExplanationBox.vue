<template>
  <div class="container">
    <div>
      <span class="font-large font-semi-bold">설명회</span>
    </div>
    <div class="margin-top">
      <div class="flex">
        <!-- 설명회 정보-->
        <div class="font-medium">
          {{ explanationDateString }}
        </div>
        <!-- 내가 등록한 매물인가 -->
        <div v-if="isMine" class="flex-self">
          <el-button
            v-if="canAddExplanation"
            type="primary"
            :icon="Calendar"
            class="button-size"
            @click="onClickAddExplanation"
          />
          <el-button
            v-else
            circle
            type="danger"
            :icon="Minus"
            class="button-size"
            @click="onClickDeleteExplanation"
          />
        </div>
        <!-- 내가 등록한 매물 아님 -->
        <div v-if="!isMine && isRegisteredExplanation">
          <el-button
            v-if="canAddReservation"
            circle
            type="primary"
            :icon="Plus"
            class="button-size"
            @click="onClickCancelReserveExplanation"
          />
          <el-button
            v-else
            type="danger"
            :icon="Minus"
            class="button-size"
            @click="onClickReserveExplanation"
          />
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { ref, reactive, computed } from "vue";
import { ProductReservation } from "@/types/MeetingReservationType";
import { Calendar, Minus, Plus } from "@element-plus/icons-vue";
import { useStore } from "vuex";

export default {
  props: {
    productIndex: Number,
  },
  setup(props) {
    const productIndexRef = ref(props.productIndex);
    const store = useStore();
    const myIndex = computed(() => store.getters["userStore/userIndex"]);

    const meetingInfo: ProductReservation = reactive({
      productIndex: productIndexRef.value,
      meetingIndex: -1,
      explanationDate: "2022.02.01 11:00",
      sellerIndex: -1,
      buyerIndex: null,
    });

    //api로 meeting 정보 가져오는 부분 추가

    const isMine = computed(() => myIndex.value === meetingInfo.sellerIndex);

    //DB에 등록된 설명회가 있는지
    const isRegisteredExplanation = computed(
      () => meetingInfo.meetingIndex !== -1
    );
    //내가 등록된 매물인데 설명회가 없는 경우 설명회 등록 가능
    const canAddExplanation = computed(
      () => isMine.value && !isRegisteredExplanation.value
    );

    const explanationDateString = computed(() =>
      isRegisteredExplanation.value
        ? meetingInfo.explanationDate
        : "일정이 없습니다"
    );

    //구매자일 경우 내가 예약 추가한 설명회인지
    const isReserved = computed(
      () => !isMine.value && meetingInfo.buyerIndex === myIndex.value
    );

    const canAddReservation = computed(
      () => isRegisteredExplanation.value && !isReserved.value
    );

    //click event
    const onClickAddExplanation = () => {
      //
    };
    const onClickDeleteExplanation = () => {
      //
    };
    const onClickReserveExplanation = () => {
      //
    };
    const onClickCancelReserveExplanation = () => {
      //
    };

    return {
      //icon
      Calendar,
      Minus,
      Plus,
      //values
      meetingInfo,
      isMine,
      isRegisteredExplanation,
      canAddExplanation,
      explanationDateString,
      canAddReservation,
      //click event
      onClickAddExplanation,
      onClickDeleteExplanation,
      onClickReserveExplanation,
      onClickCancelReserveExplanation,
    };
  },
};
</script>

<style scoped>
.container {
  border: 1px solid var(--el-color-info);
  margin-top: 10px;
  padding: 20px;
  width: 250px;
  display: flex;
  flex-direction: column;
}

.flex {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
}
.margin-top {
  margin-top: 20px;
}

.font-large {
  font-size: var(--el-font-size-extra-large);
}
.font-medium {
  font-size: var(--el-font-size-medium);
}

.font-small {
  font-size: var(--el-font-size-small);
}

.font-semi-bold {
  font-weight: 600;
}

.button-size {
  width: 13px;
  height: 13px;
}
</style>
