const state = {
  user_info: {
    user_index: "-1",
    user_id: "로그인이 되지 않음!",
    user_phone: "",
    user_name: "",
    user_address: "",
    corporate_registration_number: null,
  },
  isToken: false,
};

const getters = {
  userIndex: state => state.user_info.user_index,
  isLogin: state => state.isToken,
};

const mutations = {
  CHECK_TOKEN(state) {
    state.isToken = !state.isToken;
  },

  GET_USER_INFO(state, user_info) {
    state.user_info.user_index = user_info.userIndex;
    state.user_info.user_name = user_info.userName;
    state.user_info.user_id = user_info.userId;
    state.user_info.user_phone = user_info.userPhone;
    state.user_info.user_address = user_info.userAddress;
    state.user_info.corporate_registration_number =
      user_info.corporateRegistrationNumber;
  },

  USER_INFO_CHANGE(state, changeform) {
    state.user_info.user_name = changeform.user_name;
    state.user_info.user_id = changeform.user_id;
    state.user_info.user_phone = changeform.user_phone;
    state.user_info.user_address = changeform.user_address;
    state.user_info.corporate_registration_number =
      changeform.corporate_registration_number;
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
