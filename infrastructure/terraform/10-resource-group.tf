resource "azurerm_resource_group" "hmi_apim_rg" {
  name     = "${var.prefix}-${var.product}-${var.environment}-rg"
  location = var.location
  tags     = var.tags
}

resource "azurerm_management_lock" "hmi_apim_rg_lock" {
  name       = "resource-group-level-lock"
  scope      = azurerm_resource_group.hmi_apim_rg.id
  lock_level = var.environment == "stg" || var.environment == "prod" ? "CanNotDelete" : null
  notes      = "This Resource Group Can Not Be Deleted"
  count      = var.environment == "stg" || var.environment == "prod" ? 1 : 0
}