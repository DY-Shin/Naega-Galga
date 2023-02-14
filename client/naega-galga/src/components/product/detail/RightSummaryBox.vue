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
    <div v-if="!isMine" class="margin-top width-full">
      <el-button type="primary" class="width-full" @click="onClickOpenChat"
        >문의하기</el-button
      >
    </div>
  </div>
</template>

<script lang="ts">
import { computed, defineComponent, PropType } from "vue";
import { useStore } from "vuex";
import { Plus } from "@element-plus/icons-vue";

import { ProductSummry } from "@/types/ProductDetailType";

export default defineComponent({
  props: {
    summaryValue: {
      type: Object as PropType<ProductSummry>,
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
    const summary = computed(() => {
      const obj = { ...props.summaryValue };
      return {
        title: `${obj?.productType} ${obj?.price}`,
        floor: obj?.floor,
        managePrice: `관리비 월 ${obj?.managePrice}만`,
        sellerId: obj?.sellerId,
      };
    });

    const store = useStore();
    const myIndex = computed(() => store.getters["userStore/userIndex"]);
    const isMine = computed(
      () => myIndex.value === props.summaryValue?.sellerIndex
    );

    //on click event
    const onClickOpenChat = () => {
      let productInfo = {
        userIndex: props.summaryValue?.sellerIndex,
        userName: props.summaryValue?.sellerId,
      };

      store.commit("chatStore/GET_PRODUCT_INFO", productInfo);
      store.commit("chatStore/CHANGE_CHATROOM_STATUS", true);
      store.commit("chatStore/CHANGE_GET_CHAT_CONTENT", true);
    };

    return {
      Plus,
      summary,
      isMine,
      onClickOpenChat,
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

.width-full {
  width: 100%;
}
</style>
