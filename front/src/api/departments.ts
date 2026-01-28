import { http } from './http'
import type { DepartmentResponseDto, SpringPage } from './types'

export async function getDepartmentsByCompany(companyId: number, params: { page?: number; size?: number } = {}) {
  const res = await http.get<SpringPage<DepartmentResponseDto>>(`/api/departments/company/${companyId}`, { params })
  return res.data
}

