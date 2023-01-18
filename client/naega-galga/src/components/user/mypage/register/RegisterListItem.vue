<template>
  <el-card :body-style="{ padding: '0px' }">
    <img
      src="https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png"
      class="image"
    />
    <div id="card_explain">
      <p>{{ RegistItem.rent }}</p>
    </div>
  </el-card>
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
    return { RegistItem };
  },
});
</script>
