<template>
  <h2 style="margin-top: 60px">예약된 설명회</h2>
  <el-col id="card_padding" :span="12">
    <el-card :body-style="{ padding: '20px' }">
      <!-- 구매자&판매자 공통 부분 -->
      <p class="explanation-time">{{ explainTime }}</p>
      <p class="explanation-address">
        {{ explanationItem?.product?.productAddress }} 설명회
      </p>

      <!-- 내가 구매자인 경우 -->
      <div v-if="explanationItem.role == 'Guest'">
        <p>판매자 : {{ explanationItem?.owner?.userName }}</p>
        <p>판매자 연락처 : {{ explanationItem?.owner?.userPhone }}</p>
        <!-- <p>판매자의 부동산 주소 : {{ explanationItem?.owner?.userAddress }}</p> -->
        <p v-if="explanationItem?.owner?.corporateRegistrationNumber != null">
          사업자 주소 :
          {{
            explanationItem?.owner?.corporateRegistrationNumber.slice(0, 3)
          }}-{{
            explanationItem?.owner?.corporateRegistrationNumber.slice(3, 5)
          }}-{{
            explanationItem?.owner?.corporateRegistrationNumber.slice(5, 10)
          }}
        </p>
      </div>

      <!-- 내가 판매자인 경우 -->
      <div v-if="explanationItem.role == 'Owner'">
        <p>{{ explanationItem?.guestNumber }}명의 사람이 설명회 대기중</p>
      </div>

      <!-- 구매자&판매자 공통 부분 -->
      <p>건물 사진 : {{ explanation?.product?.productPhoto }}</p>
      <div class="button-wrapper">
        <el-button
          round
          class="meeting-button"
          type="success"
          @click="goExplanation"
          >설명회 참여하기</el-button
        >
      </div>
    </el-card>
  </el-col>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import { useRouter } from "vue-router";

export default defineComponent({
  name: "ExplanationItem",
  props: {
    explanation: { type: Object },
  },

  setup(props) {
    const router = useRouter();

    const explanationItem = { ...props.explanation };

    const explainTime = new Date(explanationItem.reserveAt).toLocaleString();

    const goExplanation = () => {
      router.push(`${explanationItem.meetingUrl}`);
    };

    return { explanationItem, goExplanation, explainTime };
  },
});
</script>
<style scoped>
.explanation-time {
  text-align: center;
  margin-bottom: 10px;
}
.explanation-address {
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
