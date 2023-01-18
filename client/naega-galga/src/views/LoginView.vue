<template>
  <div class="loginview">
    <login-left-image />
    <login-function />
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import LoginFunction from "@/components/user/login/LoginFunction.vue";
import LoginLeftImage from "@/components/user/login/LoginLeftImage.vue";

export default defineComponent({
  name: "LoginView",
  components: {
    LoginFunction,
    LoginLeftImage,
  },
});
</script>

<style>
.loginview {
  display: flex;
  justify-content: center;
  /* align-items: center; */

  height: 70vh;

  position: absolute;
  top: 20%;
  left: 25%;
}
</style>
