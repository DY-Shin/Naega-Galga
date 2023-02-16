interface Message {
  isMine: boolean;
  text: string;
  sendedTime: Date;
}

interface MeetingMessage {
  index: number;
  text: string;
  sendedTime: Date;
}

export { Message, MeetingMessage };
