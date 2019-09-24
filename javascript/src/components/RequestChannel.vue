<template>
  <div>
    <button @click="callServerMethod">Request Channel</button>
    <SentReceivedViewer
      :sent="sent"
      :received="received"
    />
  </div>
</template>

<script>
import SentReceivedViewer from "@/components/SentReceivedViewer.vue";
import { Flowable } from "rsocket-flowable";
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
      console.log("request - stream call...");
      if (this.socket) {
        const flowablePayload = new Flowable(subscriber => {
          subscriber.onSubscribe({
            cancel: () => {},
            request: n => {
              for (let index = 0; index < n; index++) {
                const message = {
                  message: "requestChannel from JavaScript! #" + index
                };
                subscriber.onNext(message);
              }
              subscriber.onComplete();
            }
          });
        });

        // test flowable payload
        // flowablePayload.subscribe({
        //   onComplete: () => console.log("done"),
        //   onError: error => console.error(error),
        //   onNext: value => {
        //     console.log("got onNext value ");
        //     console.log(value);
        //   },
        //   // Nothing happens until `request(n)` is called
        //   onSubscribe: sub => {
        //     sub.request(5);
        //   }
        // });

        this.socket
          .requestChannel({
            data: flowablePayload,
            metadata: ""
          })
          .subscribe({
            onComplete: () => {
              console.log("requestChannel done");
              this.received.push("requestChannel done");
            },
            onError: error => {
              console.log("got error with requestChannel");
              console.error(error);
            },
            onNext: value => {
              // console.log("got next value in requestChannel..");
              this.received.push(value.data);
            },
            // Nothing happens until `request(n)` is called
            onSubscribe: sub => {
              console.log("subscribe request Channel!");
              sub.request(7);
              this.sent.push(message);
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