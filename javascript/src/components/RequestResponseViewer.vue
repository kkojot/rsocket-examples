<template>
  <div>
    <p><strong>Sent:</strong></p>
    <ul>
      <li
        v-for="msg in sent"
        :key="msg"
      > {{ msg }}</li>
    </ul>
    <p><strong>Received:</strong></p>
    <ul>
      <li
        v-for="msg in received"
        :key="msg"
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