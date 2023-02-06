<template>
  <div style="font-size: 50px">회원 탈퇴</div>

  <div>비밀번호를 입력하세요</div>
  <el-input v-model="passwordForm.password" placeholder="" />
  <el-button @click="confirmPassword">비밀번호 확인</el-button>
  <el-button @click="deleteAccount" v-if="isChecked == true"
    >탈퇴버튼</el-button
  >
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

<style></style>
