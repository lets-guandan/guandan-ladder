<template>
  <div class="custom-range">
    <div class="form-group">
      <label for="start-date">起始日期:</label>
      <input type="date" class="form-control" id="start-date" v-model="startDate">
    </div>
    <div class="form-group">
      <label for="end-date">结束日期:</label>
      <input type="date" class="form-control" id="end-date" v-model="endDate">
    </div>
    <div class="form-group">
      <button class="btn btn-primary" @click="onSubmit">确定</button>
      <button class="btn btn-secondary" @click="onCancel">取消</button>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      startDate: '',
      endDate: ''
    }
  },
  methods: {
    onSubmit() {
      if (this.startDate && this.endDate) {
        this.$emit('submit', this.startDate, this.endDate)
      }
    },
    onCancel() {
      this.$emit('cancel')
    }
  }
}
</script>

<style scoped>
.custom-range {
  margin: 10px;
  padding: 10px;
  border: 1px solid #ddd;
}
</style>
