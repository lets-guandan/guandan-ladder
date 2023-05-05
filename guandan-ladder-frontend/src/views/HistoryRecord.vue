<template>
  <div>
    <div class="dropdown">

      <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
        <a class="dropdown-item" v-for="option in options" :key="option.value" :class="{ active: selectedOption.value === option.value }" @click="onOptionChange(option)">
          {{ option.label + '         ' }}
        </a>
        <div class="dropdown-divider"></div>
        <a class="dropdown-item" href="#" @click.prevent="showCustomRange">自定义时间区间</a>
      </div>
      <button class="btn btn-primary dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
        在 {{ selectedOption.label }} 期间，您的历史战绩如下：<span class="caret"></span>
      </button>
    </div>

    <custom-range v-if="showCustom" @cancel="hideCustomRange" @submit="handleCustomRange"></custom-range>
  </div>
</template>

<script>

import {historyGameRecordApi} from "@/api";
import SelectTimeRangeBySelf from '@/components/SelectTimeRangeBySelf.vue'

function getHistoryRecords() {
  historyGameRecordApi().then(res => {
    items.value = res.data
  })
}

getHistoryRecords()

export default {
  name: "HistoryRecord",
  components:{
    // eslint-disable-next-line vue/no-unused-components
    SelectTimeRangeBySelf
  },
  data() {
    return {
      selectedOption: { value: '3_days', label: '近3天' },
      options: [
        { value: '3_days', label: '近3天' },
        { value: '7_days', label: '近7天' },
        { value: '15_days', label: '近15天' },
        { value: '30_days', label: '近30天' }
      ],
      showCustom: false
    }
  },
  methods: {
    onOptionChange(option) {
      if (option.value === 'custom') {
        this.showCustomRange()
      } else {
        this.selectedOption = option
      }
    },
    showCustomRange() {
      this.showCustom = true
    },
    hideCustomRange() {
      this.showCustom = false
    },
    handleCustomRange(startDate, endDate) {
      console.log('Custom range selected:', startDate, endDate)
      this.hideCustomRange()
    }
  }
}

</script>

<style scoped>
.dropdown-menu .dropdown-item .selected-indicator {
  float: right;

}

.dropdown-toggle{
  margin-top: 10px;
  margin-left: 10px;
}
.dropdown-item {
  margin: 20px 10px 15px 10px;
  border-color: #4caf50;
  background-color: #eeeeee;
}
.dropdown-menu .dropdown-item.active {

  display: inline-block;
  /*padding: 0.5rem 1rem;*/
  font-size: 1rem;
  font-weight: 200;
  line-height: 1.5;
  text-align: center;
  color: #fff;
  background-color: #388e3c;

  border-radius: 0.25rem;
  transition: color 0.15s ease-in-out, background-color 0.15s ease-in-out,
  border-color 0.15s ease-in-out, box-shadow 0.15s ease-in-out;
  cursor: pointer;


}
</style>
