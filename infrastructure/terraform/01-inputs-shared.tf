variable "prefix" {}
variable "product" {}
variable "environment" {}
variable "location" {}
variable "tags" {}
variable "virtual_network_type" {
  description = "Network type: None / External / Internal"
  default     = "None"
}
variable "tenant_id" {
  description = "The Tenant ID for the principal we're giving permission to."
}
variable "sp_object_id" {
  description = "The Object ID for the principal we're giving permission to."
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