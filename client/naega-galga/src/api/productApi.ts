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

async function editProduct(product, id: number): Promise<number> {
  const response: AxiosResponse = await axios.put(
    `/api/products/${id}`,
    product,
    {
      headers: {
        "Content-Type": "multipart/form-data",
        Authorization: `Bearer ${localStorageManager.getAccessToken()}`,
      },
    }
  );
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

async function addProductWish(productid: number, userId: number) {
  console.log(productid + " " + userId);
  const response: AxiosResponse = await axios.post(
    `/api/products/wish`,
    {
      userIndex: userId,
      productIndex: userId,
    },
    {
      headers: {
        Authorization: `Bearer ${localStorageManager.getAccessToken()}`,
      },
    }
  );
  return response;
}

async function deleteProductWish(productIndex: number, userIndex: number) {
  const response: AxiosResponse = await axios.delete(
    `/api/products/wish/${productIndex}/${userIndex}`,
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

async function addProductReserve(
  owner: number,
  guest: number,
  date: string
): Promise<number> {
  console.log(owner + " " + guest + " " + date + " !!!");
  const response: AxiosResponse = await axios.post(
    `/api/reserve/${date}`,
    {
      owner: owner,
      guest: guest,
      date: date,
    },
    {
      headers: {
        "Content-Type": "multipart/form-data",
        Authorization: `Bearer ${localStorageManager.getAccessToken()}`,
      },
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
  searchProduct,
};
