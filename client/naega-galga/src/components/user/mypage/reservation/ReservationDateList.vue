<template>
  <reservation-date-list-item />
</template>

<script lang="ts">
import { defineComponent } from "vue";
import ReservationDateListItem from "./ReservationDateListItem.vue";

export default defineComponent({
  name: "ReservationDateList",
  components: {
    ReservationDateListItem,
  },
});
</script>
