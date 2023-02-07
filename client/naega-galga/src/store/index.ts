import { createStore } from "vuex";
import createPersistedState from "vuex-persistedstate";
import userStore from "./userStore";
import alertStore from "./alertStore";

export default createStore({
  modules: {
    userStore,
    alertStore,
  },

  plugins: [
    createPersistedState({
      paths: ["userStore"],
    }),
  ],
});
