import axios from "axios";
import localStorageManager from "@/utils/localStorageManager";

const getOneOnOneMeetingInfo = async (meetingIndex: number) => {
  const response = await axios.get(`api/meeting/one-on-one/${meetingIndex}`, {
    headers: {
      Authorization: `Bearer ${localStorageManager.getAccessToken()}`,
    },
  });
  return response;
};

const getOneOnManyMeetingInfo = async (explanationIndex: number) => {
  const response = await axios.get(
    `api/meeting/one-on-many/${explanationIndex}`,
    {
      headers: {
        Authorization: `Bearer ${localStorageManager.getAccessToken()}`,
      },
    }
  );
  return response;
};

export { getOneOnOneMeetingInfo, getOneOnManyMeetingInfo };
