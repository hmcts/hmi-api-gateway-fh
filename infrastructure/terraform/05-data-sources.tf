data "azurerm_key_vault" "infra_key_vault" {
  name                = "hmi-shared-kv-${var.environment}"
  resource_group_name = var.key_vault_rg
}
data "azurerm_user_assigned_identity" "mi" {
  resource_group_name = "managed-identities-${var.environment}-rg"
  name                = "hmi-${var.environment}-mi"
}