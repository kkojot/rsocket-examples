<template>
  <div>
    <button @click="fireAndForget">Fire and forget</button>
    <RequestResponseViewer
      :sent="sent"
      :received="received"
    />
  </div>
</template>

<script>
import RequestResponseViewer from "@/components/RequestResponseViewer.vue";
export default {
  components: {
    RequestResponseViewer
  },
  data() {
    return {
      sent: [],
      received: []
    };
  },
  methods: {
    fireAndForget() {
      console.log("click fire and forget!");
      this.sent = [];
      this.received = [];
      this.sent.push("poszlo");
      this.received.push("doszlo");
    }
  }
};
</script>

<style lang="scss" scoped>
</style>