const state = {
  opIndex: -1,
  opName: -1,
  chatRoomIndex: -1,
  isChatRoomOpen: false,
  getChatContent: false,
};

const getters = {
  opIndex: state => state.opIndex,
  opName: state => state.opName,
  chatRoomIndex: state => state.chatRoomIndex,
  isChatRoomOpen: state => state.isChatRoomOpen,
};

const mutations = {
  GET_PRODUCT_INFO(state, user_info) {
    state.opIndex = user_info.userIndex;
    state.opName = user_info.userName;
  },
  CHANGE_CHATROOM_STATUS(state, status) {
    state.isChatRoomOpen = status;
  },
  CHANGE_OP_INDEX(state, index) {
    state.opIndex = index;
  },
  CHANGE_OP_NAME(state, name) {
    state.opName = name;
  },
  CHANGE_CHATROOM_INDEX(state, index) {
    state.chatRoomIndex = index;
  },
  CHANGE_GET_CHAT_CONTENT(state, status) {
    state.getChatContent = status;
  },
};

const actions = {};

export default {
  namespaced: true,
  state,
  getters,
  mutations,
  actions,
};
