import { createStore } from "vuex";
import userStore from "./userStore";
import alertStore from "./alertStore";

export default createStore({
  modules: {
    userStore,
    alertStore,
  },
});
