data "azurerm_user_assigned_identity" "hmi_apim_mi" {
  name                = "hmi-sharedinfra-mi-${var.environment}"
  resource_group_name = "hmi-sharedinfra-${var.environment}-rg"
}