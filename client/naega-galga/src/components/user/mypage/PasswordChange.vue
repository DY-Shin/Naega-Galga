<template>
  <div class="password_form">기존 비밀번호</div>
  <el-input
    v-model="password"
    type="password"
    placeholder="비밀번호를 입력해주세요."
    show-password
    clearable="true"
  />
  <div class="password_form">새 비밀번호</div>
  <el-input
    v-model="new_password"
    type="password"
    placeholder="새 비밀번호를 입력해주세요."
    show-password
  />
  <div class="password_form">새 비밀번호 확인</div>
  <el-input
    v-model="new_password_confirm"
    type="password"
    placeholder="새 비밀번호를 다시 입력해주세요."
    show-password
  />
</template>

<script lang="ts">
import { defineComponent, ref } from "vue";

export default defineComponent({
  name: "PasswordChange",

  setup() {
    const password = ref("");
    const new_password = ref("");
    const new_password_confirm = ref("");

    return { password, new_password, new_password_confirm };
  },
});
</script>

<style>
.password_form {
  font-size: small;

  margin-top: 10px;
  margin-bottom: 3px;
  margin-left: 5px;
}
</style>
