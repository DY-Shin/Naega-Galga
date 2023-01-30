import { AxiosResponse } from "axios";
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

export {
  addProduct,
  editProduct,
  deleteProduct,
  getProduct,
  addProductWish,
  deleteProductWish,
};
