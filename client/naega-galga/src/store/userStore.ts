import apiInstance from "@/api/apiInstance";

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
  GET_USER_INFO(state, user_info) {
    state.user_info = user_info;
  },
  SAVE_TOKEN(state, token) {
    state.token = token;
  },
};
const actions = {
  get_user_info(context) {
    apiInstance
      .get(`api/v1/user`)
      .then(res => {
        context.commit("GET_USER_INFO", res.data.key);
      })
      .catch(err => {
        console.log(err);
      });
  },
  join(context, joinform) {
    apiInstance
      .post(`/api/v1/join`, {
        data: {
          id: joinform.id,
          password1: joinform.password1,
          password2: joinform.password2,
          name: joinform.name,
          phone_number: joinform.phone_number,
          register_number: joinform.register_number,
        },
      })
      .then(res => {
        context.commit("SAVE_TOKEN", res.data.key);
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
