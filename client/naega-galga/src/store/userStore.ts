import apiInstance from "@/api/apiInstance";
import apiTokenInstance from "@/api/apiTokenInstance";

const state = {
  user_info: {
    user_name: "창식이",
    user_id: "sige_tank",
    user_phone: "010-9169-5671",
    user_address: "그레이빌 206호",
  },
  token: null,
};

const getters = {};

const mutations = {
  SAVE_TOKEN(state, token) {
    state.token = token;
  },
  LOGOUT(state) {
    state.token = null;
  },

  GET_USER_INFO(state, user_info) {
    state.user_info = user_info;
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
      .post(`/api/users`, {
        data: {
          user_id: joinform.user_id,
          user_password: joinform.user_password,
          user_name: joinform.user_name,
          user_phone: joinform.user_phone,
          corporate_registration_number: joinform.corporate_registration_number,
        },
      })
      .then(res => {
        context.commit("SAVE_TOKEN", res.data.key);
      })
      .catch(err => {
        console.log(err);
      });
  },

  login(context, form) {
    apiInstance
      .post(`/api/users/login`, {
        data: {
          id: form.id,
          password: form.password,
        },
      })
      .then(res => {
        context.commit("SAVE_TOKEN", res.data.key);
      })
      .catch(err => {
        console.log(err);
      });
  },

  logout(context) {
    apiTokenInstance.post(`/api/users/logout`).then(res => {
      context.commit("LOGOUT", res.data.key).catch(err => {
        console.log(err);
      });
    });
  },

  passwordCheck() {
    apiTokenInstance.post(`/api/users/checkpassword`);
  },

  userDelete(context) {
    apiInstance.delete(`/api/users/delete`).then(res => {
      context.commit("LOGOUT", res.data.key).catch(err => {
        console.log(err);
      });
    });
  },

  get_user_info(context) {
    apiInstance
      .get(`/api/users/me`)
      .then(res => {
        context.commit("GET_USER_INFO", res.data.key);
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
