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
      alert("서버 오류로 요청을 실행할 수 없습니다.");
    }
    return Promise.reject(error);
  }
);

const app = createApp(App);
app.config.globalProperties.axios = axios;
app.use(store).use(router).mount("#app");
