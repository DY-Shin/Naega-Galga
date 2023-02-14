<template>
  <div id="nav" v-if="isHeaderShow">
    <img src="@/assets/image/logo/NGGG.png" class="logo" @click="redirect" />
    <div class="home-btn" v-if="isLog == false">
      <!-- 로그인 안 했을 때 -->
      <el-button
        id="login-btn"
        color="#b1b3b8"
        round
        @click="$router.push('/login')"
        >로그인</el-button
      >
      <el-button
        id="join-btn"
        color=" #b1b3b8"
        round
        @click="$router.push('/join')"
        >회원가입</el-button
      >
    </div>
    <!-- 로그인 했을 때 -->
    <el-dropdown v-if="isLog == true">
      <span class="el-dropdown-link">
        <el-button
          v-on:click="OpenMenu"
          id="my-btn"
          color="#393B44"
          height="20px"
          circle
        >
          <el-icon size="40" color="#F1F3F8"><UserFilled /></el-icon
        ></el-button>
      </span>
      <template #dropdown>
        <el-dropdown-menu>
          <el-dropdown-item
            index="2-1"
            style="
              --el-dropdown-menuItem-hover-fill: #dedfe0;
              --el-dropdown-menuItem-hover-color: #73767a;
            "
            hide-on-click
          >
            <router-link to="/user" class="drop-text">
              내 정보
            </router-link></el-dropdown-item
          >
          <el-dropdown-item
            index="2-3"
            style="
              --el-dropdown-menuItem-hover-fill: #dedfe0;
              --el-dropdown-menuItem-hover-color: #73767a;
            "
            ><router-link to="/user/reservation" class="drop-text"
              >예약 목록</router-link
            ></el-dropdown-item
          >

          <el-dropdown-item
            index="2-2"
            style="
              --el-dropdown-menuItem-hover-fill: #dedfe0;
              --el-dropdown-menuItem-hover-color: #73767a;
            "
            ><router-link to="/user/register" class="drop-text"
              >등록한 매물</router-link
            ></el-dropdown-item
          >

          <el-dropdown-item
            index="2-3"
            style="
              --el-dropdown-menuItem-hover-fill: #dedfe0;
              --el-dropdown-menuItem-hover-color: #73767a;
            "
            ><router-link to="/" class="drop-text"
              ><span @click="Logout">로그아웃</span></router-link
            ></el-dropdown-item
          >
        </el-dropdown-menu>
      </template>
    </el-dropdown>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref, computed } from "vue";
import { useStore } from "vuex";
import { useRouter } from "vue-router";
import { logout } from "@/api/userApi";

import localStorageManager from "@/utils/localStorageManager";
import ResponseStatus from "@/api/responseStatus";

import { UserFilled } from "@element-plus/icons-vue";

import {} from "element-plus";

export default defineComponent({
  components: {
    UserFilled,
  },
  setup() {
    const isLog = computed(() => store.getters["userStore/isLogin"]);
    const isMenu = ref(false);
    const showButton = ref(true);
    const isHeaderShow = ref(true);

    const store = useStore();
    const router = useRouter();

    // isLog.value = computed(() => store.getters["userStore/isLogin"]).value;
    // console.log(isLog.value);
    // console.log(isLog.value);
    const redirect = () => {
      store.commit("chatStore/CHANGE_CHATROOM_STATUS", false);
      router.push("/");
    };
    const OpenMenu = () => {
      isMenu.value = true;
    };
    const close = () => {
      // this.showButton = false;
    };

    const Logout = async () => {
      const response = await logout();
      store.commit("userStore/CHECK_TOKEN");

      if (response.status === ResponseStatus.Ok) {
        localStorageManager.removeTokens();
        localStorageManager.removeLocalStorage();
        router.push({ path: "/" });
      }
    };

    // const Logout = () => {
    //   // store.dispatch("userStore/logout");
    //   logout();
    //   localStorageManager.removeTokens();
    //   router.push({ path: "/" });
    //   console.log("logout");
    // };

    const changeEmpty = () => {
      // window.open("../assets/image/icon-heart.png");
      let icon2 = document.querySelector("#icon");
      // icon?.setAttribute("src", "../assets/image/icon-heart.png");
      icon2?.setAttribute("src", "/icon-heart.png");
    };

    return {
      isLog,
      isMenu,
      showButton,
      isHeaderShow,
      OpenMenu,
      close,
      Logout,
      changeEmpty,
      redirect,
    };
  },

  watch: {
    $route(to) {
      if (
        to.path === "/join" ||
        to.path === "/login" ||
        to.path.includes("/meeting")
      ) {
        this.isHeaderShow = false;
        return;
      }
      this.isHeaderShow = true;
    },
  },
});
</script>

<style scoped>
.logo {
  height: 80px;
}

.logo:hover {
  cursor: pointer;
}
.el-dropdown {
  float: right;
  right: 20px;
}
.el-popper.is-pure {
  padding: 50px;
  outline: auto;
}
.el-popper__arrow::before {
  position: fixed;
  width: 10px;
  height: 10px;
  z-index: -1;
}
.el-popper {
  position: absolute;
  border-radius: var(--el-popper-border-radius);
  padding: 5px 11px;
  z-index: 2000;
  font-size: 12px;
  line-height: 20px;
  min-width: 10px;
  word-wrap: break-word;
  visibility: visible;
  right: 20px;
}
.el-popper__arrow::before {
  left: 20px;
}

.my-menu-row {
  text-align: center;
  padding: 8px 0;
}

.my-menu-logout-row {
  text-align: center;
  border-top: 1px solid white;
  padding: 10px 0;
}
.my-menu-txt {
  text-align: center;
  text-decoration: none;
  padding: 7px 0;
  /* border: 1px solid white; */
  color: white;
}

.logout {
  padding: 15px 0;
  border: 1px solid white;
}

#my-menu {
  background-color: #c8c9cc;
  border-radius: 10px;
  position: absolute;
  font-size: 19px;
  z-index: 11;
  width: 160px;
  height: 220px;
  top: 120px;
  right: 60px;
}

#nav {
  padding: 25px 50px;
  font-size: 40px;
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
}

#title {
  cursor: pointer;
  white-space: nowrap;
  color: black;
  text-decoration: none;
}

.drop-text {
  text-align: center;
  padding: 10px 25px;
  color: #73767a;
  text-decoration: none;
  font-size: 20px;
}

#my-btn {
  width: 60px;
  height: 60px;
  /* float: right; */
}

#login-btn,
#join-btn {
  /* float: right; */
  bottom: 30px;
  color: white;
}

.home-btn {
  float: right;
  display: flex;
  flex-direction: row;
}

.el-button--large {
  --el-button-size: 100px;
  height: 50px;
  font-size: 20px;
  border-radius: var(--el-border-radius-base);
}
</style>
