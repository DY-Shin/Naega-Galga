<template>
  <div class="container">
    <div v-if="session" class="video-group">
      <user-video
        id="seller-video"
        :stream-manager="!isSeller ? publisher : subscriber"
        @click="
          updateMainVideoStreamManager(!isSeller ? publisher : subscriber)
        "
      />
      <user-video
        id="buyer-video"
        :stream-manager="isSeller ? publisher : subscriber"
        @click="updateMainVideoStreamManager(isSeller ? publisher : subscriber)"
      />
    </div>
    <div class="float-right flex-column">
      <div v-if="!isMobileScreen" class="map border shadow">
        <kakao-map
          :centerLatLng="centerLatLng"
          :trackMyPosition="isSeller"
          @changeSellerPosition="changeSellerPosition"
        ></kakao-map>
      </div>
      <chat-box
        v-if="!isMobileScreen"
        @sendMessage="sendMessage"
        :message-list="messageList"
      ></chat-box>
      <div v-else>
        <div v-if="!isChatMode" class="map border shadow">
          <kakao-map
            :centerLatLng="centerLatLng"
            :trackMyPosition="isSeller"
          ></kakao-map>
        </div>
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
import { ref, reactive, computed, watch } from "vue";
import { useRouter } from "vue-router";
import { useStore } from "vuex";
import { isMobileScreen } from "@/use/useMediaQuery";
import {
  getOneOnOneMeetingInfo,
  leaveOutOneOnOneMeetingInfo,
} from "@/api/meetingApi";

import ChatBox from "@/components/meeting/one-on-one/ChatBox.vue";
import KakaoMap from "@/components/meeting/one-on-one/KakaoMap.vue";
import UserVideo from "@/components/meeting/one-on-one/UserVideo.vue";

import { MeetingMessage } from "@/types/MeetingChatType";
import { MapCenterLatLng } from "@/types/MapTypes";

import { OpenVidu, SignalOptions } from "openvidu-browser";

import {
  Microphone,
  VideoCamera,
  VideoPause,
  ChatRound,
  LocationFilled,
  Close,
  Mute,
} from "@element-plus/icons-vue";
import ResponseStatus from "@/api/responseStatus";

export default {
  components: {
    ChatBox,
    Microphone,
    Mute,
    VideoCamera,
    VideoPause,
    ChatRound,
    LocationFilled,
    Close,
    KakaoMap,
    UserVideo,
  },
  setup() {
    //--------------------------화상 미팅방 정보
    const store = useStore();
    const router = useRouter();

    let token: string;
    const index = reactive({
      my: -1,
      seller: -1,
      buyer: -1,
      meeting: -1,
    });
    let isSeller = ref(false);
    index.my = computed(() => store.getters["userStore/userIndex"]).value;
    const str = window.location.href;
    const strList = str.split("/");
    index.meeting = parseInt(strList[strList.length - 1]);

    const getMeetingInfo = async (meetingIndex: number) => {
      const response = await getOneOnOneMeetingInfo(meetingIndex);
      const data = response.data;

      index.seller = data.sellerIndex;
      index.buyer = data.buyderIndex;
      token = data.token;

      if (index.seller === index.my) {
        isSeller.value = true;
      }

      if (response.status === ResponseStatus.Ok) {
        joinSession();
      }
    };

    getMeetingInfo(index.meeting);

    //--------------------------open-vidu things
    const ov = ref();
    const session = ref();
    const mainStreamManager = ref();
    const publisher = ref();
    const subscriber = ref();

    function joinSession() {
      ov.value = new OpenVidu();

      session.value = ov.value.initSession();

      session.value.on("signal:map", (event: any) => {
        const msg = JSON.parse(event.data).message;
        const data = msg.data;
        centerLatLng.x = data.x;
        centerLatLng.y = data.y;
      });

      session.value.on("streamCreated", ({ stream }) => {
        const subscriberObj = session.value.subscribe(stream);
        subscriber.value = subscriberObj;
        changeSellerPosition(centerLatLng);
      });

      session.value.on("streamDestroyed", () => {
        subscriber.value = null;
      });

      session.value.on("exception", ({ exception }) => {
        console.warn(exception);
      });

      session.value.on(`signal:chat`, (event: any) => {
        const msg = JSON.parse(event.data).message;
        if (msg.index !== index.my) {
          messageList.push({
            index: msg.index,
            text: msg.text,
            sendedTime: msg.sendedTime,
          });
        }
      });

      session.value.connect(token, { clientData: index.my }).then(() => {
        console.log(token);
        let publisherObj = ov.value.initPublisher(undefined, {
          audioSource: undefined,
          videoSource: undefined,
          publishAudio: true,
          publishVideo: true,
          resolution: "640x480",
          frameRate: 30,
          insertMode: "APPEND",
          mirror: false,
        });

        mainStreamManager.value = publisherObj;
        publisher.value = publisherObj;

        // --- 6) Publish your stream ---

        session.value.publish(publisher.value);

        window.addEventListener("beforeunload", leaveSession);
      });
    }

    //map
    const centerLatLng = reactive({ x: 33.450701, y: 126.570667 });
    //emit
    function changeSellerPosition(coords: MapCenterLatLng) {
      centerLatLng.x = coords.x;
      centerLatLng.y = coords.y;

      //판매자면 자신의 위치 정보를 signal로 보냄
      if (isSeller.value) {
        const signalOptions: SignalOptions = {
          data: JSON.stringify({ coords }),
          type: "map",
          to: undefined,
        };
        session.value.signal(signalOptions);
      }
    }

    //--------------------------chat
    const messageList: Array<MeetingMessage> = reactive([]);

    //새로운 문자열이 추가될때마다 시간 기준으로 정렬
    watch(
      () => messageList.length,
      () =>
        messageList.sort(
          (a: MeetingMessage, b: MeetingMessage) =>
            a.sendedTime.valueOf() - b.sendedTime.valueOf()
        )
    );
    const sendMessage = (message: MeetingMessage) => {
      messageList.push(message);
      const signalOptions: SignalOptions = {
        data: JSON.stringify({ message }),
        type: "chat",
        to: undefined,
      };
      session.value.signal(signalOptions);
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
      publisher.value.publishVideo(myVideoMute.value);
    };
    const toggleChatMode = () => {
      isChatMode.value = !isChatMode.value;
    };

    //exit
    const leaveSession = () => {
      if (session.value !== undefined) {
        session.value.disconnect();
      }

      session.value = undefined;
      publisher.value = undefined;
      subscriber.value = null;
      ov.value = undefined;
      mainStreamManager.value = undefined;

      window.removeEventListener("beforeunload", leaveSession);
    };

    const onClickExit = async () => {
      if (confirm("정말 나가시겠습니까?")) {
        try {
          const response = await leaveOutOneOnOneMeetingInfo(
            index.meeting,
            index.my,
            token
          );

          if (response.status === ResponseStatus.Ok) {
            leaveSession();
          }
        } finally {
          router.replace("/user/reservation");
        }
      }
    };

    function updateMainVideoStreamManager(stream) {
      if (mainStreamManager.value === stream) {
        return;
      }
      mainStreamManager.value = stream;
    }

    return {
      updateMainVideoStreamManager,
      isMobileScreen,
      isSeller,
      publisher,
      subscriber,
      session,
      messageList,
      sendMessage,
      //지도
      centerLatLng,
      changeSellerPosition,
      //미디어 컨트롤
      myMicMute,
      myVideoMute,
      isChatMode,
      muteMic,
      muteVideo,
      //모바일
      toggleChatMode,
      //퇴장
      onClickExit,
    };
  },
};
</script>

<style scoped>
.float-right {
  float: right;
}

.container {
  display: flex;
  flex-direction: row;
  height: 80vh;
}

.video-group {
  display: flex;
  flex-direction: row;
  align-items: center;
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
  width: 33vw;
  object-fit: cover;
  margin-right: 10px;
}

#buyer-video {
  width: 33vw;
  object-fit: cover;
}

.map {
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

  .map {
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
