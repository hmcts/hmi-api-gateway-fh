resource "azurerm_resource_group" "hmi_apim_rg" {
  name     = "${var.prefix}-${var.product}-${var.environment}-rg"
  location = var.location
  tags     = local.common_tags
}
