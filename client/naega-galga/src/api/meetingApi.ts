import apiTokenInstance from "./apiTokenInstance";

const getOneOnOneMeetingInfo = async (meetingIndex: number) => {
  const response = await apiTokenInstance.get(
    `api/meeting/one-on-one/${meetingIndex}`
  );
  return response;
};

export { getOneOnOneMeetingInfo };
