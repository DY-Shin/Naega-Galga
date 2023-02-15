<template>
  <el-col id="card_padding" :span="12">
    <el-card :body-style="{ padding: '20px' }">
      <!-- 구매자&판매자 공통 부분 -->
      <p class="meeting-time">{{ meetingTime }}</p>

      <!-- 내가 구매자인 경우 -->
      <div v-if="meetingItem.role == 'Guest'">
        <p>판매자 : {{ meetingItem?.owner?.userName }}</p>
        <p>연락처 : {{ meetingItem?.owner?.userPhone }}</p>
        <!-- <p>판매자가 파는 집 주소 : {{ meetingItem?.owner?.userAddress }}</p> -->
        <p v-if="meetingItem?.owner?.corporateRegistrationNumber != null">
          사업자번호 :
          {{ meetingItem?.owner?.corporateRegistrationNumber.slice(0, 3) }}-{{
            meetingItem?.owner?.corporateRegistrationNumber.slice(3, 5)
          }}-{{ meetingItem?.owner?.corporateRegistrationNumber.slice(5, 10) }}
        </p>
      </div>

      <!-- 내가 판매자인 경우 -->
      <div v-if="meetingItem.role == 'Owner'">
        <p>예약자 : {{ meetingItem?.guest?.userName }}</p>
        <p>예약자 연락처 : {{ meetingItem?.guest?.userPhone }}</p>
      </div>

      <!-- 구매자&판매자 공통 부분 -->
      <div class="button-wrapper">
        <el-button
          round
          class="meeting-button"
          type="primary"
          @click="goMeeting"
          >미팅하러가기</el-button
        >
      </div>
    </el-card>
  </el-col>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import { useRouter } from "vue-router";

export default defineComponent({
  name: "MeetingItem",
  props: {
    meeting: { type: Object },
  },

  setup(props) {
    const meetingItem = { ...props.meeting };
    const router = useRouter();

    const meetingTime = new Date(meetingItem.reserveAt).toLocaleString();

    const goMeeting = () => {
      router.push(`${meetingItem.meetingUrl}`);
    };
    return { meetingItem, goMeeting, meetingTime };
  },
});
</script>

<style scoped>
.meeting-time {
  text-align: center;
  margin-bottom: 30px;
}
.button-wrapper {
  text-align: center;
}

.meeting-button {
  width: 75%;
  display: inline-block;
  margin-top: 10px;
}
</style>
