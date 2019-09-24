<template>
  <div>
    <button @click="callServerMethod">Request Response</button>
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
      console.log("request - response call...");
      if (this.socket) {
        const message = { message: "requestResponse from JavaScript!" };
        this.socket
          .requestResponse({
            data: message,
            metadata: ""
          })
          .subscribe({
            onComplete: data => {
              console.log("got response with requestResponse");
              this.received.push(data.data);
            },
            onError: error => {
              console.log("got error with requestResponse");
              console.error(error);
            },
            onSubscribe: cancel => {
              this.sent.push(message);
              /* call cancel() to stop onComplete/onError */
            }
          });
      } else {
        console.log("not connected...");
      }
    }
  }
};
</script>

<style lang="scss" scoped>
</style>