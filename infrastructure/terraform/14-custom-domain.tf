locals {
  hostname_configuration_proxy = {
    default_ssl_binding = true
    host_name           = var.host_name
    certificate_name    = var.certificate_name
  }
}

data "azurerm_key_vault" "cert_key_vault" {
  name                = var.key_vault_name
  resource_group_name = var.key_vault_rg
}

data "azurerm_key_vault_secret" "certificate" {
  name         = local.hostname_configuration_proxy.certificate_name
  key_vault_id = data.azurerm_key_vault.cert_key_vault.id
}
