import apiInstance from "@/api/apiInstance";
import { createStore } from "vuex";

export default createStore({
  state: {
    token: null,
  },
  getters: {},
  mutations: {
    SAVE_TOKEN(state, token) {
      state.token = token;
    },
  },
  actions: {
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
  },
  modules: {},
});
