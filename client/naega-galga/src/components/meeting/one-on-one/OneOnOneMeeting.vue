<template>
  <div class="container">
    <div class="video-group">
      <video
        v-if="videoOn.seller"
        id="seller-video"
        class="border shadow"
        autoplay
        playinline
      ></video>
      <div v-else id="seller-video" class="border shadow">
        <div class="no-video-text-position">비디오가 연결되지 않았습니다.</div>
      </div>
      <div>
        <video
          v-if="videoOn.buyer"
          id="buyer-video"
          class="border"
          autoplay
          playinline
        ></video>
        <div v-else id="buyer-video" class="border">
          <div class="no-video-text-buyer">비디오가 연결되지 않았습니다.</div>
        </div>
      </div>
    </div>
    <div class="flex-column">
      <div id="map" class="border shadow"></div>
      <div id="chat-box" class="border shadow">
        <div id="message-box" ref="messageBoxRef">
          <div v-for="(message, index) in messageList" :key="index">
            {{ message.text }}
          </div>
        </div>
        <div id="message-input-box">
          <textarea
            v-model="messageInput"
            rows="3"
            cols="50"
            class="message-input"
            @keydown="onTextAreaKeyUp"
          ></textarea>
          <el-button type="primary" @click="onClickSendMessage">
            <el-icon class="send-icon"><Promotion /></el-icon>
          </el-button>
        </div>
      </div>
    </div>
  </div>
  <div class="control-buttons">
    <el-button v-model="canUseMic" round @click="canUseMic = !canUseMic">
      <el-icon v-if="canUseMic"><Microphone /></el-icon>
      <el-icon v-else><Mute /></el-icon>
      <span class="button-text" v-if="canUseMic">ON</span>
      <span class="button-text" v-else>OFF</span>
      <el-icon size="10"><ArrowUp /></el-icon>
    </el-button>
    <el-button v-model="canUseVideo" round @click="canUseVideo = !canUseVideo">
      <el-icon v-if="canUseVideo"><VideoCamera /></el-icon>
      <el-icon v-else><VideoPause /></el-icon>
      <span v-if="canUseVideo" class="button-text">ON</span>
      <span v-else class="button-text">OFF</span>
    </el-button>
    <el-button round type="danger" @click="onClickExit">
      <el-icon><Close /></el-icon>
      <span class="button-text">나가기</span>
    </el-button>
  </div>
</template>

<script lang="ts">
import { ref, reactive, Ref } from "vue";
import { useRouter } from "vue-router";

export default {
  setup() {
    //media control
    const canUseMic = ref(false);
    const canUseVideo = ref(false);

    const videoOn = reactive({ seller: false, buyer: false });

    //exit
    const router = useRouter();
    const onClickExit = () => {
      if (confirm("정말 나가시겠습니까?")) {
        router.back();
      }
    };

    interface Message {
      isMine: boolean;
      text: string;
    }

    const messageList: Ref<Array<Message>> = ref([]);
    const inputtedMessage = ref("");
    const messageBoxRef = ref();

    // eslint-disable-next-line no-unused-vars
    const sendMessage = (message: string) => {
      //메세지 전송
      messageList.value.push({
        isMine: true,
        text: inputtedMessage.value,
      });
      inputtedMessage.value = "";
      messageBoxRef.value.scrollTop = messageBoxRef.value.scrollHeight;
    };
    const onClickSendMessage = () => {
      sendMessage(inputtedMessage.value);
    };

    const onTextAreaKeyUp = event => {
      if (event.key === "Enter") {
        event.preventDefault();
        //shift + enter
        if (event.shiftKey) {
          inputtedMessage.value += "\n";
          return;
        }
        sendMessage(inputtedMessage.value);
        return;
      }
    };

    return {
      canUseMic,
      canUseVideo,
      videoOn,
      onClickExit,
      messageInput: inputtedMessage,
      onClickSendMessage,
      onTextAreaKeyUp,
      messageList,
      messageBoxRef,
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
.flex-column {
  display: flex;
  flex-direction: column;
  margin-left: 1.5rem;
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
  box-shadow: 5px 5px 5px #ebeef5;
}

#buyer-video {
  width: 10vw;
  height: 30vh;
  z-index: 10;
  position: absolute;
  bottom: -1px;
  right: 0;
  border-bottom: none;
}

#map {
  width: 28vw;
  height: 40vh;
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

#chat-box {
  flex: 1;
  margin-top: 1rem;
  width: 28vw;
  background-color: rgba(236, 245, 255, 0.5);
}

#message-box {
  height: 17.8em;
  overflow-y: auto;
}

#message-input-box {
  display: flex;
  flex-direction: row;
  flex: 1;
}

.message-input {
  border: none;
  background-color: transparent;
  width: 87%;
  outline: none;
}

.message-input :focus {
  outline: none;
}

.send-icon {
  transform: rotate(50deg);
}
</style>
