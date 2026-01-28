import axios from 'axios'
import { useAuthStore } from '../stores/auth'
import { useUiStore } from '../stores/ui'

export const http = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL || '',
})

http.interceptors.request.use((config) => {
  const auth = useAuthStore()
  auth.hydrate()
  const header = auth.basicAuthHeader
  if (header) config.headers.Authorization = header
  return config
})

http.interceptors.response.use(
  (r) => r,
  (error) => {
    const ui = useUiStore()
    const status = error?.response?.status as number | undefined
    const message =
      error?.response?.data?.message ||
      error?.response?.data?.error ||
      error?.message ||
      'Ошибка запроса'

    if (status === 401) {
      ui.notify('Нужна авторизация (401)')
    } else if (status === 403) {
      ui.notify('Недостаточно прав (403). Для POST/PUT/DELETE нужна роль ADMIN.')
    } else {
      ui.notify(String(message))
    }
    return Promise.reject(error)
  },
)

