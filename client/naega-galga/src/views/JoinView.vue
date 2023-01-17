<template>
  <el-form :model="joinform" class="joinform" label-width="120px">
    <el-form-item label="아이디">
      <el-input v-model="joinform.id" placeholder="아이디를 입력하세요" />
    </el-form-item>
    <el-form-item label="비밀번호">
      <el-input
        v-model="joinform.password1"
        type="password"
        placeholder="비밀번호를 입력하세요"
      />
    </el-form-item>
    <el-form-item label="비밀번호 확인">
      <el-input
        v-model="joinform.password2"
        type="password"
        placeholder="비밀번호를 다시 입력하세요"
      />
    </el-form-item>
    <p
      :hidden="joinform.password1 == joinform.password2"
      class="warning"
      style="font-size: xx-small; color: red"
    >
      비밀번호가 일치하지 않습니다.
    </p>
    <el-form-item label="이름">
      <el-input v-model="joinform.name" placeholder="이름을 입력하세요" />
    </el-form-item>
    <el-form-item label="전화번호">
      <el-input
        v-model="joinform.phone_number"
        placeholder="전화번호를 입력하세요"
      />
    </el-form-item>

    <el-form-item label="사업자이신가요?">
      <el-checkbox label="예" v-model="visible" size="large" />
    </el-form-item>

    <el-form-item label="사업자 번호" v-show="visible">
      <el-input
        v-model="joinform.register_number"
        placeholder="사업자 번호를 입력하세요"
      />
    </el-form-item>

    <el-form-item>
      <el-button type="primary" @click="join">회원가입</el-button>
      <el-button @click="goToHome">취소하기</el-button>
    </el-form-item>
  </el-form>
</template>

<script lang="ts">
import { defineComponent, reactive, ref } from "vue";
import { useStore } from "vuex";
import { useRouter } from "vue-router";

export default defineComponent({
  name: "JoinView",
  setup() {
    const joinform = reactive({
      id: "",
      password1: "",
      password2: "",
      name: "",
      phone_number: "",
      register_number: "",
    });

    const store = useStore();
    const router = useRouter();

    const visible = ref(false);

    const join = () => {
      store.dispatch("join", joinform);
      router.push({ path: "/login" });
    };

    const goToHome = () => {
      router.push({ path: "/" });
    };

    return { joinform, visible, join, goToHome };
  },
});
</script>

<style scoped>
.joinform {
  position: relative;
  max-width: 450px;

  margin: 0 auto 100px;
  padding: 70px;
  text-align: center;
  box-shadow: 0 0 20px 0 rgba(0, 0, 0, 0.2), 0 2px 2px 0 rgba(0, 0, 0, 0.24);
  border: 1px solid;
  border-color: #555555;
}
</style>
