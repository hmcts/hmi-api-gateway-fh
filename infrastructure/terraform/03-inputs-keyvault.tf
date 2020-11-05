variable "tenant_id" {
  description = "The Tenant ID for the principal we're giving permission to."
}
variable "sp_object_id" {
  description = "The Object ID for the principal we're giving permission to."
}
variable "kv_sku_name" {
  description = "The SKU for the KeyVault."
  default     = "standard"
}
variable "secret_permissions" {
  description = "The permissions (list) for the creating principal accessing secrets."
  default = [
    "get",
    "set",
    "list",
    "delete"
  ]
}
variable "certificate_permissions" {
  description = "The permissions (list) for the creating principal accessing certificates."
  default = [
    "get",
    "list"
  ]
}
variable "key_permissions" {
  description = "The permissions (list) for the creating principal accessing keys."
  default = [
    "get",
    "list",
    "create"
  ]
}
variable "storage_permissions" {
  description = "The permissions (list) for the creating principal accessing storage."
  default = [
    "get",
    "list",
    "set"
  ]
}
