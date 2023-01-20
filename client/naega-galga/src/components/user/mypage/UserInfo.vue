<!-- Memo 여기서 axios로 유저 정보 가져와서 뿌려줄 것 -->
<!-- 정보 수정 시 state에 있는 유저 정보를 수정할 것 이므로 state로 dispatch 보내준다.  -->
<template>
  <div id="mypagedetail">
    <h1>내 정보</h1>
    <hr />
    <!-- 여기는 default form 입니다. -->
    <div v-show="default_form">
      <div>
        <div class="title">이름</div>
        <div class="content">{{ info.user_name }}</div>
      </div>
      <div>
        <div class="title">아이디</div>
        <div class="content">{{ info.user_id }}</div>
      </div>
      <div>
        <div class="title">핸드폰 번호</div>
        <div class="content">{{ info.user_phone }}</div>
      </div>
      <div>
        <div class="title">주소</div>
        <div class="content">{{ info.user_address }}</div>
      </div>
    </div>
    <hr />
    <!-- 여기서부터 change form입니다. -->
    <!-- <div v-show="change_form">
      <div>
        <div class="title">이름 바꾸기</div>
        <el-input v-model="changeform.user_name" placeholder="hello" />
      </div>
      <div>
        <div class="title">아이디</div>
        <el-input v-model="changeform.user_id" placeholder="hello" />
      </div>
      <div>
        <div class="title">핸드폰 번호</div>
        <el-input v-model="changeform.user_phone_number" placeholder="hello" />
      </div>
      <div>
        <div class="title">주소</div>
        <el-input v-model="changeform.user_address" placeholder="hello" />
      </div>
    </div> -->
  </div>

  <button @click="user_info_change">수정하기</button>
  <button @click="change_to_server">저장하기</button>
  <hr />
  <h1>비밀번호 변경</h1>
  <hr />
  <password-change />
  <hr />
  <button>회원탈퇴</button>
</template>

<script lang="ts">
import { defineComponent, computed, reactive, ref } from "vue";
import { useStore } from "vuex";
import PasswordChange from "./PasswordChange.vue";

export default defineComponent({
  name: "UserInfo",
  components: {
    PasswordChange,
  },
  setup() {
    const store = useStore();
    const { state } = useStore();
    const info = computed(() => state.userStore.user_info);

    let default_form = ref(true);
    let change_form = ref(true);

    const changeform = reactive({
      user_name: "",
      user_id: "",
      user_phone_number: "",
      user_address: "",
    });

    const user_info_change = () => {
      store.commit("USER_INFO_CHANGE", changeform);
    };

    const change_to_server = () => {
      store.dispatch("change_to_server", changeform);
    };

    return {
      info,
      default_form,
      change_form,
      changeform,
      user_info_change,
      change_to_server,
    };
  },
});
</script>

<style>
.title {
  display: inline-block;
  width: 100px;
}

.content {
  display: inline-block;
  margin-left: 20px;
  margin-bottom: 10px;
}

hr scoped {
  border: solid 2px gray;
}
</style>
