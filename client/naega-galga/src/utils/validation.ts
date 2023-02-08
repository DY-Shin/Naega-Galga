const validId = /^[a-z0-9]{4,12}$/;
const validatePassword =
  /^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\(\\)\-_=+]).{8,16}$/;
const validateName = /^[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]{2,10}/;

const validation = {
  id: id => {
    if (validId.test(id) == true) {
      return true;
    }
    return false;
  },

  password: password => {
    if (validatePassword.test(password) == true) {
      return true;
    }
    return false;
  },

  name: name => {
    if (validateName.test(name) == true) {
      return true;
    }
    return false;
  },
};

export default validation;
