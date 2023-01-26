<template>
  <div class="margin">
    <router-view></router-view>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";

export default defineComponent({});
</script>

<style scoped>
.margin {
  margin: 3rem;
  margin-bottom: 2rem;
}
</style>
