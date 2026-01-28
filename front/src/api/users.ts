import { http } from './http'
import type { SpringPage, UserResponseDto } from './types'

export async function getUsersByCompany(companyId: number, params: { page?: number; size?: number } = {}) {
  const res = await http.get<SpringPage<UserResponseDto>>(`/api/users/company/${companyId}`, { params })
  return res.data
}

