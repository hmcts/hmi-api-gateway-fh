resource "azurerm_resource_group" "hmi_apim_rg" {
    name     = "${var.prefix}-${var.environment}-rg"
    location = var.location
    tags     = var.tags
}