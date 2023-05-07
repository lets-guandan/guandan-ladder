<template>
  <!--  <v-list>-->
  <!--    <v-list-item>-->
  <!--      <v-list-item v-for="item in items" :key="item.id">-->
  <!--        <div style="border: 1px solid black;">-->
  <!--          <div>比赛时间：{{ item.gameTime }}</div>-->
  <!--          <div>胜者：{{ item.winUid1 }} + {{ item.winUid1 }}</div>-->
  <!--          <div>败者：{{ item.loseUid1 }} + {{ item.loseUid1 }}</div>-->
  <!--        </div>-->
  <!--      </v-list-item>-->
  <!--    </v-list-item>-->
  <!--  </v-list>-->

<!--  增加二进制结果展示；增加button组合按钮 待我确认|| 与我有关 请求接口getUnconfirmedRecordApi传1和2-->
  <v-btn-toggle    class="score-btn" v-model="toggleSelect">
      <v-btn value="2" @click="initUnconfirmedRecords(2)">  与我有关 </v-btn>
      <v-btn value="1" @click="initUnconfirmedRecords(1)" > 待我确认 </v-btn>
    </v-btn-toggle>

  <div class="score-board" v-if="items.length > 0">
    <div class="score-item" v-for="item in items" :key="item.id">
      <div class="score-time">{{ item.gameTime }}</div>
      <div class="score-players">
        <v-badge bordered dot :color="item.winUid1Flag >0 ? 'success': 'white'">
          <div class="score-winner">{{ item.winNickname1 }}</div>
        </v-badge>
        <v-badge bordered dot :color="item.winUid2Flag >0 ? 'success': 'white'">
          <div class="score-winner">{{ item.winNickname2 }}</div>
        </v-badge>
        <v-badge bordered dot :color="item.loseUid1Flag >0 ? 'success': 'white'">
          <div class="score-loser">{{ item.loseNickname1 }}</div>
        </v-badge>
        <v-badge bordered dot :color="item.loseUid2Flag >0 ? 'success': 'white'">
          <div class="score-loser">{{ item.loseNickname2 }}</div>
        </v-badge>
      </div>

        <div class="score-confirm" @click="confirm(item)">
        <v-btn>确认</v-btn>
      </div>
    </div>
  </div>

  <div v-else>
    暂无待生效战绩列表
  </div>
</template>

<script setup lang="ts">
import {ref} from "vue";
import {confirmRecordApi, getUnconfirmedRecordApi, isSuccess} from "@/api";

const items = ref([])
const toggleSelect = ref('2')
function initUnconfirmedRecords(req) {
  getUnconfirmedRecordApi(req).then(res => {
    items.value = res.data as any

  })
}

// 初始化
initUnconfirmedRecords(2)

function confirm(item) {
  confirmRecordApi(item.id)
    .then(res => {
      if (isSuccess(res)) {
        initUnconfirmedRecords(2)
        alert('战局确认成功！')
      } else {
        alert('战局确认失败！' + res.message)
      }
    })
    .catch(e => {
      alert('战局确认异常！' + e.message)
    })
}


function  badgeColor() {
    return this.count > 0 ? 'red' : 'green'
}
</script>

<script lang="ts">
export default {
  name: "UnconfirmedRecord"
}
</script>

<style scoped>
.score-board {
  display: flex;
  flex-direction: column;
  gap: 10px;
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.score-item {
  display: flex;
  align-items: center;
  justify-content: space-between;
  border: 1px solid #eee;
  border-radius: 5px;
  padding: 10px;
}

.score-time {
  font-size: 16px;
  font-weight: bold;
}

.score-players {
  display: flex;
  flex-direction: column;
  gap: 5px;
}

.score-winner,
.score-loser {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 80px;
  height: 30px;
  border-radius: 5px;
}

.score-winner {
  background-color: #98fb98;
}

.score-loser {
  background-color: #f08080;
}

.score-confirm button {
  background-color: #4caf50;
  color: #fff;
  border: none;
  border-radius: 5px;
  padding: 8px 16px;
  font-size: 14px;
  cursor: pointer;
}

.score-confirm button:hover {
  background-color: #388e3c;
}
.score-btn {
  display: flex;
  justify-content: center;
  background-color: #8ac7b0
}

</style>
