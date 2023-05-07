<template>
  <v-app-bar>
    <v-tabs align-tabs="center" color="deep-orange" fixed-tabs style="width: 100%;">
      <v-tab width="50%" @click="fetchRankList(RankListTypeEnum.WIN_RATE)">胜率榜</v-tab>
      <v-tab width="50%" @click="fetchRankList(RankListTypeEnum.WIN_COUNT)">胜场榜</v-tab>
    </v-tabs>
  </v-app-bar>
  <v-container>
    <v-list :lines="'three'" class="rank">
      <template v-for="(item, index) in data" :key="item.uid">
        <v-list-item
          :subtitle="item.profile"
          :title="item.nickname"
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
  </v-container>
</template>

<script lang="ts" setup>
import {ref} from "vue";
import {rankListApi} from "@/api";
import {RankListTypeEnum, UserRankVO} from "@/api/types";
import {useRouter} from "vue-router";

const router = useRouter();
const data = ref<UserRankVO[]>()

/* 转换为百分比 */
function toPercent(num: number, total: number) {
  return total <= 0 ? "0%" : (Math.round(num / total * 1000) / 10.00) + "%";
}

/* 拉取排行数据 */
function fetchRankList(rankType) {
  rankListApi(rankType).then(res => {
    if (res.code === 1000) {
      alert(res.message);
      router.replace("/login");
      return
    }
    data.value = res.data
  })
}

// 初始化页面数据
fetchRankList(RankListTypeEnum.WIN_RATE)

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
