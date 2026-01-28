<script setup lang="ts">
import { computed, onMounted, ref } from 'vue'
import { getCompanies } from '../api/companies'
import { getEquipment } from '../api/equipment'
import type { CompanyResponseDto, EquipmentResponseDto, EquipmentStatus } from '../api/types'

const loading = ref(false)
const companies = ref<CompanyResponseDto[]>([])
const companyId = ref<number | null>(null)
const status = ref<EquipmentStatus | null>(null)

const items = ref<EquipmentResponseDto[]>([])
const total = ref(0)
const page = ref(1)
const size = ref(10)

const companyOptions = computed(() => companies.value.map((c) => ({ title: c.name, value: c.id })))
const statusOptions = [
  { title: 'Любой', value: null },
  { title: 'AVAILABLE', value: 'AVAILABLE' },
  { title: 'IN_USE', value: 'IN_USE' },
  { title: 'IN_REPAIR', value: 'IN_REPAIR' },
  { title: 'RETIRED', value: 'RETIRED' },
  { title: 'RESERVED', value: 'RESERVED' },
]

async function loadCompanies() {
  const res = await getCompanies({ page: 0, size: 200 })
  companies.value = res.content
  if (!companyId.value && companies.value[0]) companyId.value = companies.value[0].id
}

async function load() {
  loading.value = true
  try {
    const res = await getEquipment({
      companyId: companyId.value ?? undefined,
      status: status.value ?? undefined,
      page: page.value - 1,
      size: size.value,
    })
    items.value = res.content
    total.value = res.totalElements
  } finally {
    loading.value = false
  }
}

onMounted(async () => {
  await loadCompanies()
  await load()
})
</script>

<template>
  <div class="d-flex align-center justify-space-between mb-6">
    <div>
      <div class="text-h5 font-weight-bold">Техника</div>
      <div class="text-medium-emphasis">`GET /api/equipment` (filters: companyId, status, categoryId)</div>
    </div>
    <div class="d-flex ga-3">
      <v-select
        v-model="companyId"
        :items="companyOptions"
        label="Компания"
        style="min-width: 320px"
        @update:model-value="() => { page = 1; load() }"
      />
      <v-select
        v-model="status"
        :items="statusOptions"
        label="Статус"
        style="min-width: 220px"
        @update:model-value="() => { page = 1; load() }"
      />
    </div>
  </div>

  <v-card elevation="6">
    <v-data-table
      :items="items"
      :loading="loading"
      :items-per-page="size"
      :page="page"
      :items-length="total"
      item-key="id"
      @update:page="(p:number) => { page = p; load() }"
      @update:items-per-page="(s:number) => { size = s; page = 1; load() }"
    >
      <template #headers>
        <tr>
          <th class="text-left">ID</th>
          <th class="text-left">Категория</th>
          <th class="text-left">Модель</th>
          <th class="text-left">Serial</th>
          <th class="text-left">Инвентарный</th>
          <th class="text-left">Статус</th>
          <th class="text-left">Пользователь</th>
        </tr>
      </template>
      <template #item="{ item }">
        <tr>
          <td class="text-medium-emphasis">{{ item.id }}</td>
          <td class="text-medium-emphasis">{{ item.categoryName || item.categoryId }}</td>
          <td class="font-weight-medium">{{ item.manufacturer ? `${item.manufacturer} ${item.model}` : item.model }}</td>
          <td class="text-medium-emphasis">{{ item.serialNumber }}</td>
          <td class="text-medium-emphasis">{{ item.inventoryNumber || '—' }}</td>
          <td>
            <v-chip size="small" color="info" variant="tonal">{{ item.status || '—' }}</v-chip>
          </td>
          <td class="text-medium-emphasis">{{ item.currentUserName || '—' }}</td>
        </tr>
      </template>
      <template #bottom>
        <div class="d-flex align-center justify-end pa-3">
          <v-pagination v-model="page" :length="Math.max(1, Math.ceil(total / size))" @update:model-value="load" />
        </div>
      </template>
    </v-data-table>
  </v-card>
</template>

