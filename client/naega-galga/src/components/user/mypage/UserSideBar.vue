<template>
  <div class="sidebar">
    <div style="margin-bottom: 10px" @click="goToMyPage">내 정보</div>
    <div style="margin-bottom: 10px" @click="goToReservation">예약 목록</div>
    <div style="margin-bottom: 10px" @click="goToRegister">등록한 매물</div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import { useRouter } from "vue-router";

export default defineComponent({
  name: "UserSideBar",
  setup() {
    const router = useRouter();

    const goToMyPage = () => {
      router.push({ path: "/user" });
    };
    const goToReservation = () => {
      router.push({ name: "ReservationList" });
    };
    const goToRegister = () => {
      router.push({ name: "RegisterList" });
    };

    return { goToMyPage, goToReservation, goToRegister };
  },
});
</script>

<style scoped>
.sidebar {
  font-size: 30px;
}

/* . */
</style>
