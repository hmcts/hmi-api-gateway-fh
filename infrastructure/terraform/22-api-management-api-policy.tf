resource "azurerm_api_management_api_policy" "hmi_apim_api_policy" {
  api_name            = azurerm_api_management_api.hmi_apim_api.name
  api_management_name = azurerm_api_management.hmi_apim.name
  resource_group_name = azurerm_resource_group.hmi_apim_rg.name

  xml_content = file("../template/api-policy.xml")
}
