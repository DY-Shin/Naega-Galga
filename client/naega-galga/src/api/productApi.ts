import { AxiosResponse } from "axios";
//TODO : 나중에 token instance로 바꿀것
import apiTokenInstance from "./apiInstance";

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

async function editProduct(product, id: number): Promise<number> {
  const response: AxiosResponse = await apiTokenInstance.put(
    `/api/products/${id}`,
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

async function addProductWish(productid: number, userId: number) {
  console.log(productid + " " + userId);
  const response: AxiosResponse = await apiTokenInstance.post(
    `/api/products/wish`,
    {
      userIndex: userId,
      productIndex: userId,
    }
  );
  return response;
}

async function deleteProductWish(productIndex: number, userIndex: number) {
  const response: AxiosResponse = await apiTokenInstance.delete(
    `/api/products/wish/${productIndex}/${userIndex}`
  );
  return response;
}
async function SearchProduct(keyword: string) {
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
  editProduct,
  deleteProduct,
  getProduct,
  addProductWish,
  deleteProductWish,
  addProductReserve,
  SearchProduct,
};
