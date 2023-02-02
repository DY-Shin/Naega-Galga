<template>
  <div class="container">
    <div class="video-group">
      <ov-video
        id="seller-video"
        class="border shadow"
        autoplay
        playinline
        :stream-manager="isSeller ? publisher : subscriber"
      ></ov-video>
      <div>
        <ov-video
          id="buyer-video"
          class="border"
          autoplay
          playinline
          :stream-manager="isSeller ? subscriber : publisher"
        ></ov-video>
      </div>
    </div>
    <div class="flex-column">
      <div id="map" class="border shadow"></div>
      <chat-box
        @sendMessage="sendMessage"
        :message-list="messageList"
      ></chat-box>
    </div>
  </div>
  <div class="control-buttons">
    <el-button v-model="myMicMute" round @click="muteMic">
      <el-icon v-if="myMicMute"><Microphone /></el-icon>
      <el-icon v-else><Mute /></el-icon>
      <span class="button-text" v-if="myMicMute">ON</span>
      <span class="button-text" v-else>OFF</span>
      <el-icon size="10"><ArrowUp /></el-icon>
    </el-button>
    <el-button v-model="myVideoMute" round @click="muteVideo">
      <el-icon v-if="myVideoMute"><VideoCamera /></el-icon>
      <el-icon v-else><VideoPause /></el-icon>
      <span v-if="myVideoMute" class="button-text">ON</span>
      <span v-else class="button-text">OFF</span>
    </el-button>
    <el-button round type="danger" @click="onClickExit">
      <el-icon><Close /></el-icon>
      <span class="button-text">나가기</span>
    </el-button>
  </div>
</template>

<script lang="ts">
import { ref, reactive, onMounted, computed, watch } from "vue";
import { useRoute, useRouter } from "vue-router";
import { useStore } from "vuex";
import { getOneOnOneMeetingInfo } from "@/api/meetingApi";
import OvVideo from "./OvVideo.vue";
import { OpenVidu } from "openvidu-browser";
import ChatBox from "@/components/meeting/one-on-one/ChatBox.vue";
import { Message } from "@/types/MeetingChatType";

export default {
  components: {
    OvVideo,
    ChatBox,
  },
  setup() {
    //화상 미팅방 정보
    const store = useStore();
    const route = useRoute();
    const router = useRouter();

    let token: string;
    const index = reactive({
      my: -1,
      seller: -1,
      buyer: -1,
      meeting: -1,
    });
    let isSeller = ref(false);

    const getMeetingInfo = async (meetingIndex: number) => {
      try {
        const response = await getOneOnOneMeetingInfo(meetingIndex);
        const data = response.data;

        index.seller = data.sellerIndex;
        index.buyer = data.buyderIndex;
        token = data.token;

        if (index.seller === index.my) {
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

    const ov = ref();
    const session = ref();
    const publisher = ref();
    let subscriber = ref();

    const getSession = async () => {
      ov.value = new OpenVidu();
      session.value.value = ov.value.getSession();

      //새로운 stream을 받을때마다
      session.value.value.on("streamCreated", ({ stream }) => {
        subscriber = session.value.value.subscribe(stream);
      });
      //stream이 끊어졌을때마다
      session.value.value.on("streamDestroyed", () => {
        if (subscriber.value) {
          subscriber.value = null;
        }
      });
      session.value.on("exception", ({ exception }) => {
        console.warn(exception);
      });

      await session.value.connect(token);
      publisher.value = ov.value.initPublisher(myVideo, {
        audioSource: undefined,
        videoSource: undefined,
        publishAudio: true,
        publishVideo: true,
        insertMode: "APPEND",
        mirror: false,
      });
      session.value.publish(publisher);
    };

    onMounted(async () => {
      index.my = computed(() => store.getters["userStore/userIndex"]).value;
      index.meeting = computed(() => parseInt(route.params.id[0])).value;

      await getMeetingInfo(index.meeting);
      getSession();
    });

    //chat
    const messageList: Array<Message> = reactive([]);

    //새로운 문자열이 추가될때마다 시간 기준으로 정렬
    watch(
      () => messageList.length,
      () =>
        messageList.sort(
          (a: Message, b: Message) =>
            a.sendedTime.valueOf() - b.sendedTime.valueOf()
        )
    );
    const sendMessage = (message: Message) => {
      //chat signal 전송
      messageList.push(message);
    };

    //media control
    const myMicMute = ref(false);
    const myVideoMute = ref(false);

    const muteMic = () => {
      myMicMute.value = !myMicMute.value;
    };
    const muteVideo = () => {
      myVideoMute.value = !myVideoMute.value;
    };

    //exit
    const leaveSession = () => {
      if (session.value) {
        session.value.disconnect();
      }

      session.value = undefined;
      publisher.value = undefined;
      subscriber.value = null;
      ov.value = undefined;

      window.removeEventListener("beforeunload", leaveSession);
    };

    const onClickExit = () => {
      if (confirm("정말 나가시겠습니까?")) {
        leaveSession();
        router.back();
      }
    };

    //미디어 기기 가져오기
    // const openMediaDevices = async constraints =>
    //   await navigator.mediaDevices.getUserMedia(constraints);

    // onMounted(async () => {});

    return {
      isSeller,
      publisher,
      subscriber,
      messageList,
      sendMessage,
      myMicMute,
      myVideoMute,
      muteMic,
      muteVideo,
      onClickExit,
    };
  },
};
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

.no-video-text-position {
  position: relative;
  top: 44%;
  text-align: center;
}

.no-video-text-buyer {
  position: relative;
  top: 35%;
  text-align: center;
  word-break: keep-all;
  padding: 0 10px;
}
.border {
  border: solid 1px rgba(177, 179, 184, 0.6);
}

.shadow {
  box-shadow: var(--el-box-shadow-light);
}

#seller-video {
  width: 60vw;
  height: 80vh;
  z-index: 1;
  object-fit: cover;
  box-shadow: 5px 5px 5px #ebeef5;
  background-color: #fafafa;
}

#buyer-video {
  width: 20%;
  height: 15%;
  z-index: 10;
  position: absolute;
  bottom: -1px;
  right: 0;
  border-bottom: none;
  object-fit: cover;
  background-color: #fafafa;
}

#map {
  width: 28vw;
  height: 40vh;
}

.flex-column {
  display: flex;
  flex-direction: column;
  margin-left: 1.5rem;
}

.button-size {
  width: 100px;
}

.control-buttons {
  margin-top: 20px;
  margin-left: 33vw;
  margin-right: 33vw;
  display: flex;
  flex-direction: row;
  justify-content: space-around;
}

.button-text {
  margin-left: 10px !important;
  margin-right: 10px;
  font-size: var(--el-font-size-small);
  width: 30px;
}
</style>
