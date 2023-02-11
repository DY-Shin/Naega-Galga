import axios, { AxiosResponse } from "axios";
import localStorageManager from "@/utils/localStorageManager";

async function join(joinform) {
  const response: AxiosResponse = await axios.post("api/users", {
    userId: joinform.user_id,
    userPassword: joinform.user_password,
    userName: joinform.user_name,
    userPhone: joinform.user_phone,
    corporateRegistrationNumber: joinform.corporate_registration_number,
    userAddress: joinform.user_address,
  });
  return response.status;
}

async function login(loginform) {
  const response: AxiosResponse = await axios.post(`api/users/login`, {
    userId: loginform.id,
    userPassword: loginform.password,
  });
  return response;
}

async function logout() {
  const response: AxiosResponse = await axios.post(
    `api/users/logout`,
    {
      accessToken: localStorageManager.getAccessToken(),
      refreshToken: localStorageManager.getRefreshToken(),
    },
    {
      headers: {
        Authorization: `Bearer ${localStorageManager.getAccessToken()}`,
      },
    }
  );
  return response;
}

async function checkPassword(passwordForm) {
  const response: AxiosResponse = await axios.post(
    `/api/users/password`,
    {
      checkPassword: passwordForm.password,
    },
    {
      headers: {
        Authorization: `Bearer ${localStorageManager.getAccessToken()}`,
      },
    }
  );
  return response;
}

async function changePassword(password_change_form) {
  const response: AxiosResponse = await axios.put(
    `api/users/password`,
    {
      toBePassword: password_change_form.new_password,
    },
    {
      headers: {
        Authorization: `Bearer ${localStorageManager.getAccessToken()}`,
      },
    }
  );
  return response;
}

async function userDelete(passwordForm) {
  const response: AxiosResponse = await axios.post(`/api/users/delete`, {
    accessToken: localStorageManager.getAccessToken(),
    refreshToken: localStorageManager.getRefreshToken(),
    checkPassword: passwordForm.password,
  });
  return response;
}

async function getUserInfo() {
  const response: AxiosResponse = await axios.get("/api/users", {
    headers: {
      Authorization: `Bearer ${localStorageManager.getAccessToken()}`,
    },
  });
  return response;
}

async function userInfoChange(changeform) {
  const response: AxiosResponse = await axios.put(
    "/api/users",
    {
      userName: changeform.user_name,
      userId: changeform.user_id,
      userPhone: changeform.user_phone,
      userAddress: changeform.user_address,
      corporateRegistrationNumber: changeform.corporate_registration_number,
    },
    {
      headers: {
        Authorization: `Bearer ${localStorageManager.getAccessToken()}`,
      },
    }
  );
  return response;
}

async function getRegistProduct() {
  const response: AxiosResponse = await axios.get(`api/users/me/products`, {
    headers: {
      Authorization: `Bearer ${localStorageManager.getAccessToken()}`,
    },
  });
  return response;
}

async function getReservateProduct() {
  const response: AxiosResponse = await axios.get(`api/users/me/reservations`, {
    headers: {
      Authorization: `Bearer ${localStorageManager.getAccessToken()}`,
    },
  });
  return response;
}
export {
  join,
  login,
  logout,
  checkPassword,
  changePassword,
  userDelete,
  getUserInfo,
  userInfoChange,
  getRegistProduct,
  getReservateProduct,
};
