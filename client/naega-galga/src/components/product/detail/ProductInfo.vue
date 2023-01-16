<template>
  <el-row>
    <el-col :span="4">{{ productInfo?.productType }}</el-col>
    <el-col :span="20">{{ productInfo?.price }}</el-col>
  </el-row>
  <el-row>
    <el-col :span="4">관리비</el-col>
    <el-col :span="20"> {{ productInfo?.managePrice }}</el-col>
  </el-row>
  <el-row>
    <el-col :span="4">층수</el-col>
    <el-col :span="20">
      {{ productInfo?.floor }}
    </el-col>
  </el-row>
  <el-row>
    <el-col :span="4">면적</el-col>
    <el-col :span="20"> {{ productInfo.roomSize }}m<sup>2</sup> </el-col>
  </el-row>
  <el-row>
    <el-col :span="4">건물 이름</el-col>
    <el-col :span="20"> {{ productInfo.productName }}</el-col>
  </el-row>
  <el-row>
    <el-col :span="4">상세주소</el-col>
    <el-col :span="20">
      {{ productInfo.address }}
    </el-col>
  </el-row>
  <el-row>
    <el-col :span="4">방향</el-col>
    <el-col :span="20">
      {{ productInfo.roomDirection }}
    </el-col>
  </el-row>
  <el-row>
    <el-col :span="4">반려동물</el-col>
    <el-col :span="20">
      {{ productInfo.animal }}
    </el-col>
  </el-row>
  <el-row>
    <el-col :span="4">주차</el-col>
    <el-col :span="20">{{ productInfo.parking }}</el-col>
  </el-row>
  <el-row>
    <el-col :span="4">판매자</el-col>
    <el-col :span="20">{{ productInfo.seller }}</el-col>
  </el-row>
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
.el-row {
  font-size: var(--el-font-size-large);
  margin-top: 20px;
}
</style>
