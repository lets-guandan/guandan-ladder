<template>
  <!-- 单人排行榜 -->
  <div>
    <t-tabs :default-value="RankListTypeEnum.WIN_RATE" @change="fetchRankList">
      <t-tab-panel :value="RankListTypeEnum.WIN_RATE">
        <template #label>
          <div>胜率榜</div>
        </template>
      </t-tab-panel>
      <t-tab-panel :value="RankListTypeEnum.WIN_COUNT">
        <template #label>胜场榜</template>
      </t-tab-panel>
    </t-tabs>
    <t-list>
      <t-cell
        v-for="(item, index) in data"
        :key="item.uid"
        :title="item.nickname"
        :description="item.profile"
        align="middle"
        :bordered="false"
      >
        <template #leftIcon>
          <div style="display: flex; align-items: center">
            <span :class="['rank-num', index <= 2 ? 'top3' : '']">
              {{ index + 1 }}
            </span>
            <t-avatar shape="circle" :image="item.avatarUrl" />
          </div>
        </template>

        <template #rightIcon>
          <div>
            <div style="font-size: 8px">胜率：{{ toPercent(item.winNum, item.totalNum) }}</div>
            <div style="font-size: 8px">胜场：{{ item.winNum }}</div>
            <div style="font-size: 8px">败场：{{ item.totalNum - item.winNum }}</div>
          </div>
        </template>
      </t-cell>
    </t-list>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { RankListTypeEnum, type UserRankVO } from '@/api/types'
import { rankListApi } from '@/api'
import { useRouter } from 'vue-router'

const router = useRouter()
const data = ref<UserRankVO[]>()

/* 转换为百分比 */
function toPercent(num: number, total: number) {
  return total <= 0 ? '0%' : Math.round((num / total) * 1000) / 10.0 + '%'
}

/* 拉取排行数据 */
function fetchRankList(rankType: RankListTypeEnum) {
  rankListApi(rankType).then((res) => {
    if (res.code === 1000) {
      alert(res.message)
      router.replace('/login')
      return
    }
    data.value = res.data
  })
}

// 初始化页面数据
fetchRankList(RankListTypeEnum.WIN_RATE)
</script>

<style scoped>
.rank-num {
  font-size: 13px;
  padding: 0 6.5px;
  margin-inline-end: 12px;
}

.rank-num.top3 {
  background-color: #ffae00;
  color: #fff;
}
</style>
