<template>
  <el-button type="button" @click="addressSearch">상세주소 찾기</el-button>
</template>

<script lang="ts">
import { defineComponent } from "@vue/runtime-core";

export default defineComponent({
  setup(_, context) {
    const { emit } = context;

    function addressSearch(): void {
      // eslint-disable-next-line no-undef
      new daum.Postcode({
        oncomplete: function (data) {
          // 팝업에서 검색결과 클릭시 data를 input에 넣음
          const roadAddr = data.roadAddress; // 도로명 주소 변수
          //emit roadAddr
          emit("getRoadAddress", roadAddr);
        },
      }).open();
    }
    return {
      addressSearch,
    };
  },
});
</script>
