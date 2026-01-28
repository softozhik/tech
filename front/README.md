# Tech Equipment Frontend (Vue 3)

## Запуск

Требования: Node.js 20+.

```bash
cd front
npm install
npm run dev
```

По умолчанию `vite` проксирует `/api` на `http://localhost:8080` (см. `vite.config.ts`), так что бэкенд запускай как обычно из `tech-equipment`.

## Авторизация

Бэкенд использует **HTTP Basic Auth**:

- GET доступен всем аутентифицированным пользователям
- POST/PUT/DELETE требуют роль **ADMIN**

Во фронте логин — это сохранение `email/password` (в `localStorage`) и проверка через `GET /api/companies?page=0&size=1`.

## Темы (варианты)

Сейчас подключён **Vuetify 3** с двумя темами (`darkTheme` / `lightTheme`) и переключателем в меню.

Если захочешь другой стек темизации:

- **Vuetify 3**: богатая темизация, Material 3, быстро делать админки
- **PrimeVue** (+ Aura): много готовых компонентов, гибкие темы
- **Quasar**: “всё-в-одном”, крутые темы и layout, но более opinionated
- **Naive UI / Element Plus**: чисто компонентные библиотеки, лёгкая темизация
