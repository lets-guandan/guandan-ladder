<template>
  <div>
      <button
        class="btn btn-secondary dropdown-toggle"
        type="button"
        id="dropdownMenuButton"
        data-toggle="dropdown"
        aria-haspopup="true"
        aria-expanded="false"
      >
<!--        todo 这里注释后 选择自定义后就无法返回？？？-->
       {{ selectedOption.label }}
      </button>
      <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
        <a
          class="dropdown-item"
          v-for="option in options"
          :key="option.value"
          :class="{ active: selectedOption.value === option.value }"
          @click="onOptionChange(option)"
        >
          {{ option.label }}
        </a>
        <a
          class="dropdown-item"
          :class="{ active: selectedOption.value === 'custom' }"
          @click="showCustom = true, onOptionChange(customOption)"
        >
          自定义区间
        </a>
      </div>

      <div class="custom-date-range" v-show="showCustom">
        <v-col cols="3" md="2">
        <v-menu
          v-model="startmenu"
          :close-on-content-click="false"
          :nudge-right="20"
          :return-value.sync="startDate"
          lazy
          transition="scale-transition"
          offset-y
          full-width
          min-width="20px">
          <template v-slot:activator="{ on }">
            <v-text-field
              v-model="startDate"
              label="开始日期"
              prepend-icon="mdi-calendar"
              readonly
              v-on="on"
            ></v-text-field>
          </template>
          <v-date-picker
            v-if="startmenu"
            v-model="startDate"
            @input="startmenu = false"
          ></v-date-picker>


        </v-menu>

          <v-menu
            v-model="endmenu"
            :close-on-content-click="true"
            :nudge-right="20"
            :return-value.sync="endDate"
            lazy
            transition="scale-transition"
            offset-y
            full-width
            min-width="20px">
            <template v-slot:activator="{ on }">
              <v-text-field
                v-if="endmenu"
                v-model="endDate"
                label="截止日期"
                prepend-icon="mdi-calendar"
                readonly
                v-on="on"
              ></v-text-field>
            </template>
            <v-date-picker
              v-if="endmenu"
              v-model="endDate"
              @input="endmenu = false"
            ></v-date-picker>
          </v-menu>
        </v-col>
      </div>

      <button class="btn btn-primary dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
        在 {{ selectedOption.label }} 期间，您的历史战绩如下：<span class="caret"></span>
      </button>
  </div>



</template>

<script>
import { ref } from 'vue';

export default {
  name: "HistoryRecord",
  props: {
    propsSelectedOption: {
      type: Object,
      required: true,
    },
  },
  setup(props, { emit }){
    const  options = [
        { value: '3_days', label: '近3天' },
        { value: '7_days', label: '近7天' },
        { value: '15_days', label: '近15天' },
        { value: '30_days', label: '近30天' },
        { value: 'custom', label: '自定义2区间' }
      ];
    const selectedOption = ref(options[0]); // 默认选中 "3天"
    const showCustom = ref(false);
    const startDate = ref(null);
    const endDate = ref(null);
    function onOptionChange(option) {
      if (option.value === "custom") {
        showCustom.value = true;
      } else {
        selectedOption.value = option;
        showCustom.value = false;

      }
       emit("optionChange", option);
    }

    const customOption = { value: "custom", label: "自定义区间" };
    return {
      options,
      showCustom,
      startDate,
      endDate,
      selectedOption,
      onOptionChange,
      customOption,
    };

  },
}

</script>

<style scoped>

.dropdown-toggle{
  margin-top: 10px;
  margin-left: 10px;
}
.dropdown-item {
  margin: 20px 10px 15px 10px;
  border-color: #4caf50;
  background-color: #eeeeee;
}
.dropdown-menu .dropdown-item.active  {

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
