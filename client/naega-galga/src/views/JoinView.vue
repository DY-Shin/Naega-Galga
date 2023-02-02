<template>
  <div class="wrapper">
    <el-form
      ref="joinformRef"
      :model="joinform"
      :rules="rules"
      class="joinform"
      label-width="29%"
      status-icon
      scroll-to-error
      label-position="left"
    >
      <div class="center-div">
        <img
          class="center-item logo"
          fit:fill
          src="@/assets/image/logo/NGGG.png"
          style="width: 100px; height: 100px"
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

      <el-form-item label="핸드폰 번호" prop="user_phone">
        <el-input v-model="fullUserPhone.first_user_phone"></el-input>
      </el-form-item>
      <el-form-item>
        <el-input v-model="fullUserPhone.second_user_phone"></el-input>

        <el-input v-model="fullUserPhone.third_user_phone"></el-input>
      </el-form-item>

      <el-form-item style="margin: 0px">
        <address-search-button
          class="address-search-button"
          @getRoadAddress="setRoadAddress"
        ></address-search-button>
      </el-form-item>

      <el-form-item label="주소" style="margin-bottom: 1px">
        <el-input v-model="fullAddress.roadAddress" readonly></el-input>
      </el-form-item>

      <el-form-item prop="user_address">
        <el-input
          v-model="fullAddress.detailAddress"
          placeholder="상세 주소를 입력해주세요."
        ></el-input>
      </el-form-item>

      <el-form-item label="사업자이신가요?">
        <el-checkbox label="예" v-model="visible" />
      </el-form-item>

      <el-form-item prop="corporate_registration_number">
        <el-input
          v-show="visible"
          v-model="joinform.corporate_registration_number"
          placeholder="사업자 번호를 입력해주세요."
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
import { defineComponent, reactive, ref } from "vue";
import { useStore } from "vuex";
import { useRouter } from "vue-router";
import type { FormInstance, FormRules } from "element-plus";

import AddressSearchButton from "@/components/common/AddressSearchButton.vue";
import JoinTerms from "@/components/user/mypage/JoinTerms.vue";

export default defineComponent({
  name: "JoinView",
  components: {
    AddressSearchButton,
    JoinTerms,
  },
  setup() {
    const store = useStore();
    const router = useRouter();
    const visible = ref(false);
    const terms_check = ref(false);

    const joinformRef = ref<FormInstance>();

    const setRoadAddress = (address: string) => {
      fullAddress.roadAddress = address;
    };

    const fullUserPhone = reactive({
      first_user_phone: "",
      second_user_phone: "",
      third_user_phone: "",
    });

    const user_phone = ref(
      fullUserPhone.first_user_phone +
        "-" +
        fullUserPhone.second_user_phone +
        "-" +
        fullUserPhone.third_user_phone
    );

    const fullAddress = reactive({
      roadAddress: "",
      detailAddress: "",
    });

    const user_address = ref(
      fullAddress.roadAddress + " " + fullAddress.detailAddress
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

    const password_confirm = (rule: any, value: any, callback: any) => {
      if (value === "") {
        callback(new Error("Please input the password again"));
      } else if (value !== joinform.user_password) {
        callback(new Error("Two inputs don't match!"));
      } else {
        callback();
      }
    };

    const rules = reactive<FormRules>({
      user_id: [
        {
          required: true,
          message: "아이디를 반드시 입력해주세요.",
          trigger: "blur",
        },
        {
          min: 4,
          max: 12,
          message: "아이디는 4~12",
          trigger: "blur",
        },
      ],
      user_name: [
        {
          required: true,
          message: "이름은 반드시 입력해주세요",
          trigger: "blur",
        },
        {
          min: 2,
          max: 8,
          message: "이름은 어쩌고",
          trigger: "blur",
        },
      ],
      user_password: [
        {
          required: true,
          message: "비밀번호는 반드시 입력해주세요.",
          trigger: "blur",
        },
        {
          min: 8,
          message: "비밀번호는 8글자 이상으로 만들어주세요.",
          trigger: "blur",
        },
        {
          max: 16,
          message: "비밀번호는 16글자 이하로 만들어주세요.",
          trigger: "blur",
        },
        {
          validator: password_confirm,
          message: "비밀번호가 서로 다릅니다",
        },
      ],
      password_confirm: [
        {
          required: true,
          message: "비밀번호는 반드시 입력해주세요.",
          trigger: "blur",
        },
        {
          min: 8,
          message: "비밀번호는 8글자 이상으로 만들어주세요.",
          trigger: "blur",
        },
        {
          max: 16,
          message: "비밀번호는 16글자 이하로 만들어주세요.",
          trigger: "blur",
        },
        {
          validator: password_confirm,
          message: "비밀번호는 같아야 한다 이녀석아",
        },
      ],
      user_phone: [
        {
          required: true,
          message: "핸드폰 번호는 반드시 입력해주세요.",
          trigger: "blur",
        },
      ],
      user_address: [
        {
          required: true,
          message: "주소는 반드시 입력해주세요.",
          trigger: "blur",
        },
      ],
    });

    const submitForm = async (formEl: FormInstance | undefined) => {
      user_address.value =
        fullAddress.roadAddress + " " + fullAddress.detailAddress;
      if (!formEl) {
        return;
      }
      await formEl.validate((valid, fields) => {
        if (valid) {
          store.dispatch("userStore/join", joinform);
          router.push({ path: "/login" });
        } else {
          console.log("error submit!", fields);
        }
      });
    };

    const cancel = () => {
      router.push({ path: "/" });
    };

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
.joinform {
  width: 33%;
  min-width: 500px;

  padding-bottom: 60px;
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

.wrapper {
  display: flex;
  justify-content: center;
  align-items: center;
}

.scrollbar {
  height: 100px;
  border: 1px solid;
  border-color: #dcdfe6;
  border-radius: 4px;
  text-align: "center";
}
</style>
