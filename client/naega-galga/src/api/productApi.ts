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

export { addProduct };
