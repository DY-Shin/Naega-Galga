<template>
  <table>
    <tbody>
      <tr>
        <td width="150px" class="text-bold text-large">
          {{ productInfo?.productType }}
        </td>
        <td class="text-large">{{ productInfo?.price }}</td>
      </tr>
      <tr>
        <td width="150px" class="text-bold text-large">관리비</td>
        <td class="text-large">{{ productInfo?.managePrice }}</td>
      </tr>
      <tr>
        <td width="150px" class="text-bold text-large">층수</td>
        <td class="text-large">{{ productInfo?.floor }}</td>
      </tr>
      <tr>
        <td width="150px" class="text-bold text-large">면적</td>
        <td class="text-large">{{ productInfo.roomSize }}m<sup>2</sup></td>
      </tr>
      <tr>
        <td width="150px" class="text-bold text-large">건물 이름</td>
        <td class="text-large">{{ productInfo.productName }}</td>
      </tr>
      <tr>
        <td width="150px" class="text-bold text-large">상세주소</td>
        <td class="text-large">{{ productInfo.address }}</td>
      </tr>
      <tr>
        <td width="150px" class="text-bold text-large">방향</td>
        <td class="text-large">{{ productInfo.roomDirection }}</td>
      </tr>
      <tr>
        <td width="150px" class="text-bold text-large">반려동물</td>
        <td class="text-large">{{ productInfo.animal }}</td>
      </tr>
      <tr>
        <td width="150px" class="text-bold text-large">주차</td>
        <td class="text-large">{{ productInfo.parking }}</td>
      </tr>
      <tr>
        <td width="150px" class="text-bold text-large">판매자</td>
        <td class="text-large">{{ productInfo.seller }}</td>
      </tr>
    </tbody>
  </table>
</template>

<script lang="ts">
import { defineComponent } from "@vue/runtime-core";

export default defineComponent({
  props: {
    info: {
      type: Object,
      productType: String,
      productName: String,
      price: String,
      managePrice: Number,
      floor: Number,
      roomSize: Number,
      address: String,
      roomDirection: String,
      parking: [Number, String],
      animal: Boolean,
      seller: String,
    },
  },
  setup(props) {
    const productInfo = { ...props.info };
    const booleanToString = (value: boolean): string =>
      value ? "가능" : "불가능";

    productInfo.parking =
      productInfo.parking === 0 ? "불가능" : productInfo.parking;
    productInfo.parking = booleanToString(productInfo.parking);
    productInfo.animal = booleanToString(productInfo.animal);

    return { productInfo };
  },
});
</script>

<style scoped>
.text-bold {
  font-weight: bold;
}

.text-large {
  font-size: var(--el-font-size-large);
}

td {
  border-top: 1px solid #e9e9eb;
  padding-top: 20px;
  padding-bottom: 20px;
}
</style>
