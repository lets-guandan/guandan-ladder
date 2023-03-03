<template>
  <v-app>
    <default-bar />
    <default-view style="margin-bottom: 60px"/>
    <default-bottom />
  </v-app>
</template>

<script lang="ts" setup>
  import DefaultBar from './AppBar.vue'
  import DefaultView from './View.vue'
  import DefaultBottom from './Bottom.vue'
</script>
