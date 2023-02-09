<template>
  <el-form
    :model="password_change_form"
    :rules="first_rules"
    status-icon
    scroll-to-error
  >
    <el-form-item prop="password">
      <p>기존 비밀번호</p>
      <el-input
        v-model="password_change_form.password"
        type="password"
        placeholder="비밀번호를 입력해주세요."
        show-password
      />
    </el-form-item>
  </el-form>

  <el-button @click="confirmPassword">비밀번호 체크</el-button>

  <el-form
    :model="password_change_form"
    :rules="second_rules"
    status-icon
    scroll-to-error
  >
    <el-form-item prop="new_password">
      <p>새 비밀번호</p>
      <el-input
        v-model="password_change_form.new_password"
        v-bind:disabled="!isChecked"
        type="password"
        placeholder="새 비밀번호를 입력해주세요."
        show-password
      />
    </el-form-item>

    <el-form-item prop="new_password_confirm">
      <p>새 비밀번호 확인</p>
      <el-input
        v-model="password_change_form.new_password_confirm"
        v-bind:disabled="!isChecked"
        type="password"
        placeholder="새 비밀번호를 다시 입력해주세요."
        show-password
      />
    </el-form-item>
    <el-form-item>
      <el-button @click="sendChangePassword"> 비밀번호 변경 </el-button>
    </el-form-item>
  </el-form>
</template>

<script lang="ts">
import { defineComponent, reactive, ref } from "vue";
import type { FormRules } from "element-plus";

import { checkPassword } from "@/api/userApi";
import { changePassword } from "@/api/userApi";
import ResponseStatus from "@/api/responseStatus";

import { ElForm, ElFormItem, ElInput, ElButton } from "element-plus";

export default defineComponent({
  name: "PasswordChange",
  components: { ElForm, ElFormItem, ElInput, ElButton },
  setup() {
    const password_change_form = reactive({
      password: "",
      new_password: "",
      new_password_confirm: "",
    });

    let isChecked = ref(false);

    const confirmPassword = async () => {
      const response = await checkPassword(password_change_form);
      const data = response.data;

      if (response.status === ResponseStatus.Ok) {
        isChecked.value = data;
      }
    };

    const sendChangePassword = async () => {
      const response = await changePassword(password_change_form);

      if (response.status === ResponseStatus.Ok) {
        console.log("password change success");
      }
    };

    const password_confirm = (rule: any, value: any, callback: any) => {
      if (value === "") {
        callback(new Error("Please input the password again"));
      } else if (value !== password_change_form.new_password) {
        callback(new Error("Two inputs don't match!"));
      } else {
        callback();
      }
    };

    const first_rules = reactive<FormRules>({
      password: [
        {
          required: true,
          message: "비밀번호를 반드시 입력하세요",
          trigger: "blur",
        },
        {
          min: 8,
          max: 16,
          message: "비밀번호는 8글자 이상, 16자 이하입니다.",
          trigger: "blur",
        },
      ],
    });

    const second_rules = reactive<FormRules>({
      new_password: [
        {
          required: true,
          message: "새 비밀번호는 반드시 입력해주세요.",
          trigger: "blur",
        },
        {
          min: 8,
          message: "비밀번호는 8글자 이상으로 만들어주세요.",
          trigger: "blur",
        },
        {
          max: 16,
          message: "비밀번호는 16글자 이하로 만들어주세요.",
          trigger: "blur",
        },
      ],
      new_password_confirm: [
        {
          required: true,
          message: "비밀 번호 확인은 반드시 입력해주세요.",
          trigger: "blur",
        },
        {
          min: 8,
          message: "비밀번호는 8글자 이상으로 만들어주세요.",
          trigger: "blur",
        },
        {
          max: 16,
          message: "비밀번호는 16글자 이하로 만들어주세요.",
          trigger: "blur",
        },
        {
          validator: password_confirm,
          message: "비밀번호는 같아야함",
        },
      ],
    });

    return {
      confirmPassword,
      password_change_form,
      sendChangePassword,
      first_rules,
      second_rules,
      isChecked,
    };
  },
});
</script>

<style></style>
