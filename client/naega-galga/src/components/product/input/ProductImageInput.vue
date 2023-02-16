<template>
  <el-upload
    list-type="picture-card"
    :auto-upload="false"
    multiple
    v-model="files"
    :on-change="handleAdd"
    :on-remove="handleRemove"
    :on-preview="handlePictureCardPreview"
  >
    <el-icon><Plus /></el-icon>
  </el-upload>
  <el-dialog v-model="dialogVisible">
    <img w-full :src="dialogImageUrl" alt="Preview Image" />
  </el-dialog>
</template>
<script lang="ts" setup>
import { defineEmits, defineProps, ref } from "vue";

import type { UploadFile } from "element-plus";
import { Plus } from "@element-plus/icons-vue";

const props = defineProps({
  fileList: Array,
});
const files = ref(props.fileList);

const emit = defineEmits(["fileAdd", "fileDelete"]);
const handleRemove = (file: UploadFile) => {
  emit("fileDelete", file.name);
};
const handleAdd = (file: UploadFile): void => {
  emit("fileAdd", file);
};

const dialogImageUrl = ref("");
const dialogVisible = ref(false);

const handlePictureCardPreview = (file: UploadFile) => {
  // eslint-disable-next-line @typescript-eslint/no-non-null-assertion
  dialogImageUrl.value = file.url!;
  dialogVisible.value = true;
};
</script>
