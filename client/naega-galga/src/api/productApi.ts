import { AxiosResponse } from "axios";
import apiTokenInstance from "./apiTokenInstance";

async function addProduct(product): Promise<number> {
  const response: AxiosResponse = await apiTokenInstance.post(
    "/api/product",
    product,
    {
      headers: { "Content-Type": "multipart/form-data" },
    }
  );
  return response.status;
}

async function editProduct(product, id: number): Promise<number> {
  const response: AxiosResponse = await apiTokenInstance.put(
    `/api/product/${id}`,
    product,
    {
      headers: { "Content-Type": "multipart/form-data" },
    }
  );
  return response.status;
}

async function deleteProduct(productId: number): Promise<number> {
  const response: AxiosResponse = await apiTokenInstance.delete(
    `/api/product/${productId}`
  );
  return response.status;
}

async function getProduct(productId: number) {
  const response: AxiosResponse = await apiTokenInstance.get(
    `/api/product/${productId}`
  );
  return response;
}

async function addProductWish(productid: number, userId: number) {
  console.log(productid + " " + userId);
  const response: AxiosResponse = await apiTokenInstance.post(
    `/api/product/wish`,
    {
      userIndex: userId,
      productIndex: userId,
    }
  );
  return response;
}

async function deleteProductWish(productIndex: number, userIndex: number) {
  const response: AxiosResponse = await apiTokenInstance.delete(
    `/api/product/wish/${productIndex}/${userIndex}`
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
  date: Date
): Promise<number> {
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
