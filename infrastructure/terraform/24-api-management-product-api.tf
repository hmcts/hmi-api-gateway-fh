resource "azurerm_api_management_product_api" "hmi_apim_product_api" {
  api_name            = azurerm_api_management_api.hmi_apim_api.name
  product_id          = azurerm_api_management_product.hmi_apim_product.product_id 
  api_management_name = azurerm_api_management.hmi_apim.name
  resource_group_name = azurerm_resource_group.hmi_apim_rg.name
}
