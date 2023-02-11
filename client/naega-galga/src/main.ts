import { createApp } from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import axios from "axios";
import ResponseStatus from "./api/responseStatus";
import ApiError from "./types/ApiError";

axios.defaults.baseURL = process.env.VUE_APP_API_BASE_URL;
axios.interceptors.response.use(
  (res: any) => res,
  error => {
    const { status, message }: ApiError = error.response?.data;

    alert(message);
    if (status === ResponseStatus.Forbidden) {
      router.push("/login");
    }
    if (status === ResponseStatus.Conflict) {
      router.push("/");
    }
    if (status === ResponseStatus.InternalServerError) {
      router.replace("/");
    }
    return Promise.reject(error);
  }
);

const app = createApp(App);
app.config.globalProperties.axios = axios;
app.use(store).use(router).mount("#app");
