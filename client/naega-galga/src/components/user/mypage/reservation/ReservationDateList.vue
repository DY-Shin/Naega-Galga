<template>
  <reservation-date-list-item />
  <h1>{{ reservationDateList.reserve_date }}</h1>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import ReservationDateListItem from "./ReservationDateListItem.vue";

export default defineComponent({
  name: "ReservationDateList",
  components: {
    ReservationDateListItem,
  },
  props: {
    reservation: { type: Object },
  },
  setup(props) {
    const reservationDateList = { ...props.reservation };
    return { reservationDateList };
  },
});
</script>
