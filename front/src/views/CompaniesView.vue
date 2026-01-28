<script setup lang="ts">
import { computed, onMounted, reactive, ref } from 'vue'
import { createCompany, deleteCompany, getCompanies, updateCompany } from '../api/companies'
import type { CompanyResponseDto } from '../api/types'
import { useUiStore } from '../stores/ui'

const ui = useUiStore()

const loading = ref(false)
const items = ref<CompanyResponseDto[]>([])
const total = ref(0)

const page = ref(1) // Vuetify is 1-based
const size = ref(10)

const dialog = ref(false)
const editing = ref<CompanyResponseDto | null>(null)

const form = reactive({
  name: '',
  description: '',
  active: true,
})

const title = computed(() => (editing.value ? 'Редактировать компанию' : 'Создать компанию'))

function openCreate() {
  editing.value = null
  form.name = ''
  form.description = ''
  form.active = true
  dialog.value = true
}

function openEdit(c: CompanyResponseDto) {
  editing.value = c
  form.name = c.name
  form.description = c.description ?? ''
  form.active = c.active ?? true
  dialog.value = true
}

async function load() {
  loading.value = true
  try {
    const res = await getCompanies({ page: page.value - 1, size: size.value })
    items.value = res.content
    total.value = res.totalElements
  } finally {
    loading.value = false
  }
}

async function save() {
  if (!form.name.trim()) {
    ui.notify('Название обязательно')
    return
  }

  loading.value = true
  try {
    if (editing.value) {
      await updateCompany(editing.value.id, {
        name: form.name.trim(),
        description: form.description.trim() || undefined,
        active: form.active,
      })
      ui.notify('Компания обновлена')
    } else {
      await createCompany({
        name: form.name.trim(),
        description: form.description.trim() || undefined,
        active: form.active,
      })
      ui.notify('Компания создана')
    }
    dialog.value = false
    await load()
  } finally {
    loading.value = false
  }
}

async function remove(c: CompanyResponseDto) {
  if (!confirm(`Удалить компанию "${c.name}"?`)) return
  loading.value = true
  try {
    await deleteCompany(c.id)
    ui.notify('Удалено')
    await load()
  } finally {
    loading.value = false
  }
}

onMounted(load)
</script>

<template>
  <div class="d-flex align-center justify-space-between mb-6">
    <div>
      <div class="text-h5 font-weight-bold">Компании</div>
      <div class="text-medium-emphasis">CRUD через `/api/companies`</div>
    </div>
    <v-btn color="primary" prepend-icon="mdi-plus" :disabled="loading" @click="openCreate">
      Создать
    </v-btn>
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
          <th class="text-left">Активна</th>
          <th class="text-left">Описание</th>
          <th class="text-right">Действия</th>
        </tr>
      </template>

      <template #item="{ item }">
        <tr>
          <td class="text-medium-emphasis">{{ item.id }}</td>
          <td class="font-weight-medium">{{ item.name }}</td>
          <td>
            <v-chip size="small" :color="item.active ? 'success' : 'warning'" variant="tonal">
              {{ item.active ? 'Да' : 'Нет' }}
            </v-chip>
          </td>
          <td class="text-medium-emphasis">{{ item.description || '—' }}</td>
          <td class="text-right">
            <v-btn size="small" variant="text" icon="mdi-pencil" @click="openEdit(item)" />
            <v-btn size="small" variant="text" color="error" icon="mdi-delete" @click="remove(item)" />
          </td>
        </tr>
      </template>

      <template #bottom>
        <div class="d-flex align-center justify-end pa-3">
          <v-pagination v-model="page" :length="Math.max(1, Math.ceil(total / size))" @update:model-value="load" />
        </div>
      </template>
    </v-data-table>
  </v-card>

  <v-dialog v-model="dialog" max-width="720">
    <v-card class="pa-6" elevation="12">
      <div class="d-flex align-center justify-space-between mb-4">
        <div class="text-h6 font-weight-bold">{{ title }}</div>
        <v-btn icon="mdi-close" variant="text" @click="dialog = false" />
      </div>

      <v-row>
        <v-col cols="12">
          <v-text-field v-model="form.name" label="Название" />
        </v-col>
        <v-col cols="12">
          <v-textarea v-model="form.description" label="Описание" rows="3" />
        </v-col>
        <v-col cols="12">
          <v-switch v-model="form.active" inset color="success" label="Активная" />
        </v-col>
      </v-row>

      <div class="d-flex justify-end ga-2 mt-2">
        <v-btn variant="tonal" @click="dialog = false">Отмена</v-btn>
        <v-btn color="primary" :loading="loading" @click="save">Сохранить</v-btn>
      </div>
    </v-card>
  </v-dialog>
</template>

