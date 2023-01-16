// import axios, { AxiosInstance } from "axios";
// import ResponseStatus from "./responseStatus";
// import localStorageManager from "@/utils/localStorageManager";

//토큰 쓰는 객체
// const apiTokenInstance: AxiosInstance = axios.create({
//   baseURL: process.env.VUE_APP_API_BASE_URL,
//   headers: {
//     "Content-Type": "application/json;charset=utf-8",
//     "X-ACCESS-TOKEN": `${localStorageManager.getAccessToken()}`,
//   },
// });

// async function getRefreshToken(): Promise<string | null> {
//   try {
//     const {
//       data: { accessToken, refreshToken },
//     } = await apiTokenInstance.get("/refresh");

//     localStorageManager.setAccessToken(accessToken);

//     if (refreshToken !== null) {
//       localStorageManager.setRefreshToken(refreshToken);
//     }
//     return accessToken;
//   } catch (e) {
//     localStorageManager.removeTokens();
//   }
//   return null;
// }

////여기서 타입 에러
// apiTokenInstance.interceptors.request.use(request => {
//   if (!request.headers) {
//     return request;
//   }

//   let token: string | null = null;

//   //url이 token refresh면 refresh token 추가
//   if (request.url === "/refresh") {
//     token = localStorageManager.getRefreshToken();
//   } else {
//     token = localStorageManager.getAccessToken();
//   }

//   if (token !== null) {
//     request.headers["Authorization"] = `Bearer ${token}`;
//   }
// });

// apiTokenInstance.interceptors.response.use(
//   res => res,
//   async function (error) {
//     const {
//       config,
//       response: { status },
//     } = error;

//     //토큰 만료나 토큰이 없을시 리프레쉬 하도록
//     if (status === ResponseStatus.Unauthorized) {
//       const accessToken = await getRefreshToken();
//       if (accessToken) {
//         config.headers.Authorization = `${accessToken}`;
//       }
//       return apiTokenInstance(config);
//     }
//   }
// );
