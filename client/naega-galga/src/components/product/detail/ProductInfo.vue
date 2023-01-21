<template>
  <table>
    <tbody>
      <tr>
        <td width="200px" class="text-bold text-large">
          {{ productInfo?.productType }}
        </td>
        <td class="text-large">{{ productInfo?.price }}</td>
      </tr>
      <tr>
        <td width="200px" class="text-bold text-large">관리비</td>
        <td class="text-large">{{ productInfo?.managePrice }}</td>
      </tr>
      <tr>
        <td width="200px" class="text-bold text-large">면적</td>
        <td class="text-large">{{ productInfo.roomSize }}m<sup>2</sup></td>
      </tr>
      <tr>
        <td width="200px" class="text-bold text-large">방 방향</td>
        <td class="text-large">{{ productInfo.roomDirection }}향</td>
      </tr>
      <tr>
        <td width="200px" class="text-bold text-large">층수</td>
        <td class="text-large">{{ productInfo?.floor }}</td>
      </tr>
      <tr>
        <td width="200px" class="text-bold text-large">방 종류</td>
        <td class="text-large">{{ productInfo?.roomType }}</td>
      </tr>
      <tr>
        <td width="200px" class="text-bold text-large">주차</td>
        <td class="text-large">{{ productInfo.parking }}</td>
      </tr>
      <tr>
        <td width="200px" class="text-bold text-large">반려동물</td>
        <td class="text-large">{{ productInfo.animal }}</td>
      </tr>
      <tr>
        <td width="200px" class="text-bold text-large">엘리베이터</td>
        <td class="text-large">{{ productInfo.elevator }}</td>
      </tr>
      <tr>
        <td width="200px" class="text-bold text-large">판매자</td>
        <td class="text-large">{{ productInfo.sellerId }}</td>
      </tr>
      <tr>
        <td width="200px" class="text-bold text-large">주소</td>
        <td class="text-large">
          <tr>
            {{
              productInfo.roadAddress
            }}
          </tr>
          <tr>
            {{
              productInfo.jibunAddress
            }}
          </tr>
          <tr>
            {{
              `${productInfo.buildingName} ${productInfo.roomHo}`
            }}
          </tr>
        </td>
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
      value: {
        productType: String,
        price: String,
        managePrice: Number,
        roomSize: Number,
        roomDirection: String,
        floor: Number,
        roomType: String,
        parking: [Number, String],
        animal: Boolean,
        elevator: Boolean,
        roadAddress: String,
        jibunAddress: String,
        buildingName: String,
        roomHo: String,
        sellerId: String,
      },
    },
  },
  setup(props) {
    const productInfo = { ...props.info };
    productInfo.managePrice = `월 ${productInfo.managePrice}만`;
    const booleanToString = (value: boolean): string =>
      value ? "가능" : "불가능";

    productInfo.parking =
      productInfo.parking === 0 ? "불가능" : productInfo.parking;
    productInfo.parking = booleanToString(productInfo.parking);
    productInfo.animal = booleanToString(productInfo.animal);
    productInfo.elevator = booleanToString(productInfo.elevator);

    return { productInfo };
  },
});
</script>

<style scoped>
.text-bold {
  font-weight: 600;
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
