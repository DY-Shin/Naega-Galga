<template>
  <el-col id="card_padding" :span="8">
    <el-card @click="product" :body-style="{ padding: '0px' }">
      <div>
        <img
          :src="imageDisplay"
          class="image"
          style="width: 100%; height: 200px"
          fit="cover"
        />
      </div>

      <div id="card_explain">
        <p style="font-size: large">
          {{ registItem.productType }} {{ registItem.productPrice }}
        </p>
        <p style="font-size: small">
          관리비 : {{ registItem.productManageCost }}만원
        </p>
        <p>평수 : {{ registItem.productSize }}m²</p>
        <p>{{ registItem.productFloor }}</p>
        <p></p>
      </div>
    </el-card>
  </el-col>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import { useRouter } from "vue-router";

export default defineComponent({
  name: "RegisterListItem",
  props: {
    regist: { type: Object },
  },
  setup(props) {
    const registItem = { ...props.regist };
    const router = useRouter();

    const product = () => {
      router.push({ path: `../product/${registItem.productIndex}` });
    };
    const imagePathSplit = registItem.productPhoto.split(",");
    const imageDisplay = `${process.env.VUE_APP_API_BASE_URL}api/image/display?path=${imagePathSplit[0]}`;

    console.log(imagePathSplit[0]);

    return { registItem, product, imageDisplay };
  },
});
</script>

<style scoped>
#card_padding {
  margin-bottom: 20px;
}

#card_explain {
  padding-top: 10px;
  padding-bottom: 10px;
  padding-left: 30px;
  padding-right: 30px;
}
</style>
