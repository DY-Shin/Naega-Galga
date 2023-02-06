import axios from "axios";

const getOneOnOneMeetingInfo = async (meetingIndex: number) => {
  const response = await axios.get(`api/meeting/one-on-one/${meetingIndex}`);
  return response;
};

export { getOneOnOneMeetingInfo };
