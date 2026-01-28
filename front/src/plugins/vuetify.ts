import { createVuetify } from 'vuetify'
import * as components from 'vuetify/components'
import * as directives from 'vuetify/directives'
import { md3 } from 'vuetify/blueprints'

const lightTheme = {
  dark: false,
  colors: {
    background: '#f1f5f9',
    surface: '#ffffff',
    primary: '#7c3aed',
    secondary: '#06b6d4',
    info: '#3b82f6',
    success: '#22c55e',
    warning: '#f59e0b',
    error: '#ef4444',
    'on-surface': '#0f172a',
    'on-background': '#0f172a',
  },
}

const darkTheme = {
  dark: true,
  colors: {
    background: '#0b1020',
    surface: '#0f172a',
    primary: '#a78bfa',
    secondary: '#22d3ee',
    info: '#60a5fa',
    success: '#34d399',
    warning: '#fbbf24',
    error: '#fb7185',
    'on-surface': '#e2e8f0',
    'on-background': '#e2e8f0',
  },
}

export const vuetify = createVuetify({
  components,
  directives,
  blueprint: md3,
  theme: {
    defaultTheme: 'darkTheme',
    themes: { lightTheme, darkTheme },
  },
  defaults: {
    VBtn: { variant: 'flat', rounded: 'lg' },
    VTextField: { variant: 'outlined', density: 'comfortable' },
    VSelect: { variant: 'outlined', density: 'comfortable' },
    VTextarea: { variant: 'outlined', density: 'comfortable' },
    VDataTable: { density: 'comfortable' },
    VCard: { rounded: 'xl' },
  },
})
