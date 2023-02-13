import { createStore } from "vuex";
import createPersistedState from "vuex-persistedstate";
import userStore from "./userStore";
import alertStore from "./alertStore";
import chatStore from "./chatStore";

export default createStore({
  modules: {
    userStore,
    alertStore,
    chatStore,
  },

  plugins: [
    createPersistedState({
      paths: ["userStore", "chatStore"],
    }),
  ],
});
