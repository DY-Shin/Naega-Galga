<template>
  <reservation-date-list
    v-for="reservation in reservationDate"
    :key="reservation.id"
    :reservation="reservation"
  />
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
    const reservationDate: any = [
      {
        id: 1,
        reserve_date: "1월 18일",
      },
      {
        id: 2,
        reserve_date: "1월 19일",
      },
      {
        id: 3,
        reserve_date: "1월 20일",
      },
    ];
    return {
      reservationDate,
    };
  },
});
</script>
