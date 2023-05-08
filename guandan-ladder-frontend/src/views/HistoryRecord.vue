<script setup lang="ts">
import {ref} from "vue";
import VueDatePicker from '@vuepic/vue-datepicker';
import '@vuepic/vue-datepicker/dist/main.css'
import {myHistoryGameRecordApi} from "@/api";

// 当前查询的日期类型
const dateType = ref()

// 日期范围，当选择自定义时使用
const dateRange = ref()

const records = ref()
function loadData() {
  // TODO 查询根据日期类型进行查询
  myHistoryGameRecordApi().then(res => {
    records.value = res.data
  })
}
loadData()
</script>


<template>
  <div class="d-flex align-center flex-column">
    <v-btn-toggle
      v-model="dateType"
      color="primary"
      variant="text"
      rounded="0"
      @click="loadData"
    >
      <v-btn size="x-small" value="3_days">近3天</v-btn>
      <v-btn size="x-small" value="7_days">近7天</v-btn>
      <v-btn size="x-small" value="15_days">近15天</v-btn>
      <v-btn size="x-small" value="30_days">近30天</v-btn>
      <v-btn size="x-small" value="custom">自定义区间</v-btn>
    </v-btn-toggle>
  </div>

  <div v-show="dateType == 'custom'" style="margin: 0 30px">
    <vue-date-picker v-model="dateRange" range/>
  </div>

  <v-list :border="12">
    <v-list-item v-for="record in records" :key="record.id">
      <v-row align="center">
        <v-col cols="4">
          {{ record.gameTime }}
        </v-col>
        <v-col>
          <v-row align="center">
            <v-col cols="3" align-self="center">胜者：</v-col>
            <v-col>
              <v-chip
                label
                color="green"
                text-color="white"
                style="margin-right: 8px"
              >
                {{ record.winNickname1 }}
              </v-chip>
              <v-chip
                label
                color="green"
                text-color="white"
              >
                {{ record.winNickname2 }}
              </v-chip>
            </v-col>
          </v-row>
          <v-row align="center">
            <v-col cols="3">败者：</v-col>
            <v-col>
              <v-chip
                label
                color="red"
                text-color="white"
                style="margin-right: 8px"
              >
                {{ record.loseNickname1 }}
              </v-chip>
              <v-chip
                label
                color="red"
                text-color="white"
              >
                {{ record.loseNickname2 }}
              </v-chip>
            </v-col>
          </v-row>
        </v-col>
      </v-row>
      <v-divider/>
    </v-list-item>
  </v-list>
</template>

<style scoped>
.v-row + .v-row {
  margin-top: 0px;
}
</style>

