import { http } from './http'
import type { SoftwareLicenseResponseDto, SpringPage } from './types'

export async function getLicensesByCompany(companyId: number, params: { page?: number; size?: number } = {}) {
  const res = await http.get<SpringPage<SoftwareLicenseResponseDto>>(`/api/licenses/company/${companyId}`, { params })
  return res.data
}

export async function getExpiringLicenses(companyId: number, days = 30) {
  const res = await http.get<SoftwareLicenseResponseDto[]>(`/api/licenses/company/${companyId}/expiring`, {
    params: { days },
  })
  return res.data
}

