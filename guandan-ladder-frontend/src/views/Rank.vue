<template>
  <v-card>

  </v-card>
  <v-tabs
    v-model="tab"
    bg-color="green-darken-3"
  >
    <v-tab color="deep-orange" value="2" width="50%" @click="switchTab()">胜率榜</v-tab>
    <v-tab color="deep-orange" value="1" width="50%" @click="switchTab()">胜场榜</v-tab>
  </v-tabs>
  <v-list :lines="'three'" class="rank">
    <template v-for="(item, index) in data" :key="item.uid">
      <v-list-item
        :title="item.nickname"
        :subtitle="item.profile"
      >
        <template v-slot:prepend>
          <span :class="['rank-num', index <= 2? 'top3': '']">
            {{ index + 1 }}
          </span>
          <v-avatar>
            <v-img
              :src="item.avatarUrl"
              alt="Avatar"
            ></v-img>
          </v-avatar>
        </template>

        <template v-slot:append>
          <div>
            <div style="font-size: 8px">胜率：{{ toPercent(item.winNum, item.totalNum) }}</div>
            <div style="font-size: 8px">胜场：{{ item.winNum }}</div>
            <div style="font-size: 8px">败场：{{ item.totalNum - item.winNum }}</div>
          </div>
        </template>
      </v-list-item>
    </template>
  </v-list>
</template>

<script lang="ts" setup>
import {ref} from "vue";
import {rankListApi} from "@/api";
import {RankListTypeEnum, UserRankVO} from "@/api/types";
import {useRouter} from "vue-router";

const router = useRouter();
const data = ref<UserRankVO[]>()
const tab = ref<RankListTypeEnum>(RankListTypeEnum.WIN_RATE)
// 初始化页面数据
switchTab()

// tab切换方法
function switchTab() {
  rankListApi(tab.value).then(res => {
    if (res.code === 1000) {
      alert(res.message);
      router.replace("/login");
      return
    }
    data.value = res.data
  })
}


function toPercent(num: number, total: number) {
  return total <= 0 ? "0%" : (Math.round(num / total * 1000) / 10.00) + "%";
}


</script>


<style scoped lang="scss">
.rank-num {
  font-size: 13px;
  padding: 0 6.5px;
  margin-inline-end: 16px
}

.rank-num.top3 {
  background-color: #ffae00;
  color: #fff;
}

.rank :deep(.v-list-item--three-line .v-list-item__prepend) {
  align-self: center !important;
}
</style>
