<!-- Memo 여기서 axios로 유저 정보 가져와서 뿌려줄 것 -->
<!-- 정보 수정 시 state에 있는 유저 정보를 수정할 것 이므로
    state로 dispatch 보내준다.  -->
<template>
  <div id="mypagedetail">
    <h1>내 정보</h1>
    <hr />
    <div v-show="alpha">
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

    <div v-show="false">
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

    <button>수정하기</button>
    <button>저장하기</button>
    <hr />
    <h1>비밀번호 변경</h1>
    <hr />
    <password-change />
    <hr />
    <button>회원탈퇴</button>
  </div>
</template>

<script lang="ts">
import { defineComponent, computed } from "vue";
import { useStore } from "vuex";
import PasswordChange from "./PasswordChange.vue";

export default defineComponent({
  name: "UserInfo",
  components: {
    PasswordChange,
  },
  setup() {
    const { state } = useStore();
    const info = computed(() => state.user_info);

    const alpha = 

    return { info };
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
