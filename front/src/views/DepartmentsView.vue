<script setup lang="ts">
import { computed, onMounted, ref } from 'vue'
import { getCompanies } from '../api/companies'
import { getDepartmentsByCompany } from '../api/departments'
import type { CompanyResponseDto, DepartmentResponseDto } from '../api/types'

const loading = ref(false)
const companies = ref<CompanyResponseDto[]>([])
const companyId = ref<number | null>(null)

const items = ref<DepartmentResponseDto[]>([])
const total = ref(0)
const page = ref(1)
const size = ref(10)

const companyOptions = computed(() => companies.value.map((c) => ({ title: c.name, value: c.id })))

async function loadCompanies() {
  const res = await getCompanies({ page: 0, size: 200 })
  companies.value = res.content
  if (!companyId.value && companies.value[0]) companyId.value = companies.value[0].id
}

async function load() {
  if (!companyId.value) return
  loading.value = true
  try {
    const res = await getDepartmentsByCompany(companyId.value, { page: page.value - 1, size: size.value })
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
      <div class="text-h5 font-weight-bold">Отделы</div>
      <div class="text-medium-emphasis">`GET /api/departments/company/{companyId}`</div>
    </div>
    <v-select
      v-model="companyId"
      :items="companyOptions"
      label="Компания"
      style="max-width: 420px"
      @update:model-value="() => { page = 1; load() }"
    />
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
          <th class="text-left">Название</th>
          <th class="text-left">Родитель</th>
          <th class="text-left">Активен</th>
          <th class="text-left">Описание</th>
        </tr>
      </template>
      <template #item="{ item }">
        <tr>
          <td class="text-medium-emphasis">{{ item.id }}</td>
          <td class="font-weight-medium">{{ item.name }}</td>
          <td class="text-medium-emphasis">{{ item.parentDepartmentName || '—' }}</td>
          <td>
            <v-chip size="small" :color="item.active ? 'success' : 'warning'" variant="tonal">
              {{ item.active ? 'Да' : 'Нет' }}
            </v-chip>
          </td>
          <td class="text-medium-emphasis">{{ item.description || '—' }}</td>
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

