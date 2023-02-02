import apiInstance from "@/api/apiInstance";
import apiTokenInstance from "@/api/apiTokenInstance";
import localStorageManager from "@/utils/localStorageManager";

const state = {
  user_info: {
    user_index: "",
    user_id: "",
    user_phone: "",
    user_name: "",
    user_address: "",
    corporate_registration_number: null,
  },
  isToken: true,
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
  testToken(context) {
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
        console.log(state.isToken);
        context.commit("TOKEN_TRUE");
        console.log(state.isToken);
      })
      .catch(err => {
        console.log(err);
      });
  },

  logout(context) {
    apiInstance
      .post("/api/users/logout", {
        accessToken: localStorageManager.getAccessToken(),
        refreshToken: localStorageManager.getRefreshToken(),
      })
      .then(res => {
        console.log(res);
        localStorageManager.setAccessToken("");
        localStorageManager.setRefreshToken("");
        context.commit("TOKEN_FALSE");
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
