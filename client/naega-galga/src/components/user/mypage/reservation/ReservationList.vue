<template>
  <h1>예약 목록</h1>
  <hr />
  <!-- {{ reservateList.all }} -->
  <meeting-item
    v-for="meeting in reservateList.meeting"
    :key="meeting.meetingIndex"
    :meeting="meeting"
  />
  <hr />
  <explanation-item
    v-for="explanation in reservateList.explanation"
    :key="explanation.meetingIndex"
    :explanation="explanation"
  />
</template>

<script lang="ts">
import { getReservateProduct } from "@/api/userApi";
import ResponseStatus from "@/api/responseStatus";
import { defineComponent, reactive } from "vue";
// import MeetingItem from "./MeetingItem.vue";
// import ExplanationItem from "./explanationItem.vue";

export default defineComponent({
  name: "ReservationList",
  components: {
    // MeetingItem,
    // ExplanationItem,
  },
  setup() {
    const makeReservateProduct = async () => {
      const response = await getReservateProduct();
      const data = response.data;

      if (response.status == ResponseStatus.Ok) {
        // console.log(data);
        // reservateList.all.push(data);
        data.forEach(item => {
          if (item.type === "Meeting") {
            reservateList.meeting.push(item);
            return;
          }
          reservateList.explanation.push(item);
        });
      }
    };

    makeReservateProduct();

    const reservateList: ReservateList = reactive({
      meeting: [],
      explanation: [],
      all: [],
    });

    interface ReservateList {
      meeting: Array<any>;
      explanation: Array<any>;
      all: Array<any>;
    }

    return {
      makeReservateProduct,
      reservateList,
    };
  },
});
</script>
