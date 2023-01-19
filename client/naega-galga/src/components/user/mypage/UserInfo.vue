<!-- Memo 여기서 axios로 유저 정보 가져와서 뿌려줄 것 -->
<template>
  <div id="mypagedetail">
    <h1>내 정보</h1>
    <hr />
    <p>이름 : {{ info.user_name }}</p>
    <p>아이디 : {{ info.user_id }}</p>
    <p>핸드폰 번호 : {{ info.user_phone }}</p>
    <p>주소 : {{ info.user_address }}</p>
    <hr />
    <h1>비밀번호 변경</h1>
    <hr />
    <password-change />
    <hr />
    <button>회원탈퇴</button>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import PasswordChange from "./PasswordChange.vue";

export default defineComponent({
  name: "UserInfo",
  components: {
    PasswordChange,
  },
  setup() {
    interface Info {
      user_name: string;
      user_id: string;
      user_phone: string;
      user_address: string;
    }
    const info: Info = {
      user_name: "창식이",
      user_id: "sige_tank",
      user_phone: "010-9169-5671",
      user_address: "그레이빌 206호",
    };
    return { info };
  },
});
</script>

<style>
hr scoped {
  border: solid 2px gray;
}
</style>
