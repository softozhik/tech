import { defineStore } from 'pinia'

type UiState = {
  snack: { open: boolean; text: string }
}

export const useUiStore = defineStore('ui', {
  state: (): UiState => ({
    snack: { open: false, text: '' },
  }),
  actions: {
    notify(text: string) {
      this.snack = { open: true, text }
    },
    closeSnack() {
      this.snack.open = false
    },
  },
})

