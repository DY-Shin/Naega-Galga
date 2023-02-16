import axios, { AxiosResponse } from "axios";
import localStorageManager from "@/utils/localStorageManager";

async function addProduct(product): Promise<number> {
  const response: AxiosResponse = await axios.post("/api/products", product, {
    headers: {
      "Content-Type": "multipart/form-data",
      Authorization: `Bearer ${localStorageManager.getAccessToken()}`,
    },
  });
  return response.status;
}

async function deleteProduct(productId: number): Promise<number> {
  const response: AxiosResponse = await axios.delete(
    `/api/products/${productId}`,
    {
      headers: {
        Authorization: `Bearer ${localStorageManager.getAccessToken()}`,
      },
    }
  );
  return response.status;
}

async function getProduct(productId: number) {
  const response: AxiosResponse = await axios.get(
    `/api/products/${productId}`,
    {
      headers: {
        Authorization: `Bearer ${localStorageManager.getAccessToken()}`,
      },
    }
  );
  return response;
}

async function searchProduct(keyword: string) {
  const response: AxiosResponse = await axios.get(
    `/api/search?address=${keyword}`,
    {
      headers: {
        Authorization: `Bearer ${localStorageManager.getAccessToken()}`,
      },
    }
  );
  return response;
}

export { addProduct, deleteProduct, getProduct, searchProduct };
