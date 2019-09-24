<template>
  <div class="home">
    <p>Welcome in the RSocket JavaScript tester</p>

    <table>
      <tr>
        <th>fireAndForget</th>
        <th>requestResponse</th>
        <th>requestStream</th>
        <th>Flowable Test</th>
      </tr>
      <tr>
        <td>
          <FireAndForget />
        </td>
        <td></td>
        <td></td>
        <td></td>
      </tr>
    </table>
  </div>
</template>

<script>
// @ is an alias to /src
import FireAndForget from "@/components/FireAndForget.vue";

export default {
  name: "home",
  components: { FireAndForget }
};
</script>

<style scoped>
table {
  margin: auto;
}
td {
  text-align: left;
}
</style>
