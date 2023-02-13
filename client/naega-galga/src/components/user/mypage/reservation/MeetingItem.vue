<template>
  <div @click="product">미팅하러가기</div>
  <div v-if="meetingItem.guest == null">이 사람은 구매자입니다.</div>
  <div v-if="meetingItem.guest != null">이 사람은 판매자입니다.</div>

  <div>
    <p>{{ meetingItem }}</p>
    <p>{{ meetingItem.type }}</p>
    <p>{{ meetingItem.role }}</p>
    <p>{{ meetingItem.reserveAt }}</p>
    <p>{{ meetingItem.meetingUrl }}</p>
    <p>{{ meetingItem.guest }}</p>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import { useRouter } from "vue-router";

export default defineComponent({
  name: "MeetingItem",
  props: {
    meeting: { type: Object },
  },

  setup(props) {
    const meetingItem = { ...props.meeting };
    const router = useRouter();

    const product = () => {
      router.push(`${meetingItem.meetingUrl}`);
    };
    return { meetingItem, product };
  },
});
</script>
