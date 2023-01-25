<template>
  <div class="grid">
    <div />

    <el-form :model="joinform" class="joinform">
      <el-form-item>
        <el-form-item style="width: 50%">
          <p class="p-design">아이디</p>
          <el-input
            v-model="joinform.id"
            placeholder="영문, 숫자를 조합하여 8~12자리"
          />
        </el-form-item>
        <el-form-item style="width: 50%">
          <p class="p-design">이름</p>
          <el-input v-model="joinform.name" />
        </el-form-item>
      </el-form-item>

      <p class="p-design">비밀번호</p>
      <el-input
        v-model="joinform.password1"
        type="password"
        placeholder="영문, 숫자, 특수문자를 조합하여 8~16자리"
      />

      <p class="p-design">비밀번호 확인</p>
      <el-input v-model="joinform.password2" type="password" />

      <p
        class="warning"
        style="
          font-size: xx-small;
          color: red;
          visibility: visible;
          margin-top: 0px;
          margin-bottom: 0px;
        "
      >
        비밀번호가 일치하지 않습니다.
      </p>

      <p class="p-design">전화번호</p>
      <el-input
        v-model="joinform.phone_number"
        placeholder="ex) 010-0000-0000"
      />

      <el-form-item
        label="사업자이신가요?"
        style="margin-top: 10px; margin-bottom: 10px"
      >
        <el-checkbox label="예" v-model="visible" />
      </el-form-item>

      <el-input
        v-show="visible"
        v-model="joinform.register_number"
        placeholder="사업자 번호를 입력하세요"
        style="margin-bottom: 20px"
      />
      <div class="buttons">
        <el-button type="primary" @click="join">회원가입</el-button>
        <el-button @click="cancel">취소하기</el-button>
      </div>
    </el-form>
    <div />
  </div>
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

    const cancel = () => {
      router.push({ path: "/" });
    };

    const formInline = reactive({
      user: "",
      region: "",
    });

    const onSubmit = () => {
      console.log("submit!");
    };

    return { joinform, visible, join, cancel, formInline, onSubmit };
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

.p-design {
  margin-top: 15px;
  margin-bottom: 0px;
  font-size: 14px;
}

.buttons {
  display: flex;
  justify-content: center;
}
</style>
