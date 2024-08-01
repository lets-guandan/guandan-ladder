<template>
  <!-- 待确认战绩 -->
  <div>
    <t-tabs :default-value="ConfirmListTypeEnum.WAIT_MY_CONFIRM_LIST" @change="fetchConfirmList">
      <t-tab-panel :value="ConfirmListTypeEnum.WAIT_MY_CONFIRM_LIST">
        <template #label>
          <div>待我确认战绩</div>
        </template>
      </t-tab-panel>
      <t-tab-panel :value="ConfirmListTypeEnum.WAIT_OTHER_CONFIRM_LIST">
        <template #label>待他人确认战绩</template>
      </t-tab-panel>
    </t-tabs>
    <t-list>
      <t-cell
        v-for="item in data"
        :key="item"
        :title="''"
        align="left"
        :bordered="true"
      >
        <div>
          <span class="cell win-content" style="float: left;" >
            {{ item.winNickname1 }}
          </span>
          <span class="switch-content">
              <t-switch style="display: inline-block;" :default-value="item.winUid1Flag == '1'"
                        disabled :icon="icons" />
          </span>

          <span class="cell win-content" style="float: left;">
            {{ item.winNickname2 }}
          </span>
          <span class="switch-content">
              <t-switch style="display: inline-block;" :default-value="item.winUid2Flag == '1'"
                        disabled :icon="icons" />
          </span>
          <br/>

          <span class="cell loser-content" style="float: left;">
            {{ item.loseNickname1 }}
          </span>
          <span class="switch-content">
              <t-switch style="display: inline-block;" :default-value="item.loseUid1Flag == '1'"
                        disabled :icon="icons" />
          </span>

          <span class="cell loser-content" style="float: left;">
            {{ item.loseNickname2 }}
          </span>
          <span class="switch-content">
              <t-switch style="display: inline-block;" :default-value="item.loseUid2Flag == '1'"
                        disabled :icon="icons" />
          </span>
        </div>
        <div>
          <t-button v-if="tab=== ConfirmListTypeEnum.WAIT_MY_CONFIRM_LIST"
                    size="extra-small" theme="primary"
                    block @click="ConfirmRecord(item.id)"
                    content="确认战绩"/>
        </div>
      </t-cell>
    </t-list>
  </div>
</template>

<script setup lang="ts">
import { h, ref } from 'vue'
import { CheckIcon, CloseIcon } from 'tdesign-icons-vue-next'
import { ConfirmListTypeEnum, type GameRecordVO } from '@/api/types'
import { getUnconfirmedRecordApi, confirmRecordApi } from '@/api'
import { useRouter } from 'vue-router'

const icons = [h(CheckIcon, { size: '20px' }), h(CloseIcon, { size: '20px' })]
const columns = ref([
  { colKey: 'nickname', title: '昵称', width: 180 },
  { colKey: 'flag', title: '确认状态', width: 180 }
])

const router = useRouter()
const data = ref<GameRecordVO[]>()
const tab = ref<ConfirmListTypeEnum>()

/* 拉取对应列表数据 */
function fetchConfirmList(confirmType: ConfirmListTypeEnum) {
  // 给tab赋值，用于判断是否展示确认战绩按钮
  tab.value = confirmType
  getUnconfirmedRecordApi(confirmType).then((res) => {
    if (res.code === 1000) {
      alert(res.message)
      router.replace('/login')
      return
    }
    data.value = res.data
  })
}

function ConfirmRecord(id: number) {
  confirmRecordApi(id).then((res) => {
    if (res.code === 1000) {
      alert(res.message)
      router.replace('/login')
      return
    }
    // 重新拉取数据
    fetchConfirmList(ConfirmListTypeEnum.WAIT_MY_CONFIRM_LIST)
  })
}

// 初始化页面数据
fetchConfirmList(ConfirmListTypeEnum.WAIT_MY_CONFIRM_LIST)
</script>

<style scoped>
@import "@/style/unconfirmedRecord/iconfont.css";

.win-content {
  font-family: "unconfirmedRecordStyle";
  position: relative;
  display: inline-block; /* 确保span有宽度，以便定位角标 */
  padding-top: 5px;
  padding-bottom: 5px;
  width: 110px;
}

.win-content::after {
  content: '\e609'; /* 角标的内容 */
  position: absolute;
  top: -0.04%; /* 调整数字为负值，让角标向上偏移 */
  left: -20%; /* 调整数字为负值，让角标向左偏移 */
  font-size: larger; /* 可选，减小角标的字体大小 */
  color: green;
}

.loser-content {
  font-family: "unconfirmedRecordStyle";
  position: relative;
  display: inline-block; /* 确保span有宽度，以便定位角标 */
  padding-top: 5px;
  padding-bottom: 5px;
  width: 110px;
}

.loser-content::after {
  content: '\e627'; /* 角标的内容 */
  position: absolute;
  top: -0.04%; /* 调整数字为负值，让角标向上偏移 */
  left: -30%; /* 调整数字为负值，让角标向左偏移 */
  font-size: larger; /* 可选，减小角标的字体大小 */
  color: red;
}


.switch-content {
  position: relative;
  display: inline-block; /* 确保span有宽度，以便定位角标 */
  padding-top: 5px;
  padding-bottom: 5px;
  padding-right: 3px;
}
</style>