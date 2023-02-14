<template>
  <h1>예약 목록</h1>
  <hr />
  <h2>예약된 미팅</h2>
  <el-row :gutter="20">
    <meeting-item
      v-for="meeting in reservateList.meeting"
      :key="meeting.meetingIndex"
      :meeting="meeting"
    />
  </el-row>

  <hr />

  <h2 style="margin-top: 60px">예약된 설명회</h2>
  <el-row :gutter="20">
    <explanation-item
      v-for="item in reservateList.explanation"
      :key="item.meetingIndex"
      :explanation="item"
    />
  </el-row>
</template>

<script lang="ts">
import { getReservateProduct } from "@/api/userApi";
import ResponseStatus from "@/api/responseStatus";
import { defineComponent, reactive } from "vue";
import MeetingItem from "./MeetingItem.vue";
import ExplanationItem from "./ExplanationItem.vue";

export default defineComponent({
  name: "ReservationList",
  components: {
    MeetingItem,
    ExplanationItem,
  },
  setup() {
    const makeReservateProduct = async () => {
      const response = await getReservateProduct();
      const data = response.data;

      if (response.status == ResponseStatus.Ok) {
        data.forEach(item => {
          if (item.type === "Meeting") {
            reservateList.meeting.push(item);
          } else {
            reservateList.explanation.push(item);
          }
        });
      }
    };
    makeReservateProduct();

    const reservateList: ReservateList = reactive({
      meeting: [],
      explanation: [],
    });

    interface ReservateList {
      meeting: Array<any>;
      explanation: Array<any>;
    }

    return {
      makeReservateProduct,
      reservateList,
    };
  },
});
</script>

<style scoped></style>
