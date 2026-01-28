<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { getCompanies } from '../api/companies'

const loading = ref(false)
const companiesCount = ref<number | null>(null)

onMounted(async () => {
  loading.value = true
  try {
    const page = await getCompanies({ page: 0, size: 1 })
    companiesCount.value = page.totalElements
  } finally {
    loading.value = false
  }
})
</script>

<template>
  <div class="d-flex align-center justify-space-between mb-6">
    <div>
      <div class="text-h5 font-weight-bold">Дашборд</div>
      <div class="text-medium-emphasis">Короткая сводка</div>
    </div>
  </div>

  <v-row>
    <v-col cols="12" md="4">
      <v-card class="pa-5" elevation="6">
        <div class="text-caption text-medium-emphasis">Компаний</div>
        <div class="text-h4 font-weight-bold">
          <span v-if="loading">…</span>
          <span v-else>{{ companiesCount ?? '—' }}</span>
        </div>
      </v-card>
    </v-col>
  </v-row>
</template>

