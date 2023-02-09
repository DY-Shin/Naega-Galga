<template>
  <ov-video></ov-video>
  <chat-box></chat-box>

  <el-button round type="danger" @click="onClickExit">
    <el-icon><Close /></el-icon>
    <span class="button-text">나가기</span>
  </el-button>
</template>

<script lang="ts">
import { defineComponent, reactive, ref, onMounted, computed } from "vue";
import { useRoute, useRouter } from "vue-router";
import { useStore } from "vuex";

import OvVideo from "./OvVideo.vue";
import { OpenVidu } from "openvidu-browser";
import ChatBox from "@/components/meeting/one-on-one/ChatBox.vue";
import { getOneOnManyMeetingInfo } from "@/api/meetingApi";

export default defineComponent({
  name: "JoinView",
  components: {
    OvVideo,
    ChatBox,
  },
  setup() {
    const store = useStore();
    const route = useRoute();
    const router = useRouter();

    let token: string;
    const index = reactive({
      my_index: -1,
      seller_index: -1,
      buyer_index: -1,
      meeting_index: -1,
    });

    const isSeller = ref(false);
    const getMeetingInfo = async (explanationIndex: number) => {
      try {
        const response = await getOneOnManyMeetingInfo(explanationIndex);
        const data = response.data;

        index.seller_index = data.sellerIndex;
        index.buyer_index = data.buyderIndex;
        token = data.token;

        if (index.seller_index === index.my_index) {
          isSeller.value = true;
          myVideo = sellerVideo;
        }
      } catch (error) {
        alert("서버 오류로 실행할 수 없습니다\n잠시 후 다시 시도해 주세요.");
      }
    };
    const sellerVideo = document.querySelector("video#sellerVideo");
    const buyerVideo = document.querySelector("video#buyderVideo");
    let myVideo = buyerVideo;

    let ov;
    let session;
    // let mainStreamManager;
    const publisher = ref();
    let subscribers: string[] = [];

    const setSession = async () => {
      ov = new OpenVidu();
      session = ov.initSession();

      session.on("streamCreated", ({ stream }) => {
        let subscriber = session.subscribe(stream, undefined);
        subscribers.push(subscriber);
      });

      session.on("streamDestroyed", ({ stream }) => {
        const index = subscribers.indexOf(stream.streamManager, 0);
        if (index >= 0) {
          subscribers.splice(index, 1);
        }
      });

      session.on("exception", ({ exception }) => {
        console.warn(exception);
      });

      await session.connect(token);
      publisher.value = ov.initPublisher(myVideo, {
        audioSource: undefined,
        videoSource: undefined,
        publishAudio: true,
        publishVideo: true,
        insertMode: "APPEND",
        mirror: false,
      });
      session.publish(publisher);
    };

    const leaveSession = () => {
      if (session) {
        session.disconnect();
      }

      session = undefined;
      publisher.value = undefined;
      subscribers = [];
      ov.value = undefined;

      window.removeEventListener("beforeunload", leaveSession);
    };

    onMounted(async () => {
      index.my_index = computed(
        () => store.getters["userStore/userIndex"]
      ).value;
      index.meeting_index = computed(() => parseInt(route.params.id[0])).value;

      await getMeetingInfo(index.meeting_index);
      setSession();
    });

    const onClickExit = () => {
      if (confirm("정말 나가시겠습니까?")) {
        leaveSession();
        router.back();
      }
    };

    return { onClickExit };
  },
});
</script>

<style>
.container {
  display: flex;
}

@media only screen and (max-width: 500px) {
  .container {
    display: flex;
  }
}
</style>
