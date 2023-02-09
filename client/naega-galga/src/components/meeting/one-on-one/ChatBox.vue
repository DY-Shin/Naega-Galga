<template>
  <div id="chat-box" class="border shadow">
    <div id="message-box-container" ref="messageBoxRef">
      <div v-for="(message, index) in messageListRef" :key="index">
        <div v-if="message.isMine" class="message-box-holder">
          <div class="message-box">
            {{ message.text }}
          </div>
        </div>
        <div v-else class="message-box-holder">
          <p class="message-box message-partner">
            {{ message.text }}
          </p>
        </div>
      </div>
    </div>
    <div id="message-input-box">
      <textarea
        v-model="inputtedMessage"
        autofocus
        rows="3"
        cols="50"
        class="message-input"
        @keydown="onTextAreaKeyUp"
      ></textarea>
      <el-button type="primary" @click="onClickSendMessage" class="send-button">
        <el-icon class="send-icon"><Promotion /></el-icon>
      </el-button>
    </div>
  </div>
</template>

<script lang="ts">
import { toRef, ref, PropType } from "vue";
import { Message } from "@/types/MeetingChatType";

import { ElButton, ElIcon } from "element-plus";
import { Promotion } from "@element-plus/icons-vue";

export default {
  props: {
    messageList: {
      type: Array as PropType<Array<Message>>,
    },
  },
  components: { ElButton, ElIcon, Promotion },
  setup(props, context) {
    const messageListRef = toRef(props, "messageList");
    const inputtedMessage = ref("");
    // const messageBoxRef = ref();
    const { emit } = context;

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

    const sendMessage = (message: string) => {
      //메세지 전송
      emit("sendMessage", {
        isMine: true,
        text: message,
        sendedTime: new Date(),
      });
      inputtedMessage.value = "";
      //TODO : 안됨 고칠것
      // messageBoxRef.value.scrollTop = messageBoxRef.value.scrollHeight;
    };

    const onClickSendMessage = () => {
      sendMessage(inputtedMessage.value);
    };

    return {
      messageListRef,
      inputtedMessage,
      onTextAreaKeyUp,
      onClickSendMessage,
    };
  },
};
</script>

<style scoped>
.border {
  border: solid 1px rgba(177, 179, 184, 0.6);
}

.shadow {
  box-shadow: var(--el-box-shadow-light);
}

#chat-box {
  flex: 1;
  margin-top: 1rem;
  width: 28vw;
  background-color: rgba(236, 245, 255, 0.5);
}

#message-box-container {
  height: 17.8em;
  overflow-y: auto;
}

#message-input-box {
  display: flex;
  flex-direction: row;
  flex: 1;
  background-color: #fafafa;
  padding-top: 5px;
}

.message-input {
  border: none;
  background-color: transparent;
  width: 87%;
  outline: none;
  resize: none;
}

.message-input :focus {
  outline: none;
}

.send-button {
  margin-right: 5px;
}

.send-icon {
  transform: rotate(50deg);
}

.message-box-holder {
  width: 100%;
  margin: 0 0 15px;
  display: flex;
  flex-flow: column;
  align-items: flex-end;
}
.message-box {
  padding: 6px 10px;
  border-radius: 6px;
  position: relative;
  background: var(--el-color-warning-light-7);
  color: #6c6c6c;
  font-size: 12px;
  margin-right: 20px;
  margin-top: 5px;
  max-width: 40%;
  word-break: break-all;
  white-space: pre-wrap;
}
.message-partner {
  background: var(--el-color-primary-light-7);
  align-self: flex-start;
  margin-left: 20px;
}

@media only screen and (max-width: 500px) {
  #chat-box {
    width: 90vw;
    margin-top: unset;
    background-color: rgba(236, 245, 255, 0.5);
    margin-bottom: 5vh;
  }
  #message-box-container {
    height: 25vh;
  }
  .send-button {
    margin-right: 5px;
    width: 1em;
    height: 1em;
  }
}
</style>
