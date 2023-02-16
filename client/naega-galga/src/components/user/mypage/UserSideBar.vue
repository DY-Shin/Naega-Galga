<template>
  <div class="sidebar">
    <div
      :class="{ select: isSelect == 1 }"
      class="sidebar-item"
      @click="goToMyPage"
    >
      <p class="sidebar-p-tag">내 정보</p>
    </div>
    <div
      :class="{ select: isSelect == 2 }"
      class="sidebar-item"
      @click="goToReservation"
    >
      <p class="sidebar-p-tag">예약 목록</p>
    </div>
    <div
      :class="{ select: isSelect == 3 }"
      class="sidebar-item"
      @click="goToRegister"
    >
      <p class="sidebar-p-tag">등록한 매물</p>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref } from "vue";
import { useRouter } from "vue-router";

export default defineComponent({
  name: "UserSideBar",
  setup() {
    const router = useRouter();

    const isSelect = ref(1);

    const goToMyPage = () => {
      isSelect.value = 1;
      router.push({ path: "/user" });
    };

    const goToReservation = () => {
      isSelect.value = 2;
      router.push({ name: "ReservationList" });
    };

    const goToRegister = () => {
      isSelect.value = 3;
      router.push({ name: "RegisterList" });
    };

    return { goToMyPage, goToReservation, goToRegister, isSelect };
  },
});
</script>

<style scoped>
.sidebar {
  font-size: 30px;
}
.select {
  color: white;
  background-color: #909399;
  border-radius: 10px;
}
.sidebar-item {
  align-items: center;
  height: 40px;
  display: flex;
  margin-bottom: 5px;
}
.sidebar-p-tag {
  margin: 0;
  margin-left: 10px;
  height: 30px;
}
</style>
