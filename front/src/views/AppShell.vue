<script setup lang="ts">
import { computed } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { useAuthStore } from '../stores/auth'
import { useUiStore } from '../stores/ui'
import { useTheme } from 'vuetify'

const router = useRouter()
const route = useRoute()
const auth = useAuthStore()
const ui = useUiStore()
const theme = useTheme()

const drawer = computed({
  get: () => true,
  set: () => {},
})

const isDark = computed(() => theme.global.name.value === 'darkTheme')

function toggleTheme() {
  theme.global.name.value = isDark.value ? 'lightTheme' : 'darkTheme'
}

async function logout() {
  auth.clear()
  await router.replace({ name: 'login' })
}

const items = [
  { title: 'Дашборд', icon: 'mdi-view-dashboard', to: { name: 'dashboard' } },
  { title: 'Компании', icon: 'mdi-domain', to: { name: 'companies' } },
  { title: 'Отделы', icon: 'mdi-sitemap', to: { name: 'departments' } },
  { title: 'Пользователи', icon: 'mdi-account-group', to: { name: 'users' } },
  { title: 'Техника', icon: 'mdi-laptop', to: { name: 'equipment' } },
  { title: 'Лицензии', icon: 'mdi-certificate', to: { name: 'licenses' } },
  { title: 'Отчеты', icon: 'mdi-chart-bar', to: { name: 'reports' } },
]
</script>

<template>
  <v-layout class="h-100">
    <v-navigation-drawer v-model="drawer" permanent width="280">
      <v-list>
        <v-list-item title="Tech Equipment" subtitle="Учет техники" />
      </v-list>
      <v-divider />
      <v-list nav>
        <v-list-item
          v-for="it in items"
          :key="it.title"
          :to="it.to"
          :title="it.title"
          :prepend-icon="it.icon"
          :active="route.name === it.to.name"
        />
      </v-list>
      <template #append>
        <v-divider />
        <div class="pa-3">
          <v-btn block variant="tonal" @click="toggleTheme">
            {{ isDark ? 'Светлая тема' : 'Тёмная тема' }}
          </v-btn>
          <v-btn block class="mt-2" color="error" variant="tonal" @click="logout">Выйти</v-btn>
        </div>
      </template>
    </v-navigation-drawer>

    <v-main>
      <v-container class="py-6" fluid>
        <router-view />
      </v-container>
    </v-main>

    <v-snackbar v-model="ui.snack.open" :timeout="3500" @update:model-value="(v) => !v && ui.closeSnack()">
      {{ ui.snack.text }}
    </v-snackbar>
  </v-layout>
</template>

