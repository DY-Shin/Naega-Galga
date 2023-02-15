<template>
  <div id="chat-box" class="border shadow">
    <div id="message-box-container" ref="messageBoxRef">
      <div v-for="(message, index) in messageListRef" :key="index">
        <div v-if="message.index === myIndex" class="message-box-holder">
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
      <el-input v-model="inputtedMessage" @keyup.enter="sendMessage"></el-input>
      <el-button type="primary" @click="sendMessage" class="send-button">
        <el-icon class="send-icon"><Promotion /></el-icon>
      </el-button>
    </div>
  </div>
</template>

<script lang="ts">
import { toRef, ref, PropType, computed } from "vue";
import { MeetingMessage } from "@/types/MeetingChatType";

import { ElButton, ElIcon } from "element-plus";
import { Promotion } from "@element-plus/icons-vue";
import { useStore } from "vuex";

export default {
  props: {
    messageList: {
      type: Array as PropType<Array<MeetingMessage>>,
    },
  },
  components: { ElButton, ElIcon, Promotion },
  setup(props, context) {
    const store = useStore();
    const myIndex = computed(() => store.getters["userStore/userIndex"]).value;
    const messageListRef = toRef(props, "messageList");
    const inputtedMessage = ref("");
    const messageBoxRef = ref();
    const { emit } = context;

    const sendMessage = () => {
      if (!inputtedMessage.value) {
        return;
      }
      const message = inputtedMessage.value;
      inputtedMessage.value = "";

      //메세지 전송
      emit("sendMessage", {
        index: myIndex,
        text: message,
        sendedTime: new Date(),
      });

      messageBoxRef.value.scrollTop = messageBoxRef.value.scrollHeight;
    };

    return {
      myIndex,
      messageListRef,
      inputtedMessage,
      sendMessage,
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
  background-color: #fafafa;
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
  border-radius: 20px;
  position: relative;
  background: #ffffff;
  color: #6c6c6c;
  font-size: 12px;
  padding: 8px 15px;
  margin-right: 20px;
  margin-top: 5px;
  max-width: 40%;
  word-break: break-all;
  white-space: pre-wrap;
  border: 1px solid rgb(111, 111, 111);
}
.message-partner {
  background: rgb(223, 223, 223);
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
