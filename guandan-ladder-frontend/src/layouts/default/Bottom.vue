<template>
  <div style="bottom: 0; z-index: 1004; transform: translateY(0%); position: fixed; height: 88px; width: 100%; text-align: end">
    <div style="margin: 16px">
      <report-form-dialog />
    </div>
  </div>
</template>

<script lang="ts">
import ReportFormDialog from "@/components/ReportFormDialog.vue";

export default {
  name: "Bottom",
  components: {ReportFormDialog}
}
</script>

<style scoped>

</style>
