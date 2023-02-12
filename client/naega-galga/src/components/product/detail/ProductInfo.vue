<template>
  <table>
    <tbody>
      <tr>
        <td width="200px" class="text-bold text-large">
          {{ productValue?.productType }}
        </td>
        <td class="text-large">{{ productValue?.productPrice }}</td>
      </tr>
      <tr>
        <td width="200px" class="text-bold text-large">관리비</td>
        <td class="text-large">{{ manageCostStr }}</td>
      </tr>
      <tr>
        <td width="200px" class="text-bold text-large">면적</td>
        <td class="text-large">{{ productValue?.productSize }}m<sup>2</sup></td>
      </tr>
      <tr>
        <td width="200px" class="text-bold text-large">방 방향</td>
        <td class="text-large">
          {{ productValue?.productDirection }}
        </td>
      </tr>
      <tr>
        <td width="200px" class="text-bold text-large">층수</td>
        <td class="text-large">{{ productValue?.productFloor }}</td>
      </tr>
      <tr>
        <td width="200px" class="text-bold text-large">방 종류</td>
        <td class="text-large">{{ productValue?.productRooms }}</td>
      </tr>
      <tr>
        <td width="200px" class="text-bold text-large">주차</td>
        <td class="text-large">{{ parkingStr }}</td>
      </tr>
      <tr>
        <td width="200px" class="text-bold text-large">반려동물</td>
        <td class="text-large">{{ productValue?.productAnimal }}</td>
      </tr>
      <tr>
        <td width="200px" class="text-bold text-large">엘리베이터</td>
        <td class="text-large">{{ elevatorStr }}</td>
      </tr>
      <tr>
        <td width="200px" class="text-bold text-large">판매자</td>
        <td class="text-large">{{ sellerValue?.userId }}</td>
      </tr>
      <tr>
        <td width="200px" class="text-bold text-large">주소</td>
        <td class="text-large">
          <tr>
            {{
              buildingValue?.buildingRoadAddress
            }}
          </tr>
          <tr v-if="buildingValue?.buildingJibunAddress">
            {{
              buildingValue?.buildingJibunAddress
            }}
          </tr>
          <tr>
            {{
              `${buildingValue?.buildingName} ${productValue?.productDetail}`
            }}
          </tr>
        </td>
      </tr>
    </tbody>
  </table>
</template>

<script lang="ts">
import { computed, PropType, toRefs } from "vue";
import {
  Seller,
  Building,
  Product,
  ProductInfo,
} from "@/types/ProductDetailType";

export default {
  props: {
    type: Object as PropType<ProductInfo>,
    product: {
      type: Object as PropType<Product>,
    },
    building: {
      type: Object as PropType<Building>,
    },
    seller: {
      type: Object as PropType<Seller>,
    },
  },
  setup(props) {
    const { product, building, seller } = toRefs(props);
    const manageCostStr = computed(() => {
      const str = `월 ${props.product?.productManageCost}만`;
      return str;
    });

    const parkingStr =
      props.building?.buildingPark === 0
        ? "불가능"
        : props.building?.buildingPark;

    const elevatorStr: string =
      props.building?.buildingElevator === 0 ? "없음" : "있음";

    return {
      productValue: product,
      buildingValue: building,
      sellerValue: seller,
      manageCostStr,
      parkingStr,
      elevatorStr,
    };
  },
};
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
