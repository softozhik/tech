<script setup lang="ts">
import { computed, onMounted, ref } from 'vue'
import { getCompanies } from '../api/companies'
import { getExpiringLicenses, getLicensesByCompany } from '../api/licenses'
import type { CompanyResponseDto, SoftwareLicenseResponseDto } from '../api/types'

const loading = ref(false)
const companies = ref<CompanyResponseDto[]>([])
const companyId = ref<number | null>(null)
const days = ref(30)

const items = ref<SoftwareLicenseResponseDto[]>([])
const total = ref(0)
const page = ref(1)
const size = ref(10)

const expiring = ref<SoftwareLicenseResponseDto[] | null>(null)

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
    const res = await getLicensesByCompany(companyId.value, { page: page.value - 1, size: size.value })
    items.value = res.content
    total.value = res.totalElements
    expiring.value = null
  } finally {
    loading.value = false
  }
}

async function loadExpiring() {
  if (!companyId.value) return
  loading.value = true
  try {
    expiring.value = await getExpiringLicenses(companyId.value, days.value)
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
      <div class="text-h5 font-weight-bold">Лицензии</div>
      <div class="text-medium-emphasis">`GET /api/licenses/company/{companyId}`</div>
    </div>
    <div class="d-flex ga-3">
      <v-select
        v-model="companyId"
        :items="companyOptions"
        label="Компания"
        style="min-width: 320px"
        @update:model-value="() => { page = 1; load() }"
      />
      <v-text-field v-model.number="days" type="number" label="Дней" style="max-width: 140px" />
      <v-btn variant="tonal" :disabled="loading" @click="loadExpiring">Истекающие</v-btn>
    </div>
  </div>

  <v-alert
    v-if="expiring"
    class="mb-4"
    type="warning"
    variant="tonal"
    title="Истекающие лицензии"
    :text="`Найдено: ${expiring.length}`"
  />

  <v-card elevation="6">
    <v-data-table
      :items="expiring ?? items"
      :loading="loading"
      :items-per-page="size"
      :page="page"
      :items-length="expiring ? expiring.length : total"
      item-key="id"
      @update:page="(p:number) => { page = p; if (!expiring) load() }"
      @update:items-per-page="(s:number) => { size = s; page = 1; if (!expiring) load() }"
    >
      <template #headers>
        <tr>
          <th class="text-left">ID</th>
          <th class="text-left">ПО</th>
          <th class="text-left">Vendor</th>
          <th class="text-left">Тип</th>
          <th class="text-left">Expiry</th>
          <th class="text-left">Seats</th>
          <th class="text-left">Status</th>
        </tr>
      </template>
      <template #item="{ item }">
        <tr>
          <td class="text-medium-emphasis">{{ item.id }}</td>
          <td class="font-weight-medium">{{ item.softwareName }}</td>
          <td class="text-medium-emphasis">{{ item.softwareVendor || '—' }}</td>
          <td class="text-medium-emphasis">{{ item.licenseType || '—' }}</td>
          <td class="text-medium-emphasis">{{ item.expiryDate || '—' }}</td>
          <td class="text-medium-emphasis">
            {{ (item.seatsUsed ?? '—') + '/' + (item.seatsTotal ?? '—') }}
          </td>
          <td>
            <v-chip size="small" color="info" variant="tonal">{{ item.status || '—' }}</v-chip>
          </td>
        </tr>
      </template>
      <template #bottom>
        <div class="d-flex align-center justify-end pa-3">
          <v-btn v-if="expiring" variant="text" @click="load">Показать все</v-btn>
          <v-pagination
            v-if="!expiring"
            v-model="page"
            :length="Math.max(1, Math.ceil(total / size))"
            @update:model-value="load"
          />
        </div>
      </template>
    </v-data-table>
  </v-card>
</template>

