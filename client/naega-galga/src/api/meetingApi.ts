import axios from "axios";

const getOneOnOneMeetingInfo = async (meetingIndex: number) => {
  const response = await axios.get(`api/meeting/one-on-one/${meetingIndex}`);
  return response;
};

const getOneOnManyMeetingInfo = async (explanationIndex: number) => {
  const response = await axios.get(
    `api/meeting/one-on-many/${explanationIndex}`
  );
  return response;
};

export { getOneOnOneMeetingInfo, getOneOnManyMeetingInfo };
