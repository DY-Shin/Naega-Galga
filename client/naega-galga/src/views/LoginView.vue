<template>
  <div class="wrapper">
    <el-form :model="loginform" class="loginForm" label-width="25%">
      <img
        src="@/assets/image/logo/NGGG.png"
        style="height: 100px; margin-bottom: 20px"
      />
      <div class="logincontnet">
        <el-form-item label="아이디">
          <el-input v-model="loginform.id" />
        </el-form-item>
        <el-form-item label="비밀번호">
          <el-input
            v-model="loginform.password"
            type="password"
            @keyup.enter="goLogin"
          />
        </el-form-item>
        <el-button class="signup-button" type="primary" @click="signup"
          >회원가입</el-button
        >
        <el-button class="login-button" type="primary" @click="goLogin"
          >로그인</el-button
        >
      </div>
    </el-form>
    <div />
  </div>
</template>

<script lang="ts">
import { defineComponent, reactive } from "vue";
import { useStore } from "vuex";
import { useRouter } from "vue-router";

import { getUserInfo } from "@/api/userApi";
import { login } from "@/api/userApi";

import localStorageManager from "@/utils/localStorageManager";
import ResponseStatus from "@/api/responseStatus";

export default defineComponent({
  name: "LoginView",
  setup() {
    const store = useStore();
    const router = useRouter();

    const url =
      "https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg";

    const loginform = reactive({
      id: "",
      password: "",
    });

    const signup = () => {
      router.push({ path: "/join" });
    };

    const goLogin = async () => {
      const response = await login(loginform);
      const status = response.status;

      localStorageManager.setAccessToken(response.data.accessToken);
      localStorageManager.setRefreshToken(response.data.refreshToken);

      if (status === ResponseStatus.Ok) {
        store.commit("userStore/CHECK_TOKEN");

        const composition = async () => {
          const response = await getUserInfo();
          const data = response.data;

          if (response.status === ResponseStatus.Ok) {
            store.commit("userStore/GET_USER_INFO", data);
          }
        };
        composition();
        router.push({ path: "/" });
      }
    };

    return { loginform, signup, goLogin, url };
  },
});
</script>

<style scoped>
.loginForm {
  width: 20%;

  padding-top: 40px;
  padding-bottom: 30px;
  padding-left: 40px;
  padding-right: 40px;

  text-align: center;

  border-radius: 10px;
  box-shadow: 0 0 20px 0 rgba(0, 0, 0, 0.2), 0 2px 2px 0 rgba(0, 0, 0, 0.24);
}

.wrapper {
  display: flex;
  justify-content: center;
  align-items: center;

  height: 85vh;
}
</style>
