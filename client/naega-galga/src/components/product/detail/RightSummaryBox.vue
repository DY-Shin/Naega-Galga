<template>
  <div class="container">
    <div class="title-box">
      <span class="font-large font-semi-bold">
        {{ summary.title }}
      </span>
    </div>
    <div class="font-medium margin-top">
      {{ summary.floor }}
    </div>
    <div class="font-medium margin-top">
      {{ summary.managePrice }}
    </div>
    <div class="font-medium margin-top">
      {{ summary.sellerId }}
    </div>
    <div class="margin-top width-full">
      <el-button type="primary" class="width-full">문의하기</el-button>
    </div>
  </div>
</template>

<script lang="ts">
import { computed, defineComponent } from "vue";
import { Plus } from "@element-plus/icons-vue";

export default defineComponent({
  props: {
    summaryValue: {
      type: Object,
      value: {
        productIndex: Number,
        productType: String,
        price: String,
        floor: String,
        managePrice: Number,
        sellerId: String,
        sellerIndex: Number,
      },
    },
  },
  setup(props) {
    // const userIndex = 1;
    const summary = computed(() => {
      const obj = { ...props.summaryValue };
      return {
        title: `${obj?.productType} ${obj?.price}`,
        floor: obj?.floor,
        managePrice: `관리비 월 ${obj?.managePrice}만`,
        sellerId: obj?.sellerId,
      };
    });

    return {
      summary,
      Plus,
    };
  },
});
</script>

<style scoped>
.container {
  border: 1px solid var(--el-color-info);
  top: 50px;
  padding: 20px;
  width: 250px;
}

.font-semi-bold {
  font-weight: 600;
}

.font-large {
  font-size: var(--el-font-size-extra-large);
}
.font-medium {
  font-size: var(--el-font-size-medium);
}

.font-small {
  font-size: var(--el-font-size-small);
}

.margin-top {
  margin-top: 20px;
}

.margin-bottom {
  margin-bottom: 10px;
}

.title-box {
  display: flex;
  flex-direction: row;
  align-items: center;
}

.heart-icon {
  width: 20px;
  height: 20px;
}

.heart-btn {
  position: relative;
  right: -90px;
}

.width-full {
  width: 100%;
}

.explanation-text {
  background-color: var(--el-color-error);
  color: #f4f4f5;
  padding: 8px 15px;
  margin-right: 10px;
  border-radius: 5px;
}
</style>
