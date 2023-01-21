<template>
  <div class="container">
    <div class="title-box">
      <span class="font-large font-semi-bold">
        {{ summary.title }}
      </span>
      <el-button
        type="info"
        size="small"
        text
        class="heart-btn"
        @click="toggleLike"
      >
        <img
          class="heart-icon"
          v-if="isWish.value"
          src="@/assets/image/icon-heart-filled.png"
        />
        <img class="heart-icon" v-else src="@/assets/image/icon-heart.png" />
      </el-button>
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
    <div class="margin-top">
      <span
        v-if="summary.explanationDate !== null"
        class="font-small margin-bottom explanation-text"
      >
        {{ summary.explanationDate }}
      </span>
      <el-button type="primary" circle :icon="Plus"></el-button>
    </div>
    <div class="margin-top width-full">
      <el-button type="primary" class="width-full">문의하기</el-button>
    </div>
  </div>
</template>

<script lang="ts">
import { computed, defineComponent, reactive } from "vue";
import { Plus } from "@element-plus/icons-vue";
import { addProductWish, deleteProductWish } from "@/api/productApi";
import ResponseStatus from "@/api/responseStatus";

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
        explanationDate: String,
        isWish: Boolean,
      },
    },
  },
  setup(props) {
    const isWish = reactive({ value: props.summaryValue?.isWish });
    const userIndex = 1;

    const toggleLike = async () => {
      isWish.value = !isWish.value;
      let response;
      //관심 등록
      if (!isWish) {
        response = await addProductWish(
          props.summaryValue?.productIndex,
          userIndex
        );
      } else {
        response = await deleteProductWish(
          props.summaryValue?.productIndex,
          userIndex
        );
      }
      if ((response.status = ResponseStatus.InternalServerError)) {
        alert("서버 오류로 요청을 처리할 수 없습니다.");
      }
    };

    const dateSplit = (divider: string, value: string): string[] =>
      value.split(divider);

    const summary = computed(() => {
      const obj = { ...props.summaryValue };
      let splitedDateString: string[] | null = null;
      let dateString: string | null = null;

      if (obj.explanationDate != null) {
        //[2023, 1, 23]
        splitedDateString = dateSplit(".", obj.explanationDate);
        dateString = `${splitedDateString[1]}월 ${splitedDateString[2]}일 온라인 설명회 예정`;
      }

      return {
        title: `${obj?.productType} ${obj?.price}`,
        floor: obj?.floor,
        managePrice: `관리비 월 ${obj?.managePrice}만`,
        sellerId: obj?.sellerId,
        explanationDate: dateString,
      };
    });

    return {
      summary,
      isWish,
      toggleLike,
      Plus,
    };
  },
});
</script>

<style scoped>
.container {
  border: 1px solid var(--el-color-info);
  display: flex;
  flex-direction: column;
  position: sticky;
  float: right;
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
