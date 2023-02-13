<template>
  <div class="wrapper">
    <div style="width: 33%">
      <div style="font-size: 50px; margin-bottom: 20px">회원 탈퇴</div>

      <div style="margin-bottom: 10px; width: 80%">비밀번호를 입력하세요</div>
      <el-input
        type="password"
        v-model="passwordForm.password"
        style="width: 80%; margin-bottom: 5px"
        @keyup.enter="confirmPassword"
      />
      <el-button @click="confirmPassword" style="width: 20%; margin-bottom: 5px"
        >비밀번호 확인</el-button
      >
      <el-input style="width: 80%; margin-bottom: 5px; visibility: hidden" />
      <el-button
        type="danger"
        style="width: 20%"
        @click="deleteAccount"
        v-if="isChecked == true"
        >탈퇴하기</el-button
      >
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, reactive, ref } from "vue";
import { useRouter } from "vue-router";

import { checkPassword } from "@/api/userApi";
import { userDelete } from "@/api/userApi";
import ResponseStatus from "@/api/responseStatus";

export default defineComponent({
  name: "UserDeleteView",
  setup() {
    const router = useRouter();

    const passwordForm = reactive({
      password: "",
    });

    let isChecked = ref(false);

    const confirmPassword = async () => {
      const response = await checkPassword(passwordForm);
      const data = response.data;

      if (response.status === ResponseStatus.Ok) {
        isChecked.value = data;
      }
    };

    const deleteAccount = async () => {
      const response = await userDelete(passwordForm);

      if (response.status === ResponseStatus.Ok) {
        router.push({ path: "/" });
      }
    };

    return { passwordForm, confirmPassword, deleteAccount, isChecked };
  },
});
</script>

<style scoped>
.wrapper {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 49vh;
}
</style>
