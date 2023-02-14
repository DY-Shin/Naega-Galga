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

const leaveOutOneOnOneMeetingInfo = async (
  meetingIndexValue: number,
  userIndexValue: number,
  tokenValue: string
) => {
  const response = await axios.post(
    `/api/meeting/one-on-one/${meetingIndexValue}`,
    {
      userIndex: userIndexValue,
      token: tokenValue,
    },
    {
      headers: {
        Authorization: `Bearer ${localStorageManager.getAccessToken()}`,
      },
    }
  );
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

export {
  getOneOnOneMeetingInfo,
  leaveOutOneOnOneMeetingInfo,
  getOneOnManyMeetingInfo,
};
