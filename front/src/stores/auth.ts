import { defineStore } from 'pinia'

type AuthState = {
  username: string
  password: string
  hydrated: boolean
}

const STORAGE_KEY = 'tech-equipment.basicAuth'

export const useAuthStore = defineStore('auth', {
  state: (): AuthState => ({
    username: '',
    password: '',
    hydrated: false,
  }),
  getters: {
    isAuthenticated: (s) => !!s.username && !!s.password,
    basicAuthHeader: (s) => {
      if (!s.username || !s.password) return ''
      const token = btoa(`${s.username}:${s.password}`)
      return `Basic ${token}`
    },
  },
  actions: {
    hydrate() {
      if (this.hydrated) return
      try {
        const raw = localStorage.getItem(STORAGE_KEY)
        if (raw) {
          const parsed = JSON.parse(raw) as { username?: string; password?: string }
          this.username = parsed.username ?? ''
          this.password = parsed.password ?? ''
        }
      } catch {
        // ignore
      } finally {
        this.hydrated = true
      }
    },
    setCredentials(username: string, password: string) {
      this.username = username
      this.password = password
      localStorage.setItem(STORAGE_KEY, JSON.stringify({ username, password }))
    },
    clear() {
      this.username = ''
      this.password = ''
      localStorage.removeItem(STORAGE_KEY)
    },
  },
})

