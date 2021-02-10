resource "azurerm_key_vault" "hmi_apim_kv" {
  name                       = "${var.prefix}-${var.product}-kv-${var.environment}"
  location                   = var.location
  resource_group_name        = azurerm_resource_group.hmi_apim_rg.name
  tenant_id                  = var.tenant_id
  sku_name                   = var.kv_sku_name
  tags                       = var.tags
  soft_delete_enabled        = true
  soft_delete_retention_days = 7
}

resource "azurerm_key_vault_access_policy" "permissions" {
  key_vault_id            = azurerm_key_vault.hmi_apim_kv.id
  tenant_id               = azurerm_key_vault.hmi_apim_kv.tenant_id
  object_id               = var.sp_object_id
  certificate_permissions = var.certificate_permissions
  key_permissions         = var.key_permissions
  secret_permissions      = var.secret_permissions
  storage_permissions     = var.storage_permissions
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
