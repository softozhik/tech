import { createApp } from 'vue'
import App from './App.vue'

import { createPinia } from 'pinia'
import { router } from './router'
import { vuetify } from './plugins/vuetify'

import '@mdi/font/css/materialdesignicons.css'
import 'vuetify/styles'

import './styles/main.css'
import './style.css'

createApp(App).use(createPinia()).use(router).use(vuetify).mount('#app')
