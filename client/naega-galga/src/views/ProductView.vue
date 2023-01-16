<template>
  <div class="margin">
    <router-view></router-view>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "@vue/runtime-core";

export default defineComponent({});
</script>

<style scoped>
.margin {
  margin-left: 15rem;
  margin-right: 15rem;
}
</style>
