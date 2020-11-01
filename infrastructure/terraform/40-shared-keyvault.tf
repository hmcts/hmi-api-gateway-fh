data "azurerm_key_vault" "infra_key_vault" {
  name                = "hmi-shared-kv-${var.environment}"
  resource_group_name = "hmi-sharedservices-${var.environment}-rg"
}

resource "azurerm_key_vault_access_policy" "shared_kv_premissions" {
  key_vault_id            = data.azurerm_key_vault.infra_key_vault.id
  tenant_id               = azurerm_key_vault.hmi_apim_kv.tenant_id
  object_id               = azurerm_api_management.hmi_apim.identity[0].principal_id
  certificate_permissions = var.certificate_permissions
  key_permissions         = var.key_permissions
  secret_permissions      = var.secret_permissions
  storage_permissions     = var.storage_permissions
}
