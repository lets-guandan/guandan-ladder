<script setup lang="ts">
import {ref} from "vue";
import {useTokenStore} from "@/store/user-store";
import {useRouter} from "vue-router";

const dialog = ref(false)

const router = useRouter();

const screenWidth = ref(240)
const screenHeight = ref(140)

function exitLogin() {
  useTokenStore().token = undefined
  router.push("/login")
}

defineExpose({
  open: () => {
    dialog.value = true
    screenWidth.value = window.innerWidth * 0.618
    screenHeight.value = screenWidth.value * 0.525
  }
})
</script>

<template>
  <v-dialog v-model="dialog" persistent width="auto">
    <v-card :height="screenHeight" :width="screenWidth">
      <v-card-text style="align-self: center">确认退出登录？</v-card-text>
      <v-card-actions style="align-self: center">
        <v-btn color="green-darken-1" variant="text" @click="dialog = false">
          取消
        </v-btn>
        <v-btn color="green-darken-1" variant="text" @click="dialog = false; exitLogin()">
          确认
        </v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<style scoped>

</style>
