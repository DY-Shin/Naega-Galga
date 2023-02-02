<template>
  <div id="mypagedetail">
    <h1>내 정보</h1>

    <button v-show="loginCheck">로그인O</button>
    <button v-show="!loginCheck">로그인X</button>

    <el-button @click="logout">로그아웃</el-button>
    <hr />
    <el-form v-if="isChange == false">
      <el-form-item class="one-line">
        <p class="title">아이디</p>
        <p class="content">{{ info.user_id }}</p>
      </el-form-item>
      <el-form-item class="one-line">
        <p class="title">이름</p>
        <p class="content">{{ info.user_name }}</p>
      </el-form-item>
      <el-form-item class="one-line">
        <p class="title">핸드폰 번호</p>
        <p class="content">{{ info.user_phone }}</p>
      </el-form-item>
      <el-form-item class="one-line">
        <p class="title">주소</p>
        <p class="content">{{ info.user_address }}</p>
      </el-form-item>
      <el-form-item class="one-line">
        <p class="title">사업자 번호</p>
        <p class="content-change-button">
          {{ info.corporate_registration_number }}
        </p>
        <el-button @click="putUserInfo">수정하기</el-button>
      </el-form-item>
    </el-form>

    <el-form :model="changeform" v-if="isChange == true">
      <el-form-item class="one-line">
        <p class="title-change">아이디</p>
        <p class="content">{{ info.user_id }}</p>
      </el-form-item>

      <el-form-item class="one-line">
        <p class="title-change">이름</p>
        <el-input
          class="content-change"
          v-model="changeform.user_name"
        ></el-input>
      </el-form-item>

      <el-form-item class="one-line">
        <p class="title-change">핸드폰 번호</p>
        <el-input
          class="content-change"
          v-model="changeform.user_phone"
        ></el-input>
      </el-form-item>

      <el-form-item class="one-line">
        <p class="title-change">주소</p>
        <el-input
          class="content-change"
          v-model="changeform.user_address"
        ></el-input>
      </el-form-item>

      <el-form-item class="one-line">
        <p class="title-change">사업자 번호</p>
        <el-input
          class="content-change-button"
          v-model="changeform.corporate_registration_number"
        ></el-input>
        <el-button @click="saveChangeInfo">저장하기</el-button>
      </el-form-item>
    </el-form>
  </div>

  <hr />
  <h1>비밀번호 변경</h1>

  <hr />
  <password-change />

  <hr />
  <user-delete-dialog />
</template>

<script lang="ts">
import {
  defineComponent,
  computed,
  reactive,
  ref,
  onMounted,
  // onBeforeMount,
} from "vue";
import { useStore } from "vuex";
import PasswordChange from "./PasswordChange.vue";
import UserDeleteDialog from "./UserDeleteDialog.vue";

export default defineComponent({
  name: "UserInfo",
  components: {
    PasswordChange,
    UserDeleteDialog,
  },
  setup() {
    const store = useStore();
    const { state } = useStore();

    let info = computed(() => state.userStore.user_info);
    const loginCheck = computed(() => store.getters["userStore/isLogin"]);

    onMounted(() => {
      store.dispatch("userStore/getUserInfo");
      store.dispatch("userStore/isToken");
    });

    let isChange = ref(false);

    const changeform = reactive({
      user_name: "",
      user_id: "",
      user_phone: "",
      user_address: "",
      corporate_registration_number: "",
    });

    const putUserInfo = () => {
      isChange.value = true;
      // event.preventDefault();
    };

    const saveChangeInfo = () => {
      store.dispatch("userStore/userInfoChange", changeform);
      isChange.value = false;
      // event.preventDefault();
    };

    const logout = () => {
      store.dispatch("userStore/logout");
    };

    return {
      info,
      changeform,
      isChange,
      putUserInfo,
      saveChangeInfo,
      logout,
      loginCheck,
    };
  },
});
</script>

<style scoped>
.one-line {
  height: 60px;
  margin: 0px;
}

.title {
  width: 15%;
}

.content {
  width: 85%;
}

.title-change {
  width: 15%;
}

.content-change {
  width: 85%;
}

.content-change-button {
  width: 50%;
}

hr scoped {
  border: solid 2px gray;
}
</style>
