<template>
  {{ meetingItem }}
</template>

<script lang="ts">
import { defineComponent } from "vue";

export default defineComponent({
  name: "MeetingItem",
  props: {
    meeting: { type: Object },
  },

  setup(props) {
    const meetingItem = { ...props.meeting };
    return { meetingItem };
  },
});
</script>
