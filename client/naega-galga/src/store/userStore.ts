import apiInstance from "@/api/apiInstance";
import apiTokenInstance from "@/api/apiTokenInstance";
import localStorageManager from "@/utils/localStorageManager";
import router from "@/router";

const state = {
  user_info: {
    user_index: "",
    user_id: "",
    user_phone: "",
    user_name: "",
    user_address: "",
    corporate_registration_number: null,
  },
};

const getters = {};

const mutations = {
  GET_USER_INFO(state, user_info) {
    state.user_info.user_name = user_info.userName;
    state.user_info.user_id = user_info.userId;
    state.user_info.user_phone = user_info.userPhone;
    state.user_info.user_address = user_info.userAddress;
  },

  USER_INFO_CHANGE(state, changeform) {
    state.user_info.user_name = changeform.user_name;
    state.user_info.user_id = changeform.user_id;
    state.user_info.user_phone = changeform.user_phone;
    state.user_info.user_address = changeform.user_address;
  },
};

const actions = {
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
        router.push({ path: "/" });
      })
      .catch(err => {
        console.log(err);
      });
  },

  logout() {
    apiInstance
      .post("/api/users/logout", {
        accessToken: localStorageManager.getAccessToken(),
        refreshToken: localStorageManager.getRefreshToken(),
      })
      .then(res => {
        console.log(res);
        localStorageManager.setAccessToken("");
        localStorageManager.setRefreshToken("");
      })
      .catch(err => {
        console.log(err);
      });
  },

  getUserInfo(context) {
    apiTokenInstance
      .get("/api/users")
      .then(res => {
        context.commit("GET_USER_INFO", res.data);
      })
      .catch(error => {
        if (error) {
          console.log(error);
        }
      });
  },

  userDelete(context, passwordForm) {
    apiInstance
      .post("/api/users/delete", {
        accessToken: localStorageManager.getAccessToken(),
        refreshToken: localStorageManager.getRefreshToken(),
        checkPassword: passwordForm.password,
      })
      .then(res => {
        console.log(res);
      })
      .catch(err => {
        console.log(err);
      });
  },

  userInfoChange(context) {
    apiInstance.put(`/api/users/change`).then(res => {
      context.commit("USER_INFO_CHANGE", res.data.key).catch(err => {
        console.log(err);
      });
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
