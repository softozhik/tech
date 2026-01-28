<script setup lang="ts">
import { computed, onMounted, ref } from 'vue'
import { getCompanies } from '../api/companies'
import { reportEquipmentByDepartment, reportLicensesExpiring } from '../api/reports'
import type { CompanyResponseDto } from '../api/types'
import { Bar } from 'vue-chartjs'
import { Chart as ChartJS, CategoryScale, LinearScale, BarElement, Tooltip, Legend } from 'chart.js'

ChartJS.register(CategoryScale, LinearScale, BarElement, Tooltip, Legend)

const loading = ref(false)
const companies = ref<CompanyResponseDto[]>([])
const companyId = ref<number | null>(null)
const days = ref(30)

const byDept = ref<Record<string, number> | null>(null)
const expiring = ref<Array<Record<string, unknown>> | null>(null)

const companyOptions = computed(() => companies.value.map((c) => ({ title: c.name, value: c.id })))

const chartData = computed(() => {
  const labels = Object.keys(byDept.value ?? {})
  const data = Object.values(byDept.value ?? {})
  return {
    labels,
    datasets: [
      {
        label: 'Единиц техники',
        data,
        backgroundColor: '#a78bfa',
      },
    ],
  }
})

async function loadCompanies() {
  const res = await getCompanies({ page: 0, size: 200 })
  companies.value = res.content
  if (!companyId.value && companies.value[0]) companyId.value = companies.value[0].id
}

async function runReports() {
  if (!companyId.value) return
  loading.value = true
  try {
    byDept.value = await reportEquipmentByDepartment(companyId.value)
    expiring.value = await reportLicensesExpiring(companyId.value, days.value)
  } finally {
    loading.value = false
  }
}

onMounted(async () => {
  await loadCompanies()
  await runReports()
})
</script>

<template>
  <div class="d-flex align-center justify-space-between mb-6">
    <div>
      <div class="text-h5 font-weight-bold">Отчеты</div>
      <div class="text-medium-emphasis">`/api/reports/*`</div>
    </div>
    <div class="d-flex ga-3">
      <v-select
        v-model="companyId"
        :items="companyOptions"
        label="Компания"
        style="min-width: 320px"
        @update:model-value="runReports"
      />
      <v-text-field v-model.number="days" type="number" label="Дней" style="max-width: 140px" />
      <v-btn color="primary" :loading="loading" @click="runReports">Обновить</v-btn>
    </div>
  </div>

  <v-row>
    <v-col cols="12" md="7">
      <v-card class="pa-5" elevation="6">
        <div class="text-h6 font-weight-bold mb-2">Техника по отделам</div>
        <div class="text-medium-emphasis mb-4">`GET /api/reports/equipment-by-department?companyId=`</div>
        <div v-if="!byDept">—</div>
        <Bar v-else :data="chartData" />
      </v-card>
    </v-col>
    <v-col cols="12" md="5">
      <v-card class="pa-5" elevation="6">
        <div class="text-h6 font-weight-bold mb-2">Истекающие лицензии</div>
        <div class="text-medium-emphasis mb-4">`GET /api/reports/licenses-expiring?companyId=&days=`</div>
        <div class="text-medium-emphasis mb-3">Найдено: {{ expiring?.length ?? '—' }}</div>
        <v-list density="compact" v-if="expiring">
          <v-list-item v-for="(r, idx) in expiring.slice(0, 8)" :key="idx">
            <v-list-item-title class="text-body-2">{{ String(r.softwareName ?? r.name ?? 'License') }}</v-list-item-title>
            <v-list-item-subtitle class="text-caption">
              {{ String(r.expiryDate ?? r.expiry ?? r.date ?? '') }}
            </v-list-item-subtitle>
          </v-list-item>
        </v-list>
      </v-card>
    </v-col>
  </v-row>
</template>

