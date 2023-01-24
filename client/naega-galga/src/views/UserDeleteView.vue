<template>
  <div>비밀번호를 입력하세요</div>
  <el-input v-model="password" placeholder="Please input" />
  <button @click="checkpassword">비밀번호 확인</button>
</template>

<script lang="ts">
import { defineComponent, ref } from "vue";
import { useStore } from "vuex";
import { useRouter } from "vue-router";

export default defineComponent({
  name: "UserDeleteView",
  setup() {
    const store = useStore();
    const router = useRouter();

    const password = ref("");

    const checkpassword = () => {
      store.dispatch("passwordCheck");
    };

    const userdelete = () => {
      store.dispatch("userDelete");
      router.push({ path: "" });
    };

    return { password, checkpassword, userdelete };
  },
});
</script>

<style></style>
