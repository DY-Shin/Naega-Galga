<template>
  <div class="sidebar">
    <div @click="goToMyPage">내 정보</div>
    <div @click="goToReservation">예약 목록</div>
    <div @click="goToRegister">등록된 매물</div>
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
    const goToWish = () => {
      router.push({ name: "WishList" });
    };
    const goToRegister = () => {
      router.push({ name: "RegisterList" });
    };

    return { goToMyPage, goToReservation, goToWish, goToRegister };
  },
});
</script>

<style scoped>
.sidebar {
  border-radius: 0.5rem;
  border: 2px solid;
  padding: 2rem;
}
</style>
