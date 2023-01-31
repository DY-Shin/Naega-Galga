<template>
  <div>비밀번호를 입력하세요</div>
  <el-input v-model="passwordForm.password" placeholder="" />
  <button @click="checkPassword">비밀번호 확인</button>
  <!-- 정말로 삭제 할지 한번 묻고 진짜 회원탈퇴 진행 -->
</template>

<script lang="ts">
import { defineComponent, reactive, ref } from "vue";
import { useStore } from "vuex";
import { useRouter } from "vue-router";
import apiTokenInstance from "@/api/apiTokenInstance";

export default defineComponent({
  name: "UserDeleteView",
  setup() {
    const store = useStore();
    const router = useRouter();

    const passwordForm = reactive({
      password: "",
    });

    let isChecked = ref(false);

    const checkPassword = () => {
      apiTokenInstance
        .post(`api/users/password`, {
          checkPassword: passwordForm.password,
        })
        .then(res => {
          isChecked.value = res.data;
          passwordForm.password = "";
        })
        .catch(err => {
          console.log(err);
        });
    };

    const userdelete = () => {
      store.dispatch("userStore/userDelete");
      router.push({ path: "" });
    };

    return { passwordForm, checkPassword, userdelete, isChecked };
  },
});
</script>

<style></style>
