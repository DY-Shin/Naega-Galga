<template>
  <div class="container">
    <div>
      <span class="font-large font-semi-bold">설명회</span>
    </div>
    <div class="margin-top">
      <div class="flex">
        <!-- 설명회 정보-->
        <div class="font-medium">
          {{ meetingInfo.explanationDate }}
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
  <explanation-add-dialog
    :is-show="dialogShow"
    @closeDialog="closeDialog"
  ></explanation-add-dialog>
</template>

<script lang="ts">
import { ref, reactive, computed } from "vue";
import { ProductReservation } from "@/types/MeetingReservationType";
import { Calendar, Minus, Plus } from "@element-plus/icons-vue";
import { useStore } from "vuex";
import ExplanationAddDialog from "@/components/product/detail/ExplanationAddDialog.vue";
import { getExplanationInfo } from "@/api/explanationApi";
import ResponseStatus from "@/api/responseStatus";

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

      if (response.status === ResponseStatus.NoContent) {
        return;
      }
      if (response.status === ResponseStatus.InternalServerError) {
        alert("서버 오류로 실행할 수 없습니다");
      }
      const data = response.data;
      meetingInfo.meetingIndex = data.meetingIndex;
      meetingInfo.sellerIndex = data.sellerIndex;
      meetingInfo.buyerIndex = data.buyerIndex;
      if (meetingInfo.explanationDate !== null) {
        meetingInfo.explanationDate = dateToFormatString(data.reservedAt);
      }
    };
    getMeetingInfo();

    const isMine = computed(() => myIndex.value === meetingInfo.sellerIndex);

    //DB에 등록된 설명회가 있는지
    const isRegisteredExplanation = computed(
      () => meetingInfo.meetingIndex === -1
    );
    //내가 등록된 매물인데 설명회가 없는 경우 설명회 등록 가능
    const canAddExplanation = computed(
      () => isMine.value && !isRegisteredExplanation.value
    );

    //구매자일 경우 내가 예약 추가한 설명회인지
    const isReserved = computed(
      () => !isMine.value && meetingInfo.buyerIndex === myIndex.value
    );

    const canAddReservation = computed(
      () => isRegisteredExplanation.value && !isReserved.value
    );

    //click event
    const dialogShow = ref(false);
    const onClickAddExplanation = () => {
      dialogShow.value = !dialogShow.value;
    };
    const closeDialog = () => {
      dialogShow.value = false;
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
      canAddReservation,
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
