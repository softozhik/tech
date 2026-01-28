export type SpringPage<T> = {
  content: T[]
  totalElements: number
  totalPages: number
  size: number
  number: number
  numberOfElements: number
  first: boolean
  last: boolean
  empty: boolean
}

export type CompanyResponseDto = {
  id: number
  name: string
  description?: string | null
  active?: boolean | null
  createdAt?: string
  updatedAt?: string
}

export type DepartmentResponseDto = {
  id: number
  companyId: number
  companyName?: string
  name: string
  parentDepartmentId?: number | null
  parentDepartmentName?: string | null
  description?: string | null
  active?: boolean | null
  createdAt?: string
  updatedAt?: string
}

export type UserRole = 'ADMIN' | 'EMPLOYEE'
export type UserStatus = 'ACTIVE' | 'INACTIVE' | 'SUSPENDED'

export type UserResponseDto = {
  id: number
  companyId: number
  companyName?: string
  departmentId?: number | null
  departmentName?: string | null
  email: string
  fullName: string
  role: UserRole
  status: UserStatus
  createdAt?: string
  updatedAt?: string
}

export type EquipmentStatus = 'AVAILABLE' | 'IN_USE' | 'IN_REPAIR' | 'RETIRED' | 'RESERVED'
export type ConditionStatus = 'EXCELLENT' | 'GOOD' | 'FAIR' | 'POOR' | 'BROKEN'

export type EquipmentResponseDto = {
  id: number
  companyId: number
  companyName?: string
  categoryId: number
  categoryName?: string
  currentUserId?: number | null
  currentUserName?: string | null
  serialNumber: string
  inventoryNumber?: string | null
  manufacturer?: string | null
  model: string
  specifications?: string | null
  purchaseDate?: string | null
  purchasePrice?: string | null
  warrantyUntil?: string | null
  status?: EquipmentStatus | null
  conditionStatus?: ConditionStatus | null
  notes?: string | null
  createdAt?: string
  updatedAt?: string
}

export type LicenseStatus = 'ACTIVE' | 'EXPIRED' | 'SUSPENDED' | 'CANCELLED'

export type SoftwareLicenseResponseDto = {
  id: number
  companyId: number
  companyName?: string
  softwareName: string
  softwareVendor?: string | null
  version?: string | null
  licenseKey?: string | null
  licenseType?: string | null
  purchaseDate?: string | null
  purchasePrice?: string | null
  expiryDate?: string | null
  seatsTotal?: number | null
  seatsUsed?: number | null
  autoRenewal?: boolean | null
  status?: LicenseStatus | null
  notes?: string | null
  createdAt?: string
  updatedAt?: string
}

