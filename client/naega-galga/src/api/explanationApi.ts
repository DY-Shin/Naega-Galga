import localStorageManager from "@/utils/localStorageManager";
import axios from "axios";

const addExplanation = async (
  productIndex: number,
  explanationDate: string
) => {
  const response = await axios.post(
    `/api/meetings/explanations/${productIndex}`,
    {
      dateString: explanationDate,
    },
    {
      headers: {
        Authorization: `Bearer ${localStorageManager.getAccessToken()}`,
      },
    }
  );
  return response;
};

const addExplanationReservation = async (productIndex: number | undefined) => {
  const response = await axios.post(
    `/api/meetings/explanations`,
    {
      productIndex: productIndex,
    },
    {
      headers: {
        Authorization: `Bearer ${localStorageManager.getAccessToken()}`,
      },
    }
  );
  return response;
};

const getExplanationInfo = async (productIndex: number | undefined) => {
  const response = await axios.get(
    `/api/meetings/explanations/${productIndex}`,
    {
      headers: {
        Authorization: `Bearer ${localStorageManager.getAccessToken()}`,
      },
    }
  );
  return response;
};

export { addExplanation, addExplanationReservation, getExplanationInfo };
