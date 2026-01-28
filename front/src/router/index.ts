import { createRouter, createWebHistory } from 'vue-router'
import { useAuthStore } from '../stores/auth'

const LoginView = () => import('../views/LoginView.vue')
const AppShell = () => import('../views/AppShell.vue')

const DashboardView = () => import('../views/DashboardView.vue')
const CompaniesView = () => import('../views/CompaniesView.vue')
const DepartmentsView = () => import('../views/DepartmentsView.vue')
const UsersView = () => import('../views/UsersView.vue')
const EquipmentView = () => import('../views/EquipmentView.vue')
const LicensesView = () => import('../views/LicensesView.vue')
const ReportsView = () => import('../views/ReportsView.vue')

export const router = createRouter({
  history: createWebHistory(),
  routes: [
    { path: '/login', name: 'login', component: LoginView },
    {
      path: '/',
      component: AppShell,
      children: [
        { path: '', name: 'dashboard', component: DashboardView },
        { path: 'companies', name: 'companies', component: CompaniesView },
        { path: 'departments', name: 'departments', component: DepartmentsView },
        { path: 'users', name: 'users', component: UsersView },
        { path: 'equipment', name: 'equipment', component: EquipmentView },
        { path: 'licenses', name: 'licenses', component: LicensesView },
        { path: 'reports', name: 'reports', component: ReportsView },
      ],
    },
  ],
})

router.beforeEach(async (to) => {
  const auth = useAuthStore()
  auth.hydrate()

  if (to.name === 'login') return true
  if (!auth.isAuthenticated) return { name: 'login' }
  return true
})

