const validId = /^[a-z0-9]{4,12}$/;
const validatePassword =
  /^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\(\\)\-_=+]).{8,16}$/;
const validateName = /^[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]{2,10}/;
const validateNumber = /^[0-9|null]*$/;

const validation = {
  id: id => {
    if (validId.test(id)) {
      return true;
    }
    return false;
  },

  password: password => {
    if (validatePassword.test(password)) {
      return true;
    }
    return false;
  },

  name: name => {
    if (validateName.test(name)) {
      return true;
    }
    return false;
  },
  phone: phone => {
    if (validateNumber.test(phone)) {
      return true;
    }
    return false;
  },
  registration_number: registration_number => {
    if (validateNumber.test(registration_number)) {
      return true;
    }
    return false;
  },
};

export default validation;
