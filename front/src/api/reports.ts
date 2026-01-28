import { http } from './http'

export async function reportEquipmentByDepartment(companyId: number) {
  const res = await http.get<Record<string, number>>('/api/reports/equipment-by-department', { params: { companyId } })
  return res.data
}

export async function reportLicensesExpiring(companyId: number, days = 30) {
  const res = await http.get<Array<Record<string, unknown>>>('/api/reports/licenses-expiring', {
    params: { companyId, days },
  })
  return res.data
}

