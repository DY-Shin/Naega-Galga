import localStorageManager from "@/utils/localStorageManager";
import axios from "axios";

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

export { getExplanationInfo };
