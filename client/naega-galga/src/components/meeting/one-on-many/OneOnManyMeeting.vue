<template>
  <div class="container">
    <div class="video-group">
      <ov-video class="seller-video" :streamManager="publisher"></ov-video>
    </div>

    <div class="flex-column">
      <chat-box
        @sendMessage="sendMessage"
        :message-list="messageList"
      ></chat-box>
    </div>
  </div>

  <div class="control-buttons">
    <el-button round type="danger" @click="onClickExit">
      <el-icon><Close /></el-icon>
      <span class="button-text">나가기</span>
    </el-button>
  </div>
</template>

<script lang="ts">
import {
  defineComponent,
  reactive,
  ref,
  onMounted,
  computed,
  watch,
} from "vue";
import { useRoute, useRouter } from "vue-router";
import { useStore } from "vuex";

import OvVideo from "@/components/meeting/one-on-one/OvVideo.vue";
import { OpenVidu, SignalOptions } from "openvidu-browser";
import ChatBox from "@/components/meeting/one-on-one/ChatBox.vue";
import { Message } from "@/types/MeetingChatType";

import { getOneOnManyMeetingInfo } from "@/api/meetingApi";
// import { Close } from "@element-plus/icons-vue";

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

    // const isSeller = ref(false);
    const getMeetingInfo = async (explanationIndex: number) => {
      try {
        const response = await getOneOnManyMeetingInfo(explanationIndex);
        const data = response.data;

        index.seller_index = data.sellerIndex;
        index.buyer_index = data.buyderIndex;
        token = data.token;

        if (index.seller_index === index.my_index) {
          // isSeller.value = true;
          myVideo = sellerVideo;
        }
      } catch (error) {
        alert("오류 발생");
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

      session.on(`signal:chat`, event => {
        const msg = JSON.parse(event.data).message;
        messageList.push({
          isMine: false,
          text: msg,
          sendedTime: new Date(),
        });
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

    const messageList: Array<Message> = reactive([]);

    watch(
      () => messageList.length,
      () =>
        messageList.sort(
          (a: Message, b: Message) =>
            a.sendedTime.valueOf() - b.sendedTime.valueOf()
        )
    );
    const sendMessage = (message: Message) => {
      messageList.push(message);
      const signalOptions: SignalOptions = {
        data: JSON.stringify({ message }),
        type: "chat",
        to: undefined,
      };
      session.signal(signalOptions);
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

    //media control
    const myMicMute = ref(false);
    const myVideoMute = ref(false);
    const isChatMode = ref(true);

    const muteMic = () => {
      myMicMute.value = !myMicMute.value;
      publisher.value.publishAudio(myMicMute.value);
    };
    const muteVideo = () => {
      myVideoMute.value = !myVideoMute.value;
      publisher.value.publishVideo(myMicMute.value);
    };
    const toggleChatMode = () => {
      isChatMode.value = !isChatMode.value;
    };

    const onClickExit = () => {
      if (confirm("정말 나가시겠습니까?")) {
        leaveSession();
        router.back();
      }
    };

    return {
      onClickExit,
      sendMessage,
      messageList,
      muteMic,
      muteVideo,
      toggleChatMode,
    };
  },
});
</script>

<style scoped>
.container {
  display: flex;
  flex-direction: row;
  height: 80vh;
}
.video-group {
  position: relative;
}
.seller-video {
  width: 60vw;
  height: 80vh;
  border: 1px solid;
}
.flex-column {
  display: flex;
  flex-direction: column;
  margin-left: 1.5rem;
}
.control-buttons {
  margin-top: 20px;
  margin-left: 33vw;
  margin-right: 33vw;
  display: flex;
  flex-direction: row;
  justify-content: space-around;
}
@media only screen and (max-width: 500px) {
  .container {
    display: flex;
  }
}
</style>
