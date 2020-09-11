locals {
  hostname_configuration_proxy {
    default_ssl_binding = true
    host_name           = var.host_name
    key_vault_id        = var.key_vault_id
    certificate_name    = var.certificate_name
  }
}

data "azurerm_key_vault_secret" "certificate" {
  name         = var.hostname_configuration_proxy.certificate_name
  key_vault_id = var.hostname_configuration_proxy.key_vault_id
}