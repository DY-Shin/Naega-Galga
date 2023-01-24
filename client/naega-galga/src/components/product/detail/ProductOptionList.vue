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
import { computed, defineComponent, PropType } from "@vue/runtime-core";

export default defineComponent({
  props: {
    options: {
      type: Array as PropType<Array<string>>,
    },
  },
  setup(props) {
    //옵션 정보들을 icon image로 변경
    const optionIconList = computed(() =>
      props.options?.map(option => {
        switch (option) {
          case "냉장고":
            return {
              name: option,
              src: require("@/assets/image/product-option-icon/icon-refrigerator.png"),
            };
          case "에어컨":
            return {
              name: option,
              src: require("@/assets/image/product-option-icon/icon-air-conditioner.png"),
            };
          case "세탁기":
            return {
              name: option,
              src: require("@/assets/image/product-option-icon/icon-washing-machine.png"),
            };
          case "가스레인지":
            return {
              name: option,
              src: require("@/assets/image/product-option-icon/icon-gas-range.png"),
            };
          case "인덕션":
            return {
              name: option,
              src: require("@/assets/image/product-option-icon/icon-induction-stove.png"),
            };
          case "전자레인지":
            return {
              name: option,
              src: require("@/assets/image/product-option-icon/icon-microwaves.png"),
            };
          case "와이파이":
            return {
              name: option,
              src: require("@/assets/image/product-option-icon/icon-wifi.png"),
            };
          case "책상":
            return {
              name: option,
              src: require("@/assets/image/product-option-icon/icon-desk-chair.png"),
            };
          case "옷장":
            return {
              name: option,
              src: require("@/assets/image/product-option-icon/icon-wardrobe.png"),
            };
          case "침대":
            return {
              name: option,
              src: require("@/assets/image/product-option-icon/icon-double-bed.png"),
            };
        }
      })
    );

    return {
      optionIconList,
    };
  },
});
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
