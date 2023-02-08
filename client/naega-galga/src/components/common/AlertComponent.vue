<template>
  <div>
    <el-alert
      v-if="getIsShow"
      show-icon
      :type="getAlertInfo.alertType"
      :title="getAlertInfo.title"
      :description="getAlertInfo.description"
      @close="setIsShow(false)"
    >
    </el-alert>
  </div>
</template>

<script lang="ts" setup>
import { computed } from "vue";
import { useStore } from "vuex";
import { ElAlert } from "element-plus";

const store = useStore();
const getIsShow = computed(() => store.getters["alertStore/getIsShow"]);
const getAlertInfo = computed(() => store.getters["alertStore/getAlertInfo"]);

const setIsShow = (value: boolean) =>
  store.dispatch("alertStore/SET_IS_SHOW", value);
</script>

<style scoped>
div {
  position: fixed;
  right: 0;
  width: 300px;
  max-width: 300px;
}
.el-alert {
  width: 400px;
}
</style>
