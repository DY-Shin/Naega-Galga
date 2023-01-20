<template>
  <el-button text @click="centerDialogVisible = true"> 회원탈퇴 </el-button>

  <el-dialog v-model="centerDialogVisible" title="경고" width="20%" center>
    <span id="confirm"> 정말로 회원탈퇴 하시겠습니까? </span>
    <template #footer>
      <span class="dialog-footer">
        <el-button class="dialogbutton" @click="centerDialogVisible = false"
          >아니오
        </el-button>
        <el-button
          class="dialogbutton"
          type="primary"
          @click="userDelete"
          style="width: 70px"
          >예
        </el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script lang="ts">
import { defineComponent, ref } from "vue";
import { useStore } from "vuex";

export default defineComponent({
  name: "UserDeleteDialog",
  setup() {
    const store = useStore();
    const centerDialogVisible = ref(false);
    const userDelete = () => {
      store.dispatch("userDelete");
    };

    return { centerDialogVisible, userDelete };
  },
});
</script>

<style scoped>
.dialog-footer button:first-child {
  margin-right: 10px;
}
.dialogbutton {
  width: 70px;
}
#confirm {
  justify-content: center;
  display: flex;
}
</style>
