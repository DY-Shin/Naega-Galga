import { AxiosResponse } from "axios";
import apiTokenInstance from "./apiTokenInstance";

async function addProduct(product): Promise<number> {
  const response: AxiosResponse = await apiTokenInstance.post(
    "/api/products",
    product,
    {
      headers: { "Content-Type": "multipart/form-data" },
    }
  );
  return response.status;
}

async function deleteProduct(productId: number): Promise<number> {
  const response: AxiosResponse = await apiTokenInstance.delete(
    `/api/products/${productId}`
  );
  return response.status;
}

async function getProduct(productId: number) {
  const response: AxiosResponse = await apiTokenInstance.get(
    `/api/products/${productId}`
  );
  return response;
}

async function searchProduct(keyword: string) {
  const response: AxiosResponse = await apiTokenInstance.get(
    `/api/search?address=${keyword}`
  );
  return response;
}

async function addProductReserve(
  owner: number,
  guest: number,
  date: string
): Promise<number> {
  console.log(owner + " " + guest + " " + date + " !!!");
  const response: AxiosResponse = await apiTokenInstance.post(
    `/api/reserve/${date}`,
    {
      owner: owner,
      guest: guest,
      date: date,
    },
    {
      headers: { "Content-Type": "multipart/form-data" },
    }
  );
  return response.status;
}
export {
  addProduct,
  deleteProduct,
  getProduct,
  addProductReserve,
  searchProduct,
};
