<template>
  <div class="wrapper">
    <el-form :model="loginform" class="loginForm" label-width="100px">
      <img
        fit:fill
        src="@/assets/image/logo/NGGG.png"
        style="width: 100px; height: 100px"
      />
      <div class="logincontnet">
        <el-form-item label="아이디">
          <el-input v-model="loginform.id" />
        </el-form-item>
        <el-form-item label="비밀번호">
          <el-input
            v-model="loginform.password"
            type="password"
            @keyup.enter="login"
          />
        </el-form-item>
        <el-button class="signup-button" @click="signup">회원가입</el-button>
        <el-button class="login-button" @click="login">로그인</el-button>
      </div>
    </el-form>
    <div />
  </div>
</template>

<script lang="ts">
import { defineComponent, reactive } from "vue";
import { useStore } from "vuex";
import { useRouter } from "vue-router";

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

    const login = () => {
      store.dispatch("userStore/login", loginform);
      router.push({ path: "/user" });
    };

    return { loginform, signup, login, url };
  },
});
</script>

<style scoped>
.loginForm {
  width: 25%;

  padding-top: 10px;
  padding-bottom: 60px;
  padding-left: 30px;
  padding-right: 30px;

  text-align: center;

  border-radius: 10px;
  box-shadow: 0 0 20px 0 rgba(0, 0, 0, 0.2), 0 2px 2px 0 rgba(0, 0, 0, 0.24);
}

.wrapper {
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>
