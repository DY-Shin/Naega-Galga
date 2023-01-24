/* eslint-disable no-unused-vars */

enum AlertType {
  Success = "success",
  Info = "info",
  Warning = "warning",
  Error = "error",
}

interface AlertInfo {
  alertType: AlertType;
  title: string;
  description: string;
}

const state = {
  isShow: false,
  alertInfo: {
    alertType: AlertType.Success,
    title: "",
    description: "",
  },
};
const getters = {
  getIsShow: state => state.isShow,
  getAlertInfo: state => state.alertInfo,
};
const mutations = {
  SET_IS_SHOW(state, value: boolean) {
    state.isShow = value;
  },
  SET_ALERT_INFO(state, obj: AlertInfo) {
    state.alertInfo = { ...obj };
  },
};

export default {
  namespaced: true,
  state,
  getters,
  mutations,
};
