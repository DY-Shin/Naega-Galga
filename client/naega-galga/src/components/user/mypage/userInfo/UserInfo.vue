<template>
  <div id="mypagedetail">
    <h1>내 정보</h1>
    <hr />

    <!-- 유저 정보를 보여주는 부분 -->

    <el-form v-if="!isChange" label-width="22%" label-position="left">
      <el-form-item label="아이디">
        {{ info.user_id }}
      </el-form-item>

      <el-form-item label="이름">
        {{ info.user_name }}
      </el-form-item>
      <el-form-item label="핸드폰 번호">
        {{ info.user_phone?.slice(0, 3) }}
        -
        {{ info.user_phone?.slice(4, 8) }}
        -
        {{ info.user_phone?.slice(9, 13) }}
      </el-form-item>
      <el-form-item label="주소">
        {{ info.user_address }}
      </el-form-item>
      <el-form-item label="사업자 번호">
        <div class="content" v-show="info.corporate_registration_number">
          {{ info.corporate_registration_number?.slice(0, 3) }}
          -
          {{ info.corporate_registration_number?.slice(3, 5) }}
          -
          {{ info.corporate_registration_number?.slice(5, 10) }}
        </div>

        <div class="content" v-show="!info.corporate_registration_number"></div>

        <el-button @click="putUserInfo" type="primary" class="button-size"
          >수정하기</el-button
        >
      </el-form-item>
    </el-form>

    <!-- 유저 정보를 바꾸는 changeform을 보여주는 부분 -->
    <el-form
      v-if="isChange == true"
      ref="changeformRef"
      :model="changeform"
      :rules="rules"
      label-width="22%"
      label-position="left"
    >
      <el-form-item label="아이디">
        {{ info.user_id }}
      </el-form-item>

      <el-form-item label="이름" prop="user_name">
        <el-input class="content" v-model="changeform.user_name"></el-input>
      </el-form-item>

      <el-form-item label="핸드폰 번호" prop="user_phone">
        <div class="content" style="display: flex">
          <el-input
            v-model="fullUserPhone.first_user_phone"
            placeholder="010"
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
        </div>
      </el-form-item>

      <el-form-item label="주소" prop="user_address">
        <el-input
          class="content"
          v-model="fullAddress.roadAddress"
          readonly
        ></el-input>
        <address-search-button
          class="address-search-button button-size"
          @getRoadAddress="setRoadAddress"
        ></address-search-button>

        <el-form-item class="content">
          <el-input
            v-model="fullAddress.detailAddress"
            placeholder="상세 주소를 입력해주세요."
          >
          </el-input>
        </el-form-item>
      </el-form-item>

      <el-form-item label="사업자 번호" prop="corporate_registration_number">
        <el-input
          class="content"
          v-model="changeform.corporate_registration_number"
          placeholder=" '-' 를 뺀 10자리 사업자 번호를 입력해주세요."
        ></el-input>
        <el-button
          @click="submitForm(changeformRef)"
          type="primary"
          class="button-size"
          >저장하기</el-button
        >
      </el-form-item>
    </el-form>
  </div>

  <hr style="margin-bottom: 100px" />

  <h1>비밀번호 변경</h1>

  <hr />
  <password-change />

  <hr />
  <user-delete-dialog />
</template>

<script lang="ts">
import { defineComponent, computed, reactive, ref } from "vue";
import { useStore } from "vuex";
import type { FormInstance, FormRules } from "element-plus";

import AddressSearchButton from "@/components/common/AddressSearchButton.vue";
import PasswordChange from "./PasswordChange.vue";
import UserDeleteDialog from "./UserDeleteDialog.vue";

import validation from "@/utils/validation";

import { getUserInfo } from "@/api/userApi";
import { userInfoChange } from "@/api/userApi";
import ResponseStatus from "@/api/responseStatus";

import { ElForm, ElFormItem, ElInput, ElButton } from "element-plus";

export default defineComponent({
  name: "UserInfo",
  components: {
    AddressSearchButton,
    PasswordChange,
    UserDeleteDialog,
    ElForm,
    ElFormItem,
    ElInput,
    ElButton,
  },
  setup() {
    const store = useStore();

    const changeformRef = ref<FormInstance>();

    const setRoadAddress = (address: string) => {
      fullAddress.roadAddress = address;
    };

    const composition = async () => {
      const response = await getUserInfo();
      const data = response.data;

      if (response.status === ResponseStatus.Ok) {
        store.commit("userStore/GET_USER_INFO", data);
      } else {
        console.log("err");
      }
    };

    const info = computed(() => store.state.userStore.user_info);

    const fullUserPhone = reactive({
      first_user_phone: "010",
      second_user_phone: null,
      third_user_phone: null,
    });

    const user_phone = ref(
      `${fullUserPhone.first_user_phone} ${fullUserPhone.second_user_phone} ${fullUserPhone.third_user_phone}`
    );

    const fullAddress = reactive({
      roadAddress: "",
      detailAddress: null,
    });

    const user_address = ref(
      `${fullAddress.roadAddress} ${fullAddress.detailAddress}`
    );

    const changeform = reactive({
      user_name: "",
      user_id: "",
      user_phone,
      user_address,
      corporate_registration_number: "",
    });

    const name_rule = (rule: any, value: any, callback: any) => {
      if (value === "") {
        callback(new Error("이름은 반드시 입력해주세요."));
      } else if (
        changeform.user_name.length < 2 ||
        changeform.user_name.length > 8
      ) {
        callback(new Error("이름은 2~8글자 사이로 만들어주세요."));
      } else if (validation.name(changeform.user_name) === false) {
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

    const rules = reactive<FormRules>({
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
          trigger: "blur",
        },
      ],
    });
    let isChange = ref(false);

    const putUserInfo = () => {
      isChange.value = true;
    };

    const saveChangeInfo = async () => {
      const response = await userInfoChange(changeform);
      const data = response.data;

      if (response.status === ResponseStatus.Ok) {
        store.commit("userStore/USER_INFO_CHANGE", data);
        isChange.value = false;
        composition();
      } else {
        console.log("err");
      }
    };

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
          saveChangeInfo();
          console.log(valid);
        } else {
          console.log("error submit", fields);
        }
      });
    };

    return {
      composition,
      info,
      changeformRef,
      changeform,
      rules,
      isChange,
      putUserInfo,
      submitForm,
      saveChangeInfo,
      fullUserPhone,
      fullAddress,
      setRoadAddress,
    };
  },
});
</script>

<style scoped>
.content {
  width: 70%;
}

.button-size {
  margin-left: 5px;
  width: 22%;
}

hr scoped {
  border: solid 2px gray;
}

.el-form-item__label {
  font-size: large;
}
</style>
