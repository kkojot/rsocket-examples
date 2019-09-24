<template>
  <div>
    <p><strong>Sent:</strong></p>
    <ul>
      <li
        v-for="(msg, index) in sent"
        :key="index"
      > {{ msg }}</li>
    </ul>
    <p><strong>Received:</strong></p>
    <ul>
      <li
        v-for="(msg, index) in received"
        :key="index"
      > {{ msg }}</li>
    </ul>
  </div>
</template>

<script>
export default {
  props: {
    sent: {
      type: Array,
      default: []
    },
    received: {
      type: Array,
      default: []
    }
  }
};
</script>

<style lang="scss" scoped>
</style>