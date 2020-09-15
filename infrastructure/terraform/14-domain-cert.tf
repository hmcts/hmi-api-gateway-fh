data "azurerm_key_vault" "cert_key_vault" {
  name                = var.key_vault_name
  resource_group_name = var.key_vault_rg
}

resource "azurerm_key_vault_access_policy" "permissions" {
  key_vault_id            = data.azurerm_key_vault.cert_key_vault.id
  tenant_id               = azurerm_key_vault.hmi_apim_kv.tenant_id
  object_id               = var.principal_object_id
  certificate_permissions = var.certificate_permissions
  key_permissions         = var.key_permissions
  secret_permissions      = var.secret_permissions
  storage_permissions     = var.storage_permissions
}

data "azurerm_key_vault_secret" "certificate_secret" {
  name         = var.certificate_name
  key_vault_id = data.azurerm_key_vault.cert_key_vault.id
}