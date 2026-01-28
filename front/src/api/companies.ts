import { http } from './http'
import type { CompanyResponseDto, SpringPage } from './types'

export type CreateCompanyDto = {
  name: string
  description?: string
  active?: boolean
}

export type UpdateCompanyDto = {
  name?: string
  description?: string
  active?: boolean
}

export async function getCompanies(params: { page?: number; size?: number } = {}) {
  const res = await http.get<SpringPage<CompanyResponseDto>>('/api/companies', { params })
  return res.data
}

export async function createCompany(dto: CreateCompanyDto) {
  const res = await http.post<CompanyResponseDto>('/api/companies', dto)
  return res.data
}

export async function updateCompany(id: number, dto: UpdateCompanyDto) {
  const res = await http.put<CompanyResponseDto>(`/api/companies/${id}`, dto)
  return res.data
}

export async function deleteCompany(id: number) {
  await http.delete(`/api/companies/${id}`)
}

