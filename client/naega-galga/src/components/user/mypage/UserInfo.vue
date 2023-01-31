<!-- Memo 여기서 axios로 유저 정보 가져와서 뿌려줄 것 -->
<!-- 정보 수정 시 state에 있는 유저 정보를 수정할 것 이므로 state로 dispatch 보내준다.  -->
<template>
  <div id="mypagedetail">
    <h1>내 정보</h1>
    <el-button @click="logout">로그아웃</el-button>
    <hr />
    <!-- 여기는 default form 입니다. -->
    <el-form v-if="isChange == false">
      <el-form-item class="one-line">
        <p class="title">이름</p>
        <p class="content">{{ info.user_name }}</p>
      </el-form-item>
      <el-form-item class="one-line">
        <p class="title">아이디</p>
        <p class="content">{{ info.user_id }}</p>
      </el-form-item>
      <el-form-item class="one-line">
        <p class="title">핸드폰 번호</p>
        <p class="content">{{ info.user_phone }}</p>
      </el-form-item>
      <el-form-item class="one-line">
        <p class="title">주소</p>
        <p class="content">{{ info.user_address }}</p>
      </el-form-item>
      <el-form-item>
        <button @click="go_user_info_change">수정하기</button>
      </el-form-item>
    </el-form>

    <el-form :model="changeform" v-if="isChange == true">
      <el-form-item>
        <p class="title-change">이름</p>
        <el-input
          class="content-change"
          v-model="changeform.user_name"
        ></el-input>
      </el-form-item>
      <el-form-item>
        <p class="title-change">아이디</p>
        <el-input
          class="content-change"
          v-model="changeform.user_id"
        ></el-input>
      </el-form-item>
      <el-form-item>
        <p class="title-change">핸드폰 번호</p>
        <el-input
          class="content-change"
          v-model="changeform.user_phone"
        ></el-input>
      </el-form-item>
      <el-form-item>
        <p class="title-change">주소</p>
        <el-input
          class="content-change"
          v-model="changeform.user_address"
        ></el-input>
      </el-form-item>
      <el-form-item>
        <button @click="user_info_change">저장하기</button>
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
import { defineComponent, computed, reactive, ref, onMounted } from "vue";
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
    const info = computed(() => state.userStore.user_info);

    let isChange = ref(false);

    const changeform = reactive({
      user_name: "",
      user_id: "",
      user_phone: "",
      user_address: "",
    });

    const go_user_info_change = () => {
      isChange.value = true;
      // event.preventDefault();
    };

    const user_info_change = () => {
      store.dispatch("userStore/userInfoChange", changeform);
      isChange.value = false;
      // event.preventDefault();
    };

    const getUserInfo = onMounted(() =>
      store.dispatch("userStore/getUserInfo")
    );

    const logout = () => {
      store.dispatch("userStore/logout");
    };

    return {
      info,
      changeform,
      isChange,
      go_user_info_change,
      user_info_change,
      getUserInfo,
      logout,
    };
  },
});
</script>

<style>
.one-line {
  height: 40px;
}

.title {
  width: 15%;
}

.content {
  width: 85%;
}

.title-change {
  margin-top: 8px;
  margin-bottom: 0px;
  width: 15%;
}

.content-change {
  margin-top: 8px;
  margin-bottom: 0px;
  width: 85%;
}

hr scoped {
  border: solid 2px gray;
}
</style>
