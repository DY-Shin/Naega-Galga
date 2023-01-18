<template>
  <reservation-date-list />
</template>

<script lang="ts">
import { defineComponent } from "vue";
import ReservationDateList from "./ReservationDateList.vue";

export default defineComponent({
  name: "ReservationList",
  components: {
    ReservationDateList,
  },
  setup() {
    interface Reservation {
      seller: string;
      time: string;
    }

    const reservation: Reservation = {
      seller: "부동부동산",
      time: "비밀입니다.",
    };
    return {
      reservation,
    };
  },
});
</script>
