<template>
  <div class="wrapper">
    <el-form
      ref="joinformRef"
      :model="joinform"
      :rules="rules"
      class="joinform"
      label-width="29%"
      label-position="left"
      status-icon
      scroll-to-error
    >
      <div class="center-div">
        <img
          class="center-item logo"
          fit:fill
          src="@/assets/image/logo/NGGG.png"
          style="height: 100px"
        />
      </div>

      <el-form-item label="아이디" prop="user_id">
        <el-input v-model="joinform.user_id" />
      </el-form-item>

      <el-form-item label="비밀번호" prop="user_password">
        <el-input v-model="joinform.user_password" type="password"></el-input>
      </el-form-item>

      <el-form-item label="비밀번호 확인" prop="password_confirm">
        <el-input
          v-model="joinform.password_confirm"
          type="password"
        ></el-input>
      </el-form-item>

      <el-form-item label="이름" prop="user_name">
        <el-input v-model="joinform.user_name"></el-input>
      </el-form-item>

      <el-form-item label="핸드폰 번호" prop="user_phone" style="display: flex">
        <el-input
          v-model="fullUserPhone.first_user_phone"
          style="flex: 3"
          maxlength="3"
          disabled
        ></el-input>

        <p style="flex: 1; text-align: center; margin: 0px">-</p>

        <el-input
          v-model="fullUserPhone.second_user_phone"
          placeholder="1234"
          style="flex: 4"
          maxlength="4"
        ></el-input>

        <p style="flex: 1; text-align: center; margin: 0px">-</p>

        <el-input
          v-model="fullUserPhone.third_user_phone"
          placeholder="5678"
          style="flex: 4"
          maxlength="4"
        ></el-input>
      </el-form-item>

      <el-form-item style="margin-bottom: 1px">
        <address-search-button
          class="address-search-button"
          @getRoadAddress="setRoadAddress"
        ></address-search-button>
      </el-form-item>

      <el-form-item label="주소" prop="user_address">
        <el-input v-model="fullAddress.roadAddress" readonly></el-input>
        <el-input
          v-model="fullAddress.detailAddress"
          placeholder="상세 주소를 입력해주세요."
        >
        </el-input>
      </el-form-item>

      <el-form-item label="사업자이신가요?">
        <el-checkbox label="예" v-model="visible" />
      </el-form-item>

      <el-form-item v-show="visible" prop="corporate_registration_number">
        <el-input
          v-model="joinform.corporate_registration_number"
          placeholder="'-' 를 뺀 10자리 사업자 번호를 입력해주세요."
          maxlength="10"
        ></el-input>
      </el-form-item>

      <el-scrollbar class="scrollbar">
        <join-terms />
      </el-scrollbar>

      <el-form-item>
        <el-checkbox class="address-search-button" v-model="terms_check"
          >동의합니다.</el-checkbox
        >
      </el-form-item>

      <div class="center-div">
        <el-button
          class="center-item"
          @click="submitForm(joinformRef)"
          v-bind:disabled="!terms_check"
        >
          가입하기
        </el-button>
        <el-button calss="center-item" @click="cancel"> 취소하기 </el-button>
      </div>
    </el-form>
  </div>
</template>

<script lang="ts">
import { defineComponent, reactive, ref, watch } from "vue";
import { useRouter } from "vue-router";
import type { FormInstance, FormRules } from "element-plus";

import AddressSearchButton from "@/components/common/AddressSearchButton.vue";
import JoinTerms from "@/components/user/mypage/JoinTerms.vue";

import validation from "@/utils/validation";
import { join } from "@/api/userApi";

export default defineComponent({
  name: "JoinView",
  components: {
    AddressSearchButton,
    JoinTerms,
  },
  setup() {
    const router = useRouter();
    const visible = ref(false);
    const terms_check = ref(false);

    const joinformRef = ref<FormInstance>();

    const setRoadAddress = (address: string) => {
      fullAddress.roadAddress = address;
    };

    const fullUserPhone = reactive({
      first_user_phone: "010",
      second_user_phone: null,
      third_user_phone: null,
    });

    const user_phone = ref(
      `${fullUserPhone.first_user_phone} +
        ${fullUserPhone.second_user_phone} +
        ${fullUserPhone.third_user_phone}`
    );

    const fullAddress = reactive({
      roadAddress: "",
      detailAddress: null,
    });

    const user_address = ref(
      `${fullAddress.roadAddress} ${fullAddress.detailAddress}`
    );

    const joinform = reactive({
      user_id: "",
      user_name: "",
      user_password: "",
      password_confirm: "",
      user_phone,
      user_address,
      corporate_registration_number: null,
    });

    const id_rule = (rule: any, value: any, callback: any) => {
      if (value === "") {
        callback(new Error("아이디는 반드시 입력해주세요."));
      } else if (joinform.user_id.length < 4 || joinform.user_id.length > 12) {
        callback(new Error("아이디는 4~12글자 사이로 만들어주세요."));
      } else if (validation.id(joinform.user_id) === false) {
        callback(new Error("아이디는 소문자와 숫자로 만들어주세요"));
      } else {
        callback();
      }
    };

    const password_rule = (rule: any, value: any, callback: any) => {
      if (value === "") {
        callback(new Error("비밀번호는 반드시 입력해주세요"));
      } else if (joinform.user_password.length < 8) {
        callback(new Error("비밀번호는 8글자 이상으로 만들어주세요."));
      } else if (joinform.user_password.length > 16) {
        callback(new Error("비밀번호는 16글자 이하로 만들어주세요."));
      } else if (validation.password(joinform.user_password) === false) {
        callback(
          new Error("비밀번호는 문자, 숫자, 특수문자를 섞어 만들어주세요")
        );
      } else {
        callback();
      }
    };

    const password_confirm_rule = (rule: any, value: any, callback: any) => {
      if (value === "") {
        callback(new Error("비밀번호 확인은 반드시 입력해주세요"));
      } else if (joinform.password_confirm.length < 8) {
        callback(new Error("비밀번호는 8글자 이상으로 만들어주세요."));
      } else if (joinform.password_confirm.length > 16) {
        callback(new Error("비밀번호는 16글자 이하로 만들어주세요."));
      } else if (
        value !== joinform.user_password &&
        joinform.password_confirm.length > 7
      ) {
        callback(new Error("비밀번호가 서로 다릅니다!"));
      } else {
        callback();
      }
    };

    const name_rule = (rule: any, value: any, callback: any) => {
      if (value === "") {
        callback(new Error("이름은 반드시 입력해주세요."));
      } else if (
        joinform.user_name.length < 2 ||
        joinform.user_name.length > 8
      ) {
        callback(new Error("이름은 2~8글자 사이로 만들어주세요."));
      } else if (validation.name(joinform.user_name) === false) {
        callback(new Error("이름은 한글만 가능합니다!"));
      } else {
        callback();
      }
    };

    const phone_rule = (rule: any, value: any, callback: any) => {
      if (
        !fullUserPhone.second_user_phone != null &&
        fullUserPhone.second_user_phone?.["length"] != "4"
      ) {
        callback(new Error("올바르지 않은 핸드폰 번호입니다."));
      } else if (fullUserPhone.third_user_phone == "") {
        callback(new Error("올바르지 않은 핸드폰 번호입니다."));
      } else if (
        validation.phone(fullUserPhone.second_user_phone) == false ||
        validation.phone(fullUserPhone.third_user_phone) == false
      ) {
        callback(new Error("핸드폰 번호는 숫자만 가능합니다!"));
      } else {
        callback();
      }
    };

    const address_rule = (rule: any, value: any, callback: any) => {
      if (!fullAddress.roadAddress) {
        callback(new Error("주소는 반드시 입력해주세요."));
      } else if (
        fullAddress.roadAddress &&
        fullAddress.detailAddress?.["length"] == 0
      ) {
        callback(new Error("주소는 반드시 입력해주세요."));
      } else {
        callback();
      }
    };

    const registration_number_rule = (rule: any, value: any, callback: any) => {
      if (!joinform.corporate_registration_number && visible.value == true) {
        callback(new Error("사업자이시면 사업자 번호는 반드시 입력해주세요"));
      } else if (
        validation.registration_number(
          joinform.corporate_registration_number
        ) == false
      ) {
        callback(
          new Error(
            "사업자 번호는 숫자로만 이루어져 있습니다. 다시 입력해주세요."
          )
        );
      } else if (
        joinform.corporate_registration_number?.["length"] != 10 &&
        joinform.corporate_registration_number != null
      ) {
        callback(new Error("사업자 번호는 10자리 숫자입니다."));
      } else {
        callback();
      }
    };

    const rules = reactive<FormRules>({
      user_id: [
        {
          required: true,
          validator: id_rule,
          trigger: "blur",
        },
      ],

      user_password: [
        {
          required: true,
          validator: password_rule,
          trigger: "blur",
        },
      ],

      password_confirm: [
        {
          required: true,
          validator: password_confirm_rule,
          trigger: "blur",
        },
      ],

      user_name: [
        {
          required: true,
          validator: name_rule,
          trigger: "blur",
        },
      ],

      user_phone: [
        {
          required: true,
          validator: phone_rule,
          trigger: "blur",
        },
      ],
      user_address: [
        {
          required: true,
          validator: address_rule,
        },
      ],
      corporate_registration_number: [
        {
          required: true,
          validator: registration_number_rule,
          trigger: "blur",
        },
      ],
    });

    const submitForm = async (formEl: FormInstance | undefined) => {
      user_phone.value =
        fullUserPhone.first_user_phone +
        "-" +
        fullUserPhone.second_user_phone +
        "-" +
        fullUserPhone.third_user_phone;
      user_address.value =
        fullAddress.roadAddress + " " + fullAddress.detailAddress;
      if (!formEl) {
        return;
      }
      await formEl.validate((valid, fields) => {
        if (valid) {
          join(joinform);
          router.push({ path: "/login" });
        } else {
          console.log("error submit!", fields);
        }
      });
    };

    const cancel = () => {
      router.push({ path: "/" });
    };

    watch(
      () => visible.value,
      (_, prev) => {
        if (prev) {
          joinform.corporate_registration_number = null;
          console.log(joinform.corporate_registration_number);
        }
      }
    );

    return {
      visible,
      joinformRef,
      joinform,
      rules,
      setRoadAddress,
      fullUserPhone,
      fullAddress,
      submitForm,
      cancel,
      terms_check,
    };
  },
});
</script>

<style scoped>
.wrapper {
  display: flex;
  justify-content: center;
  align-items: center;
}
.joinform {
  width: 33%;
  min-width: 500px;

  padding-bottom: 20px;
  padding-left: 50px;
  padding-right: 50px;

  border-radius: 10px;
  box-shadow: 0 0 20px 0 rgba(0, 0, 0, 0.2), 0 2px 2px 0 rgba(0, 0, 0, 0.24);
}

.logo {
  margin: 10px;
}

.address-search-button {
  margin-bottom: 0px;
  margin-left: auto;
}

.center-div {
  text-align: center;
}
.center-item {
  display: inline-block;
}

.scrollbar {
  height: 100px;
  border: 1px solid;
  border-color: #dcdfe6;
  border-radius: 4px;
  text-align: "center";
}
</style>
