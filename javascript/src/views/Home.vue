<template>
  <div class="home">
    <p>Welcome in the RSocket JavaScript tester</p>
    <p>RSocket is connected: {{ isConnected }}</p>

    <div class="responses">
      <FireAndForget :socket="socket" />
      <hr />
      <RequestResponse :socket="socket" />
      <hr />
      <RequestStream :socket="socket" />
      <hr />
      <FlowableTest />
    </div>
  </div>
</template>

<script>
import { RSocketClient, JsonSerializers } from "rsocket-core";
import RSocketWebSocketClient from "rsocket-websocket-client";
// @ is an alias to /src
import FireAndForget from "@/components/FireAndForget.vue";
import RequestResponse from "@/components/RequestResponse.vue";
import RequestStream from "@/components/RequestStream.vue";
import FlowableTest from "@/components/FlowableTest.vue";

export default {
  name: "home",
  components: { FireAndForget, RequestResponse, RequestStream, FlowableTest },
  data() {
    return {
      socket: null
    };
  },
  methods: {
    connect() {
      console.log("connecting with RSocket...");
      const transport = new RSocketWebSocketClient({
        url: "ws://localhost:8801"
      });
      const client = new RSocketClient({
        // send/receive JSON objects instead of strings/buffers
        serializers: JsonSerializers,
        setup: {
          // ms btw sending keepalive to server
          keepAlive: 60000,
          // ms timeout if no keepalive response
          lifetime: 180000,
          // format of `data`
          dataMimeType: "application/json",
          // format of `metadata`
          metadataMimeType: "application/json"
        },
        transport
      });
      client.connect().subscribe({
        onComplete: socket => {
          this.socket = socket;
        },
        onError: error => {
          console.log("got connection error");
          console.error(error);
        },
        onSubscribe: cancel => {
          /* call cancel() to abort */
        }
      });
    }
  },
  computed: {
    isConnected() {
      return this.socket ? true : false;
    }
  },
  mounted() {
    this.connect();
  }
};
</script>

<style scoped>
div.responses {
  width: 700px;
  margin: auto;
}
</style>
