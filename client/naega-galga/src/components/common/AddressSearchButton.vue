<template>
  <el-button type="primary" @click="addressSearch"> 주소 찾기 </el-button>
</template>

<script lang="ts">
import { defineComponent } from "@vue/runtime-core";
// eslint-disable-next-line @typescript-eslint/no-explicit-any
declare const daum: any;

import { ElButton } from "element-plus";

export default defineComponent({
  components: {
    ElButton,
  },
  setup(_, context) {
    const { emit } = context;

    function addressSearch(): void {
      new daum.Postcode({
        oncomplete: function (data) {
          // 팝업에서 검색결과 클릭시 data를 input에 넣음
          const { roadAddress, jibunAddress } = data; // 도로명 주소 변수
          //emit roadAddr
          emit("getRoadAddress", roadAddress);
          emit("getJibunAddress", jibunAddress);
        },
      }).open();
    }
    return {
      addressSearch,
    };
  },
});
</script>
