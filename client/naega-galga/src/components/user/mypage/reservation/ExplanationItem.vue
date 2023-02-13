<template>
  {{ explanationItem }}
</template>

<script lang="ts">
import { defineComponent } from "vue";

export default defineComponent({
  name: "ExplanationItem",
  props: {
    explanation: { type: Object },
  },

  setup(props) {
    const explanationItem = { ...props.explanation };
    return { explanationItem };
  },
});
</script>
