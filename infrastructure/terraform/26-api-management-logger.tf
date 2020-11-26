data "azurerm_key_vault_secret" "instrumentation_key" {
  name         = "appins-instrumentation-key"
  key_vault_id = data.azurerm_key_vault.infra_key_vault.id
}

resource "azurerm_api_management_logger" "hmi_apim_logger" {
  name                = "hmi-apim-logger-${var.environment}"
  resource_group_name = azurerm_resource_group.hmi_apim_rg.name
  api_management_name = azurerm_api_management.hmi_apim.name

  application_insights {
    instrumentation_key = data.azurerm_key_vault_secret.instrumentation_key.value
  }
}