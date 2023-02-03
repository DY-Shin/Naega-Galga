import apiInstance from "@/api/apiInstance";
import apiTokenInstance from "@/api/apiTokenInstance";
import localStorageManager from "@/utils/localStorageManager";
import axios from "axios";
import router from "../router";

const state = {
  user_info: {
    user_index: "abab",
    user_id: "로그인이 되지 않음!",
    user_phone: "로그인이 되지 않음!",
    user_name: "로그인이 되지 않음!",
    user_address: "로그인이 되지 않음!",
    corporate_registration_number: null,
  },
  isToken: false,
};

const getters = {
  userIndex(state) {
    return state.user_info.user_index;
  },

  isLogin: state => state.isToken,
};

const mutations = {
  GET_USER_INFO(state, user_info) {
    state.user_info.user_name = user_info.userName;
    state.user_info.user_id = user_info.userId;
    state.user_info.user_phone = user_info.userPhone;
    state.user_info.user_address = user_info.userAddress;
    state.user_info.corporate_registration_number =
      user_info.corporateRegistrationNumber;
  },

  USER_INFO_CHANGE(state, changeform) {
    state.user_info.user_name = changeform.user_name;
    state.user_info.user_id = changeform.user_id;
    state.user_info.user_phone = changeform.user_phone;
    state.user_info.user_address = changeform.user_address;
    state.user_info.corporate_registration_number =
      changeform.corporate_registration_number;
  },

  TOKEN_TRUE(state) {
    state.isToken = true;
  },
  TOKEN_FALSE(state) {
    state.isToken = false;
  },
};

const actions = {
  isToken(context) {
    if (localStorageManager.getAccessToken()) {
      context.commit("TOKEN_TRUE");
    } else {
      context.commit("TOKEN_FALSE");
    }
  },

  join(context, joinform) {
    apiInstance
      .post("/api/users", {
        userId: joinform.user_id,
        userPassword: joinform.user_password,
        userName: joinform.user_name,
        userPhone: joinform.user_phone,
        corporateRegistrationNumber: joinform.corporate_registration_number,
        userAddress: joinform.user_address,
      })
      .then(res => {
        console.log(res);
      })
      .catch(err => {
        console.log(err);
      });
  },

  login(context, loginform) {
    apiInstance
      .post("/api/users/login", {
        userId: loginform.id,
        userPassword: loginform.password,
      })
      .then(res => {
        localStorageManager.setAccessToken(res.data.accessToken);
        localStorageManager.setRefreshToken(res.data.refreshToken);

        context.commit("TOKEN_TRUE");

        router.push({ path: "/" });
      })
      .catch(err => {
        console.log(err);
      });
  },

  // login(context, loginform) {
  //   axios({
  //     method: "post",
  //     url: `http://localhost:8888/api/users/login`,
  //     data: {
  //       userId: loginform.id,
  //       userPassword: loginform.password,
  //     },
  //     headers: {
  //       Authorization:
  //         "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJvbWo5NTEyIiwiYXV0aCI6IlJPTEVfVVNFUiIsImV4cCI6MTY3NTM5NzQxOX0.UvSXDSuk2Y4EhTRD3CenajxpOlrqQyNuDq-7caQq4Io",
  //     },
  //   })
  //     .then(res => {
  //       localStorageManager.setAccessToken(res.data.accessToken);
  //       localStorageManager.setRefreshToken(res.data.refreshToken);
  //       console.log("good");

  //       router.push({ path: "/" });
  //     })
  //     .catch(err => {
  //       console.log(err);
  //     });
  // },

  logout(context) {
    apiInstance
      .post("/api/users/logout", {
        accessToken: localStorageManager.getAccessToken(),
        refreshToken: localStorageManager.getRefreshToken(),
      })
      .then(res => {
        console.log(res);
        localStorageManager.removeTokens();

        console.log(localStorageManager.getAccessToken());

        context.commit("TOKEN_FALSE");

        router.push({ path: "/" });
      })
      .catch(err => {
        console.log(err);
      });
  },

  getUserInfo(context) {
    axios({
      method: "get",
      url: `http://localhost:8888/api/users`,
      headers: {
        Authorization: `Bearer ${localStorageManager.getAccessToken()}`,
      },
    })
      .then(res => {
        context.commit("GET_USER_INFO", res.data);
        console.log("success");
        console.log(localStorageManager.getAccessToken());
      })
      .catch(err => {
        console.log("fail");
        console.log(localStorageManager.getAccessToken());
        console.log(err);
      });
  },
  // getUserInfo(context) {
  //   axios
  //     .get("/api/users")
  //     .then(res => {
  //       context.commit("GET_USER_INFO", res.data);
  //       console.log("success");
  //       console.log(localStorageManager.getAccessToken());
  //     })
  //     .catch(err => {
  //       console.log("fail");
  //       console.log(localStorageManager.getAccessToken());
  //       console.log(err);
  //     });
  // },

  userInfoChange(context, changeform) {
    apiTokenInstance
      .put(`/api/users`, {
        userName: changeform.user_name,
        userId: changeform.user_id,
        userPhone: changeform.user_phone,
        userAddress: changeform.user_address,
        corporateRegistrationNumber: changeform.corporate_registration_number,
      })
      .then(res => {
        context.commit("USER_INFO_CHANGE", res.data);
      })
      .catch(err => {
        console.log(err);
      });
  },
};

export default {
  namespaced: true,
  state,
  getters,
  mutations,
  actions,
};
