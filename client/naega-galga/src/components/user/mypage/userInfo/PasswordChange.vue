<template>
  <el-form
    :model="password_change_form"
    :rules="first_rules"
    status-icon
    scroll-to-error
    label-width="22%"
    label-position="left"
    v-bind:disabled="isChecked"
  >
    <el-form-item label="기존 비밀번호" prop="password">
      <el-input
        v-model="password_change_form.password"
        type="password"
        class="content"
        placeholder="기존 비밀번호를 입력해주세요."
        show-password
      />
      <el-button type="primary" @click="confirmPassword" class="button-size"
        >비밀번호 확인
      </el-button>
    </el-form-item>
  </el-form>

  <el-form
    :model="password_change_form"
    :rules="second_rules"
    status-icon
    scroll-to-error
    label-width="22%"
    label-position="left"
  >
    <el-form-item label="새 비밀번호" prop="new_password">
      <el-input
        v-model="password_change_form.new_password"
        v-bind:disabled="!isChecked"
        class="content"
        type="password"
        placeholder="새 비밀번호를 입력해주세요."
        show-password
      />
    </el-form-item>

    <el-form-item label="새 비밀번호 확인" prop="new_password_confirm">
      <el-input
        v-model="password_change_form.new_password_confirm"
        v-bind:disabled="!isChecked"
        class="content"
        type="password"
        placeholder="새 비밀번호를 다시 입력해주세요."
        show-password
      />
      <el-button @click="sendChangePassword" type="primary" class="button-size"
        >비밀번호 변경
      </el-button>
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

<style scoped>
.content {
  width: 70%;
}
.button-size {
  margin-left: 5px;
  width: 22%;
}
</style>
