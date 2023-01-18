<template>
  <el-form :model="loginform" class="loginFunction" label-width="100px" width>
    <div class="logincontnet">
      <el-form-item label="아이디">
        <el-input v-model="loginform.id" />
      </el-form-item>
      <el-form-item label="비밀번호">
        <el-input v-model="loginform.password" />
      </el-form-item>
      <el-button class="signup-button" @click="signup">회원가입</el-button>
      <el-button class="login-button" @click="login">로그인</el-button>
    </div>
  </el-form>
</template>

<script lang="ts">
import { defineComponent, reactive } from "vue";
import { useStore } from "vuex";
import { useRouter } from "vue-router";

export default defineComponent({
  name: "LoginFunction",
  setup() {
    const loginform = reactive({
      id: "",
      password: "",
    });

    const store = useStore();
    const router = useRouter();

    const login = () => {
      store.dispatch("login", loginform);
      router.push({ path: "/user" });
    };

    const signup = () => {
      router.push({ path: "/join" });
    };

    return { loginform, login, signup };
  },
});
</script>

<style>
.loginFunction {
  width: 300px;
  height: 300px;
  padding: 50px;

  display: flex;
  justify-content: center;
  align-items: center;

  border: 1px solid;
}

.logincontnet {
  position: absolute;
  top: 25%;
  left: 55%;
}

.signup-button {
  position: absolute;
  left: 10%;
}
.login-button {
  position: absolute;
  left: 72%;
}
</style>
