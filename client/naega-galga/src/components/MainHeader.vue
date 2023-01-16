<template>
  <div id="nav">
    <router-link to="/" id="title">내가갈가[家]</router-link>
    <div class="home-btn" v-if="isLog == false">
      <!-- 로그인 안 했을 때 -->
      <el-button id="login-btn" color="#b1b3b8" size="large">로그인</el-button>
      <el-button id="join-btn" color=" #b1b3b8" size="large"
        >회원가입</el-button
      >
    </div>
    <!-- 로그인 했을 때 -->
    <el-dropdown style="float: right" v-if="isLog == true">
      <span class="el-dropdown-link">
        <el-button
          v-on:click="OpenMenu"
          id="my-btn"
          circle
          color="#393B44"
          size="large"
          height="20px"
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
            <router-link to="/" class="drop-text">
              내 정보
            </router-link></el-dropdown-item
          >
          <el-dropdown-item
            index="2-2"
            style="
              --el-dropdown-menuItem-hover-fill: #dedfe0;
              --el-dropdown-menuItem-hover-color: #73767a;
            "
            ><router-link to="" class="drop-text"
              >등록된 매물</router-link
            ></el-dropdown-item
          >
          <el-dropdown-item
            index="2-3"
            style="
              --el-dropdown-menuItem-hover-fill: #dedfe0;
              --el-dropdown-menuItem-hover-color: #73767a;
            "
            ><router-link to="" class="drop-text"
              >예약 목록</router-link
            ></el-dropdown-item
          ><el-dropdown-item
            index="2-3"
            style="
              --el-dropdown-menuItem-hover-fill: #dedfe0;
              --el-dropdown-menuItem-hover-color: #73767a;
            "
            ><router-link to="" class="drop-text"
              >찜 목록</router-link
            ></el-dropdown-item
          ><el-dropdown-item
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
import { defineComponent } from "vue";

export default defineComponent({
  data() {
    return {
      isLog: true,
      isMenu: false,
      showButton: true,
    };
  },
  methods: {
    OpenMenu(): void {
      this.isMenu = true;
    },
    close() {
      // this.showButton = false;
    },
    Logout() {
      this.isLog = false;
    },
    changeEmpty(): void {
      // window.open("../assets/image/icon-heart.png");
      let icon2 = document.querySelector("#icon");
      // icon?.setAttribute("src", "../assets/image/icon-heart.png");
      icon2?.setAttribute("src", "/icon-heart.png");
    },
  },
});
</script>

<style scoped>
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
  padding: 30px 50px 0 50px;
  height: 100px;
  font-size: 50px;
  text-decoration: none;
}

#title {
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
  width: 70px;
  height: 70px;
  /* float: right; */
}

#login-btn,
#join-btn {
  /* float: right; */
  margin-left: 20px;
  margin-top: 30px;
  color: white;
}

.home-btn {
  float: right;
}

.el-button--large {
  --el-button-size: 100px;
  height: 50px;
  font-size: 20px;
  border-radius: var(--el-border-radius-base);
}
</style>
