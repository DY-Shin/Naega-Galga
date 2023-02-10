<template>
  <el-dialog
    v-model="isShowRef"
    title="설명회 일정 등록"
    class="z-index-high"
    destroy-on-close
    draggable
    @closed="closeDialog"
  >
    <el-config-provider :locale="locale">
      <el-calendar v-model="selectedDate" class="margin-top"></el-calendar>
    </el-config-provider>
    <div class="select-box-flex">
      <div>
        <el-select
          v-model="selectedTimeOption"
          class="select-width margin-right"
        >
          <el-option
            v-for="(option, index) in timeOptionList"
            :key="index"
            :value="option"
          ></el-option>
        </el-select>
        <el-select v-model="selectedHour" class="select-width">
          <el-option
            v-for="(hour, index) in hoursList"
            :key="index"
            :value="hour"
          ></el-option>
        </el-select>
        <span class="select-text">시</span>
        <el-select v-model="selectedMinutes" class="select-width">
          <el-option
            v-for="(minute, index) in minutesList"
            :key="index"
            :value="minute"
          ></el-option>
        </el-select>
        <span class="select-text">분</span>
      </div>
      <el-button
        type="primary"
        class="flex-self"
        @click="onClickAddExplanation"
      >
        등록
      </el-button>
    </div>
  </el-dialog>
</template>

<script lang="ts">
import { toRef, ref } from "vue";
import { useRoute } from "vue-router";

import { addExplanation } from "@/api/explanationApi";
import ResponseStatus from "@/api/responseStatus";

import ko from "element-plus/dist/locale/ko.mjs";

export default {
  props: {
    isShow: {
      type: Boolean,
    },
  },
  setup(props, context) {
    const route = useRoute();
    const productIndex = parseInt(route.params.id[0]);
    const isShowRef = toRef(props, "isShow");
    const { emit } = context;

    const closeDialog = () => {
      emit("closeDialog");
    };

    //date
    const selectedDate = ref(new Date());

    //time
    const timeOptionList = ["오전", "오후"];
    const selectedTimeOption = ref("오전");
    const hoursList = [...Array(12).keys()].map(i => i + 1);
    const selectedHour = ref(hoursList[0]);
    const minutesList = [0, 10, 20, 30, 40, 50];
    const selectedMinutes = ref(minutesList[0]);

    const addPadStart = (time: number) => time.toString().padStart(2, "0");

    //설명회 등록
    const onClickAddExplanation = async () => {
      const date = selectedDate.value;
      const year = date.getFullYear();
      const month = addPadStart(date.getMonth() + 1);
      const day = addPadStart(date.getDate());

      const hour: string | number =
        selectedTimeOption.value === "오전"
          ? addPadStart(selectedHour.value)
          : selectedHour.value + 12;
      const minutes = addPadStart(selectedMinutes.value);

      const dateString = `${year}-${month}-${day} ${hour}:${minutes}:00.000`;

      const response = await addExplanation(productIndex, dateString);

      if (response.status === ResponseStatus.Created) {
        closeDialog();
      }
      if (response.status === ResponseStatus.NoContent) {
        alert("잘못된 요청입니다");
      }
    };

    return {
      isShowRef,
      locale: ko,
      closeDialog,
      //date
      selectedDate,
      //time
      timeOptionList,
      selectedTimeOption,
      hoursList,
      selectedHour,
      minutesList,
      selectedMinutes,
      onClickAddExplanation,
    };
  },
};
</script>

<style scoped>
.z-index-high {
  z-index: 100;
}

.select-box-flex {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  margin: 0 3%;
}

.select-width {
  width: 80px;
}

.select-text {
  margin-left: 5px;
  margin-right: 20px;
}

.flex-self {
  justify-self: end !important;
}

.margin-top {
  margin-top: -40px;
}

.margin-right {
  margin-right: 20px;
}
.el-calendar {
  background-color: var(--el-bg-color);
}

.el-calendar-table td.is-today {
  background-color: var(--el-color-black);
  color: var(--el-color-white);
}
</style>
