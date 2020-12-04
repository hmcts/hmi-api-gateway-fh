resource "azurerm_resource_group" "hmi_apim_rg" {
  name     = "${var.prefix}-${var.product}-${var.environment}-rg"
  location = var.location
  tags     = var.tags
}

resource "azurerm_management_lock" "hmi_apim_rg_lock" {
  name       = "resource-group-level-lock"
  scope      = azurerm_resource_group.hmi_apim_rg.id
  lock_level = null
  notes      = "This Resource Group Can Not Be Deleted"
}