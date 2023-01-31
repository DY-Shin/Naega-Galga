<template>
  <div class="container">
    <div
      class="icon-container"
      v-for="option in optionIconList"
      :key="option?.name"
    >
      <span class="icon-bg">
        <img class="icon" :src="option?.src" />
      </span>
      <div class="option-name">
        {{ option?.name }}
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { computed } from "vue";

export default {
  props: {
    options: {
      type: Object,
    },
  },
  setup(props) {
    interface OptionIcon {
      name: string;
      src: any;
    }
    //옵션 정보들을 icon image로 변경
    const optionIconList = computed(() => {
      const arr: OptionIcon[] = [];

      if (props.options?.optionFridge) {
        arr.push({
          name: "냉장고",
          src: require("@/assets/image/product-option-icon/icon-refrigerator.png"),
        });
      }
      if (props.options?.optionAirConditioner) {
        arr.push({
          name: "에어컨",
          src: require("@/assets/image/product-option-icon/icon-air-conditioner.png"),
        });
      }
      if (props.options?.optionWashingMachine) {
        arr.push({
          name: "세탁기",
          src: require("@/assets/image/product-option-icon/icon-washing-machine.png"),
        });
      }
      if (props.options?.optionGasStove) {
        arr.push({
          name: "가스레인지",
          src: require("@/assets/image/product-option-icon/icon-gas-range.png"),
        });
      }
      if (props.options?.optionInduction) {
        arr.push({
          name: "인덕션",
          src: require("@/assets/image/product-option-icon/icon-induction-stove.png"),
        });
      }
      if (props.options?.optionMicrowave) {
        arr.push({
          name: "전자레인지",
          src: require("@/assets/image/product-option-icon/icon-microwaves.png"),
        });
      }
      if (props.options?.optionWifi) {
        arr.push({
          name: "와이파이",
          src: require("@/assets/image/product-option-icon/icon-wifi.png"),
        });
      }
      if (props.options?.optionDesk) {
        arr.push({
          name: "책상",
          src: require("@/assets/image/product-option-icon/icon-desk-chair.png"),
        });
      }
      if (props.options?.optionCloset) {
        arr.push({
          name: "옷장",
          src: require("@/assets/image/product-option-icon/icon-wardrobe.png"),
        });
      }
      if (props.options?.optionBed) {
        arr.push({
          name: "침대",
          src: require("@/assets/image/product-option-icon/icon-double-bed.png"),
        });
      }
      return arr;
    });
    return {
      optionIconList,
    };
  },
};
</script>

<style scoped>
.container {
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  align-content: center;
}
.icon-container {
  margin-left: 2rem;
}

.icon-bg {
  padding: 10px;
  display: inline-block;
  background-color: #c8c9cc;
  border-radius: 5px;
}
.icon {
  width: 3rem;
  height: 3rem;
  object-fit: scale-down;
}

.option-name {
  font-size: var(--el-font-size-large);
  text-align: center;
  margin-top: 2px;
}
</style>
