<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '../stores/auth'
import { useUiStore } from '../stores/ui'
import { http } from '../api/http'
import type { VForm } from 'vuetify/components'

const router = useRouter()
const auth = useAuthStore()
const ui = useUiStore()

const username = ref(auth.username || 'admin@example.com')
const password = ref(auth.password || 'password')
const loading = ref(false)
const isFormValid = ref(false)
const form = ref<VForm | null>(null)

// Validation Rules
const requiredRule = (v: string) => !!v || 'Поле обязательно для заполнения'
const emailRule = (v: string) => /.+@.+\..+/.test(v) || 'E-mail должен быть валидным'

const usernameRules = [requiredRule, emailRule]
const passwordRules = [requiredRule]

async function login() {
  if (!isFormValid.value) {
    return
  }
  loading.value = true
  try {
    auth.setCredentials(username.value.trim(), password.value)
    // cheap auth check
    await http.get('/api/companies', { params: { page: 0, size: 1 } })
    await router.replace({ name: 'dashboard' })
  } catch (e) {
    auth.clear()
    // The error is already displayed by the interceptor
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  form.value?.validate()
})
</script>

<template>
  <div class="login-background">
    <v-container class="fill-height">
      <v-row align="center" justify="center">
        <v-col cols="12" sm="10" md="6" lg="4">
          <v-card class="pa-8" elevation="12" rounded="lg" theme="light">
            <div class="text-h4 font-weight-bold text-center mb-4">Tech Equipment</div>
            <div class="text-medium-emphasis text-center mb-8">Вход в систему</div>

            <v-form ref="form" v-model="isFormValid">
              <v-text-field
                v-model="username"
                :rules="usernameRules"
                label="Email"
                prepend-inner-icon="mdi-account-outline"
                variant="outlined"
                autocomplete="username"
                class="mb-4"
              />
              <v-text-field
                v-model="password"
                :rules="passwordRules"
                label="Пароль"
                type="password"
                prepend-inner-icon="mdi-lock-outline"
                variant="outlined"
                autocomplete="current-password"
                @keyup.enter="login"
              />

              <div class="text-center mt-4">
                <v-btn
                  size="large"
                  color="primary"
                  :loading="loading"
                  :disabled="!isFormValid || loading"
                  @click="login"
                >
                  Войти
                </v-btn>
              </div>
            </v-form>

            <div class="text-caption text-medium-emphasis text-center mt-6">
              Тестовые данные: <span class="font-weight-medium">admin@example.com / password</span>
            </div>
          </v-card>
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<style scoped>
.login-background {
  background: linear-gradient(to right, #6a11cb 0%, #2575fc 100%);
  min-height: 100vh;
}
</style>
