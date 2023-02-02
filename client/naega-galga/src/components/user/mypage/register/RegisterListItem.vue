<template>
  <el-col id="card_padding" :span="8">
    <el-card :body-style="{ padding: '0px' }">
      <img
        src="https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png"
        class="image"
      />
      <div id="card_explain">
        <p>{{ RegistItem.productFloor }}</p>
        <p>{{ RegistItem.productType }}</p>
        <p>{{ RegistItem.productPrice }}</p>
        <p>{{ RegistItem.productManageCost }}</p>
        <p>{{ RegistItem.productSize }}</p>
        <p>{{ RegistItem.productPhoto }}</p>
      </div>
    </el-card>
  </el-col>
</template>

<script lang="ts">
import { defineComponent } from "vue";

export default defineComponent({
  name: "RegisterListItem",
  props: {
    regist: { type: Object },
  },
  setup(props) {
    const RegistItem = { ...props.regist };
    console.log(RegistItem);
    return { RegistItem };
  },
});
</script>

<style>
#card_padding {
  padding-bottom: 20px;
}

#card_explain {
  padding-left: 20px;
  padding-right: 20px;
  padding-bottom: 20px;
}
</style>
