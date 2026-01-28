import { http } from './http'
import type { EquipmentResponseDto, SpringPage } from './types'

export async function getEquipment(params: {
  companyId?: number
  status?: string
  categoryId?: number
  page?: number
  size?: number
} = {}) {
  const res = await http.get<SpringPage<EquipmentResponseDto>>('/api/equipment', { params })
  return res.data
}

