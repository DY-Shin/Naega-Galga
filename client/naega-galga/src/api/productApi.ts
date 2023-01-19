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

export { addProduct, editProduct, deleteProduct };
