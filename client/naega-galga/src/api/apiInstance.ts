import axios, { AxiosInstance } from "axios";

//token을 쓰지 않는 객체
const apiInstance: AxiosInstance = axios.create({
  baseURL: process.env.VUE_APP_API_BASE_URL,
  headers: {
    "Content-Type": "application/json;charset=utf-8",
  },
});

export default apiInstance;
