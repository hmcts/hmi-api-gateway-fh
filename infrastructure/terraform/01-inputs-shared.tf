variable "prefix" {}
variable "product" {}
variable "environment" {}
variable "location" {}
variable "builtFrom" {}
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
    "Get",
    "Set",
    "List",
    "Delete"
  ]
}

locals {
  common_tags = module.ctags.common_tags
}
module "ctags" {
  source      = "git::https://github.com/hmcts/terraform-module-common-tags.git?ref=master"
  environment = var.environment
  product     = var.prefix
  builtFrom   = var.builtFrom
}

data "azurerm_client_config" "current" {}


variable "custom_certificates" {
  type = map(object({
    id                   = string
    certificate_filename = string
    certificate_password = string
  }))
  description = "Custom Certificates to upload to HMI"
  default     = {}
}