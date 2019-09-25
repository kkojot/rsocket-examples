<template>
  <div>
    <button @click="callServerMethod">Fire and forget</button>
    <SentReceivedViewer
      :sent="sent"
      :received="received"
    />
  </div>
</template>

<script>
import SentReceivedViewer from "@/components/SentReceivedViewer.vue";
export default {
  props: {
    socket: {
      type: Object,
      default: null
    }
  },
  components: {
    SentReceivedViewer
  },
  data() {
    return {
      sent: [],
      received: []
    };
  },
  methods: {
    callServerMethod() {
      console.log("click fire and forget!");
      //   this.sent = [];
      //   this.received = [];
      if (this.socket) {
        const message = { message: "fire and forget from JavaScript!" };
        this.socket.fireAndForget({
          data: message,
          metadata: ""
        });
        this.sent.push(message);
      } else {
        console.log("not connected...");
      }
    }
  }
};
</script>

<style lang="scss" scoped>
</style>