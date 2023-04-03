<template>
  <v-dialog
    v-model="dialog"
    persistent
    width="1024"
  >
    <template v-slot:activator="{ props }">
      <v-btn
        v-bind="props"
        color="primary"
        icon="mdi-plus"
        size="large"
        style="transform-origin: center center"
      ></v-btn>
    </template>
    <v-card>
      <v-card-title>
        <span class="text-h5">战局上报</span>
      </v-card-title>
      <v-card-text>
        <v-container>
          <v-row>
            <v-col
              cols="12"
              sm="6"
            >
              <v-autocomplete
                :items="items"
                item-title="nickname"
                item-value="uid"
                label="胜者1"
                clearable
                v-model="formModel.winUid1"
              >
                <template v-slot:item="{ props, item }">
                  <v-list-item
                    lines="one"
                    v-bind="props"
                    :prepend-avatar="item?.raw?.avatarUrl"
                    :title="item?.raw?.nickname"
                  />
                </template>
              </v-autocomplete>
            </v-col>
            <v-col
              cols="12"
              sm="6"
            >
              <v-autocomplete
                :items="items"
                item-title="nickname"
                item-value="uid"
                label="胜者2"
                clearable
                v-model="formModel.winUid2"
              >
                <template v-slot:item="{ props, item }">
                  <v-list-item
                    lines="one"
                    v-bind="props"
                    :prepend-avatar="item?.raw?.avatarUrl"
                    :title="item?.raw?.nickname"
                  />
                </template>
              </v-autocomplete>
            </v-col>
            <v-col
              cols="12"
              sm="6"
            >
              <v-autocomplete
                :items="items"
                item-title="nickname"
                item-value="uid"
                label="败者1"
                clearable
                v-model="formModel.loseUid1"
              >
                <template v-slot:item="{ props, item }">
                  <v-list-item
                    lines="one"
                    v-bind="props"
                    :prepend-avatar="item?.raw?.avatarUrl"
                    :title="item?.raw?.nickname"
                  />
                </template>
              </v-autocomplete>
            </v-col>
            <v-col
              cols="12"
              sm="6"
            >
              <v-autocomplete
                :items="items"
                item-title="nickname"
                item-value="uid"
                label="败者2"
                clearable
                v-model="formModel.loseUid2"
              >
                <template v-slot:item="{ props, item }">
                  <v-list-item
                    lines="one"
                    v-bind="props"
                    :prepend-avatar="item?.raw?.avatarUrl"
                    :title="item?.raw?.nickname"
                  />
                </template>
              </v-autocomplete>
            </v-col>
            <v-col
              cols="12"
              sm="6"
            >
              <VueDatePicker
                enable-seconds
                model-type="yyyy-MM-dd HH:mm:ss"
                format="yyyy-MM-dd HH:mm:ss"
                placeholder="掼蛋时间"
                v-model="formModel.gameTime"
                :teleport="true"
              />
            </v-col>
          </v-row>
        </v-container>
      </v-card-text>
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn
          color="blue-darken-1"
          variant="text"
          @click="dialog = false"
        >
          Close
        </v-btn>
        <v-btn
          color="blue-darken-1"
          variant="text"
          @click="handleSubmit"
        >
          Save
        </v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script setup lang="ts">
import {reactive, ref, toRaw} from "vue";
import {listUserApi, reportGameRecordApi} from "@/api";
import type {GameRecordDTO} from "@/api/types";
import {UserVO} from "@/api/types";

import VueDatePicker from '@vuepic/vue-datepicker';
import '@vuepic/vue-datepicker/dist/main.css'
import {useRouter} from "vue-router";


const router = useRouter();
const dialog = ref(false)

const items = ref<UserVO[]>()
listUserApi().then(res => {
  if (res.code === 1000) {
    if (res.code === 1000) {
      router.replace("/login");
      return
    }
  }
  items.value = res.data
})

const formModel = reactive<GameRecordDTO>({
  winUid1: '',
  winUid2: '',
  loseUid1: '',
  loseUid2: '',
  gameTime: ''
})

function handleSubmit() {
  reportGameRecordApi(toRaw(formModel))
}

</script>

<script lang="ts">
export default {
  name: "ReportFormDialog"
}
</script>

<style scoped>

</style>
