resource "azurerm_api_management_api" "hmi_apim_api" {
  name                = "${var.prefix}-${var.product}-api"
  resource_group_name = azurerm_resource_group.hmi_apim_rg.name
  api_management_name = azurerm_api_management.hmi_apim.name
  revision            = var.revision
  display_name        = "${var.prefix}-${var.product}-api"
  path                = var.prefix
  protocols           = var.protocols

  service_url = var.service_url

  subscription_required = false

  import {
    content_format = var.open_api_spec_content_format
    content_value  = var.open_api_spec_content_value
  }
}

resource "azurerm_api_management_api" "hmi_apim_api_health" {
  name                = "${var.prefix}-${var.product}-api-health"
  resource_group_name = azurerm_resource_group.hmi_apim_rg.name
  api_management_name = azurerm_api_management.hmi_apim.name
  revision            = var.revision
  display_name        = "${var.prefix}-${var.product}-api-health"
  path                = "health"
  protocols           = var.protocols

  subscription_required = false

  import {
    content_format = var.open_api_spec_content_format
    content_value  = var.open_api_spec_content_value
  }
}
