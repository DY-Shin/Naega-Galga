<template>
  <div class="grid">
    <div />
    <el-form
      ref="joinformRef"
      :model="joinform"
      :rules="rules"
      class="joinform"
      style="min-width: 400px"
      status-icon
    >
      <el-form-item>
        <el-form-item prop="user_id" style="width: 50%">
          <p>아이디</p>
          <el-input v-model="joinform.user_id" />
        </el-form-item>

        <el-form-item prop="user_name" style="width: 50%">
          <p>이름</p>
          <el-input v-model="joinform.user_name"></el-input>
        </el-form-item>
      </el-form-item>

      <el-form-item prop="user_password">
        <p>비밀번호</p>
        <el-input v-model="joinform.user_password" type="password"></el-input>
      </el-form-item>

      <el-form-item prop="password_confirm">
        <p>비밀번호 확인</p>
        <el-input
          v-model="joinform.password_confirm"
          type="password"
        ></el-input>
      </el-form-item>

      <el-form-item prop="user_phone">
        <p>핸드폰 번호</p>
        <el-input v-model="joinform.user_phone"></el-input>
      </el-form-item>

      <el-form-item label="사업자이신가요?">
        <el-checkbox label="예" v-model="visible" />
      </el-form-item>

      <el-form-item prop="corporate_registration_number">
        <el-input
          v-show="visible"
          v-model="joinform.corporate_registration_number"
        ></el-input>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="submitForm(joinformRef)">
          가입하기
        </el-button>
        <el-button @click="cancel"> 취소하기 </el-button>
      </el-form-item>
    </el-form>
    <div />
  </div>
</template>

<script lang="ts">
import { defineComponent, reactive, ref } from "vue";
import { useStore } from "vuex";
import { useRouter } from "vue-router";
import type { FormInstance, FormRules } from "element-plus";

export default defineComponent({
  name: "JoinView",
  setup() {
    const store = useStore();
    const router = useRouter();
    const visible = ref(false);

    const joinformRef = ref<FormInstance>();
    const joinform = reactive({
      user_id: "",
      user_name: "",
      user_password: "",
      password_confirm: "",
      user_phone: "",
      corporate_registration_number: "",
    });

    const rules = reactive<FormRules>({
      user_id: [
        {
          required: true,
          message: "아이디를 반드시 입력해주세요.",
          trigger: "blur",
        },
        {
          min: 4,
          max: 12,
          message: "아이디는 4~12",
          trigger: "blur",
        },
      ],
      user_name: [
        {
          required: true,
          message: "이름은 반드시 입력해주세요",
          trigger: "blur",
        },
        {
          min: 2,
          max: 8,
          message: "이름은 어쩌고",
          trigger: "blur",
        },
      ],
      user_password: [
        {
          required: true,
          message: "비밀번호는 반드시 입력해주세요.",
          trigger: "blur",
        },
        {
          min: 8,
          max: 16,
          message: "비밀번호는 8~16",
          trigger: "blur",
        },
      ],
      password_confirm: [
        {
          required: true,
          message: "비밀번호는 반드시 입력해주세요.",
          trigger: "blur",
        },
        {
          message: "비밀번호는 같아야 한다 이녀석아",
        },
      ],
    });

    const submitForm = async (formEl: FormInstance | undefined) => {
      if (!formEl) {
        return;
      }
      await formEl.validate((valid, fields) => {
        if (valid) {
          store.dispatch("userStore/join", joinform);
          console.log("submit!");
        } else {
          console.log("error submit!", fields);
        }
      });
    };

    const cancel = () => {
      router.push({ path: "/" });
    };
    return { visible, joinformRef, joinform, rules, submitForm, cancel };
  },
});
</script>

<style scoped>
.grid {
  display: grid;
  grid-template-columns: 7fr 9fr 7fr;
  padding-top: 75px;
}

.joinform {
  padding-top: 70px;
  padding-bottom: 50px;
  padding-left: 70px;
  padding-right: 70px;

  box-shadow: 0 0 20px 0 rgba(0, 0, 0, 0.2), 0 2px 2px 0 rgba(0, 0, 0, 0.24);
  border: 1px solid;
  border-color: #555555;
}
</style>
