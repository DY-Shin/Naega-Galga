<template>
  <div class="container">
    <div>
      <span class="font-large font-semi-bold">설명회</span>
    </div>
    <div class="margin-top">
      <div class="flex">
        <!-- 설명회 정보-->
        <div v-if="meetingInfo.meetingIndex > 0" class="font-medium">
          {{ meetingInfo.explanationDate }}
        </div>
        <div v-else>일정이 없습니다</div>
        <!-- 내가 등록한 매물인가 -->
        <div class="flex-self">
          <el-button
            v-if="canAddExplanation"
            type="primary"
            :icon="Calendar"
            class="button-size"
            @click="onClickAddExplanation"
          />
          <el-button
            v-if="canDeleteExplanation"
            circle
            type="danger"
            :icon="Minus"
            class="button-size"
            @click="onClickDeleteExplanation"
          />
          <!-- 내가 등록한 매물 아님 -->
          <el-button
            v-if="meetingInfo.meetingIndex > 0 && canAddReservation"
            circle
            type="primary"
            :icon="Plus"
            class="button-size"
            @click="onClickReserveExplanation"
          />
          <el-button
            v-if="meetingInfo.meetingIndex > 0 && canDeleteReservation"
            type="danger"
            :icon="Minus"
            class="button-size"
            @click="onClickCancelReserveExplanation"
          />
        </div>
      </div>
    </div>
  </div>
  <explanation-add-dialog
    :is-show="dialogShow"
    @closeDialog="closeDialog"
  ></explanation-add-dialog>
</template>

<script lang="ts">
import { ref, reactive, computed, onUpdated } from "vue";
import { useStore } from "vuex";

import { ProductReservation } from "@/types/MeetingReservationType";
import {
  getExplanationInfo,
  addExplanationReservation,
  cancelReservation,
  deleteExplanation,
} from "@/api/explanationApi";
import ResponseStatus from "@/api/responseStatus";

import { Calendar, Minus, Plus } from "@element-plus/icons-vue";

import ExplanationAddDialog from "@/components/product/detail/ExplanationAddDialog.vue";
import { useRouter } from "vue-router";

export default {
  props: {
    productIndex: Number,
  },
  components: {
    ExplanationAddDialog,
  },
  setup(props) {
    const productIndexRef = ref(props.productIndex);
    const store = useStore();
    const router = useRouter();
    const myIndex = computed(() => store.getters["userStore/userIndex"]);

    const meetingInfo: ProductReservation = reactive({
      productIndex: productIndexRef.value,
      meetingIndex: -1,
      explanationDate: "일정이 없습니다",
      sellerIndex: -1,
      buyerIndex: null,
    });

    const dateToFormatString = (dateString: string): string => {
      const blankSplitlist = dateString.split(" ");
      const dateSplitList = blankSplitlist[0].split("-");
      const timeSplitList = blankSplitlist[1].split(":");

      const nowDate = new Date();
      const yearValue = parseInt(dateSplitList[0]);
      //오늘이랑 다른 년도면 표시 아니면 표시 안함
      const year = nowDate.getFullYear() !== yearValue ? `${yearValue}년` : "";

      const month = parseInt(dateSplitList[1]);
      const day = parseInt(dateSplitList[2]);

      const hour = parseInt(timeSplitList[0]);
      const minutes = parseInt(timeSplitList[1]);

      const str = `${year} ${month}월 ${day}일 ${hour}시 ${minutes}분 예정`;
      return str;
    };

    const getMeetingInfo = async () => {
      const response = await getExplanationInfo(meetingInfo.productIndex);
      const status = response.data.status;

      if (status === ResponseStatus.NoContent) {
        router.back();
        return;
      }

      const data = response.data;
      meetingInfo.meetingIndex = data.meetingIndex;
      meetingInfo.sellerIndex = data.sellerIndex;
      meetingInfo.buyerIndex = data.buyerIndex;
      if (data.reservedAt != null) {
        meetingInfo.explanationDate = dateToFormatString(data.reservedAt);
      }
    };
    getMeetingInfo();

    onUpdated(() => getMeetingInfo());

    const isMine = computed(() => myIndex.value === meetingInfo.sellerIndex);

    //내가 등록된 매물인데 설명회가 없는 경우 설명회 등록 가능
    const canAddExplanation = computed(
      () => isMine.value && meetingInfo.meetingIndex <= 0
    );

    const canDeleteExplanation = computed(
      () => isMine.value && meetingInfo.meetingIndex > 0
    );

    //구매자가 예약 삭제
    const canDeleteReservation = computed(
      () => !isMine.value && meetingInfo.buyerIndex === myIndex.value
    );

    //구매자가 예약 추가
    const canAddReservation = computed(
      () => !isMine.value && meetingInfo.buyerIndex !== myIndex.value
    );

    //click event
    const dialogShow = ref(false);

    //dialog on
    const onClickAddExplanation = () => {
      dialogShow.value = !dialogShow.value;
    };
    const closeDialog = () => {
      dialogShow.value = false;
    };

    //판매자가 설명회를 삭제
    const onClickDeleteExplanation = async () => {
      const response = await deleteExplanation(meetingInfo.meetingIndex);
      if (response.status === ResponseStatus.Ok) {
        meetingInfo.meetingIndex = -1;
      }
      if (response.status === ResponseStatus.NoContent) {
        alert("잘못된 요청입니다");
      }
    };

    //구매자가 설명회를 예약 목록에 추가
    const onClickReserveExplanation = async () => {
      const response = await addExplanationReservation(
        meetingInfo.productIndex
      );

      if (response.status === ResponseStatus.Created) {
        meetingInfo.buyerIndex = myIndex.value;
      }
      if (response.status === ResponseStatus.NoContent) {
        alert("잘못된 요청입니다");
      }
    };

    //구매자가 설명회 예약을 취소함
    const onClickCancelReserveExplanation = async () => {
      const response = await cancelReservation(meetingInfo.meetingIndex);
      if (response.status === ResponseStatus.Ok) {
        meetingInfo.buyerIndex = -1;
      }
    };

    return {
      //icon
      Calendar,
      Minus,
      Plus,
      //values
      meetingInfo,
      isMine,
      canAddExplanation,
      canDeleteExplanation,
      canAddReservation,
      canDeleteReservation,
      //click event
      onClickAddExplanation,
      dialogShow,
      closeDialog,
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
