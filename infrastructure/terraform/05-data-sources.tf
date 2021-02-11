data "azurerm_key_vault" "infra_key_vault" {
  name                = "hmi-shared-kv-${var.environment}"
  resource_group_name = "hmi-sharedservices-${var.environment}-rg"
}
