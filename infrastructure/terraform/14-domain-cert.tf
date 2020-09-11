data "azurerm_key_vault" "cert_key_vault" {
  name                = var.key_vault_name
  resource_group_name = var.key_vault_rg
}

data "azurerm_key_vault_secret" "certificate_secret" {
  name         = var.certificate_name
  key_vault_id = data.azurerm_key_vault.cert_key_vault.id
}