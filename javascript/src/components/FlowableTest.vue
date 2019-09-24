<template>
  <div>
    <button @click="callServerMethod">Flowable test</button>
    <p>see the console log</p>
  </div>
</template>

<script>
import { Flowable } from "rsocket-flowable";
export default {
  methods: {
    callServerMethod() {
      const flowable = new Flowable(subscriber => {
        // lambda is not executed until `subscribe()` is called
        const values = [0, 1, 2, 3];
        subscriber.onSubscribe({
          cancel: () => {},
          request: n => {
            while (n--) {
              if (values.length) {
                const next = values.shift();
                // Can't publish values until request() is called
                subscriber.onNext(next);
              } else {
                subscriber.onComplete();
                break;
              }
            }
          }
        });
      });
      flowable.subscribe({
        onComplete: () => console.log("done"),
        onError: error => console.error(error),
        onNext: value => {
          console.log("got onNext value " + value);
        },
        // Nothing happens until `request(n)` is called
        onSubscribe: sub => {
          sub.request(5);
        }
      });
    }
  }
};
</script>

<style lang="scss" scoped>
</style>