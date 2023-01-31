<template>
  <div class="grid">
    <div />
    <el-form
      ref="joinformRef"
      :model="joinform"
      :rules="rules"
      class="joinform"
      style="min-width: 400px"
      status-icon
      scroll-to-error
    >
      <el-form-item>
        <el-form-item prop="user_id" style="width: 50%">
          <p class="p-tag-design">아이디</p>
          <el-input v-model="joinform.user_id" />
        </el-form-item>

        <el-form-item prop="user_name" style="width: 50%">
          <p class="p-tag-design">이름</p>
          <el-input v-model="joinform.user_name"></el-input>
        </el-form-item>
      </el-form-item>

      <el-form-item prop="user_password">
        <p class="p-tag-design">비밀번호</p>
        <el-input v-model="joinform.user_password" type="password"></el-input>
      </el-form-item>

      <el-form-item prop="password_confirm">
        <p class="p-tag-design">비밀번호 확인</p>
        <el-input
          v-model="joinform.password_confirm"
          type="password"
        ></el-input>
      </el-form-item>

      <el-form-item prop="user_phone">
        <p class="p-tag-design">핸드폰 번호</p>
        <el-input v-model="joinform.user_phone"></el-input>
      </el-form-item>

      <el-form-item prop="user_address">
        <p class="p-tag-design">주소</p>
        <address-search-button
          class="address-search-button"
          @getRoadAddress="setRoadAddress"
        ></address-search-button>

        <el-input v-model="address_info.road_address" readonly></el-input>
        <el-input
          v-model="address_info.sebu_address"
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
        ></el-input>
      </el-form-item>

      <el-form-item>
        <el-button @click="submitForm(joinformRef)"> 가입하기 </el-button>
        <el-button @click="cancel"> 취소하기 </el-button>
      </el-form-item>
    </el-form>
    <div />
  </div>
</template>

<script lang="ts">
import { defineComponent, reactive, ref } from "vue";
import { useStore } from "vuex";
import { useRouter } from "vue-router";
import type { FormInstance, FormRules } from "element-plus";

import AddressSearchButton from "@/components/common/AddressSearchButton.vue";

export default defineComponent({
  name: "JoinView",
  components: {
    AddressSearchButton,
  },
  setup() {
    const store = useStore();
    const router = useRouter();
    const visible = ref(false);

    const joinformRef = ref<FormInstance>();

    const setRoadAddress = (address: string) => {
      address_info.road_address = address;
    };

    const address_info = reactive({
      road_address: "",
      sebu_address: "",
    });

    const user_address = ref(
      address_info.road_address + " " + address_info.sebu_address
    );

    const joinform = reactive({
      user_id: "",
      user_name: "",
      user_password: "",
      password_confirm: "",
      user_phone: "",
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
    });

    const submitForm = async (formEl: FormInstance | undefined) => {
      user_address.value =
        address_info.road_address + " " + address_info.sebu_address;
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
      submitForm,
      cancel,
      setRoadAddress,
      address_info,
      user_address,
    };
  },
});
</script>

<style scoped>
.grid {
  display: grid;
  grid-template-columns: 7fr 9fr 7fr;
  padding-top: 50px;
}

.joinform {
  padding-top: 70px;
  padding-bottom: 50px;
  padding-left: 70px;
  padding-right: 70px;

  box-shadow: 0 0 20px 0 rgba(0, 0, 0, 0.2), 0 2px 2px 0 rgba(0, 0, 0, 0.24);
  border: 1px solid;
  border-color: #555555;
}

.p-tag-design {
  margin: 0px;
}

.address-search-button {
  margin-left: auto;
  margin-bottom: 1px;
}
</style>
