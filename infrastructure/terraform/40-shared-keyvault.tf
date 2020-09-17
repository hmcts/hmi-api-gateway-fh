data "azurerm_key_vault" "infra_key_vault" {
  name                = var.infra_kv_name
  resource_group_name = var.infra_kv_rg
}

resource "azurerm_key_vault_access_policy" "infra_kv_permissions" {
  key_vault_id            = data.azurerm_key_vault.infra_key_vault.id
  tenant_id               = azurerm_key_vault.hmi_apim_kv.tenant_id
  object_id               = azurerm_api_management.hmi_apim.identity.*.principal_id
  certificate_permissions = var.certificate_permissions
  key_permissions         = var.key_permissions
  secret_permissions      = var.secret_permissions
  storage_permissions     = var.storage_permissions
}