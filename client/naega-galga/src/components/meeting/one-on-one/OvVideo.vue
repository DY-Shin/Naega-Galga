<template>
  <video autoplay ref="videoElRef"></video>
</template>

<script lang="ts">
import { ref } from "vue";

export default {
  props: {
    streamManager: Object,
  },

  setup(props) {
    const videoElRef = ref(null);

    props.streamManager?.addVideoElement(videoElRef);
  },
};
</script>
