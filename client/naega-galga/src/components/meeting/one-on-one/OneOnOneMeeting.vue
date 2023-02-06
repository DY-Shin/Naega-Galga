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
      <div v-if="!isMobileScreen" id="map" class="border shadow"></div>
      <chat-box
        v-if="!isMobileScreen"
        @sendMessage="sendMessage"
        :message-list="messageList"
      ></chat-box>
      <div v-else>
        <div v-if="!isChatMode" id="map" class="border shadow"></div>
        <chat-box
          v-else
          @sendMessage="sendMessage"
          :message-list="messageList"
        ></chat-box>
      </div>
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
    <el-button v-if="isMobileScreen" round @click="toggleChatMode">
      <span v-if="isChatMode">
        <el-icon><ChatRound /></el-icon>
        <span class="button-text">채팅</span>
      </span>
      <span v-else>
        <el-icon><LocationFilled /></el-icon>
        <span class="button-text">지도</span>
      </span>
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
import { OpenVidu, SignalOptions } from "openvidu-browser";
import ChatBox from "@/components/meeting/one-on-one/ChatBox.vue";
import { Message } from "@/types/MeetingChatType";
import { isMobileScreen } from "@/use/useMediaQuery";

export default {
  components: {
    OvVideo,
    ChatBox,
  },
  setup() {
    //--------------------------화상 미팅방 정보
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

    //--------------------------open-vidu things
    let ov;
    let session;
    const publisher = ref();
    const subscriber = ref();

    const setSession = async () => {
      ov = new OpenVidu();
      session = ov.initSession();

      //새로운 stream을 받을때마다
      session.on("streamCreated", ({ stream }) => {
        subscriber.value = session.subscribe(stream);
      });
      //stream이 끊어졌을때마다
      session.on("streamDestroyed", () => {
        if (subscriber.value) {
          subscriber.value = null;
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

    onMounted(async () => {
      index.my = computed(() => store.getters["userStore/userIndex"]).value;
      index.meeting = computed(() => parseInt(route.params.id[0])).value;

      await getMeetingInfo(index.meeting);
      setSession();
    });

    //--------------------------chat
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
      messageList.push(message);
      const signalOptions: SignalOptions = {
        data: JSON.stringify({ message }),
        type: "chat",
        to: undefined,
      };
      session.signal(signalOptions);
    };

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

    //exit
    const leaveSession = () => {
      if (session) {
        session.disconnect();
      }

      session = undefined;
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
      isMobileScreen,
      isSeller,
      publisher,
      subscriber,
      messageList,
      sendMessage,
      myMicMute,
      myVideoMute,
      isChatMode,
      muteMic,
      muteVideo,
      toggleChatMode,
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
  bottom: 0;
  right: 1px;
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

@media only screen and (max-width: 500px) {
  .container {
    display: flex;
    flex-direction: column;
    height: 80vh;
  }

  .video-group {
    box-sizing: border-box;
    top: 0;
    width: 90vw;
    height: 40vh;
    align-self: center;
  }

  #seller-video {
    width: inherit;
    height: inherit;
    overflow-x: hidden;
  }

  #buyer-video {
    width: 30%;
    height: 25%;
    z-index: 10;
    position: absolute;
    right: -2px;
    border-bottom: none;
    object-fit: cover;
    background-color: #fafafa;
  }

  .flex-column {
    flex-direction: column-reverse;
    margin-left: unset;
    margin-top: 20px;
    width: 90vw;
    align-self: center;
  }

  #map {
    width: 90vw;
    height: 30vh;
    margin-bottom: 5vh;
  }

  .control-buttons {
    width: 100%;
    background-color: #fafafa;
    margin-top: unset;
    margin-left: unset;
    margin-right: unset;
    display: flex;
    flex-direction: row;
    justify-content: space-around;
    position: fixed;
    margin-bottom: 10px;
    left: 0;
  }

  .button-text {
    margin-left: 5px !important;
    margin-right: 10px;
    font-size: (--el-font-size-x-small);
    width: 20px;
  }
}
</style>
