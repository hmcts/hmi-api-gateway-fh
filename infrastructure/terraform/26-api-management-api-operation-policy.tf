resource "azurerm_api_management_api_operation_policy" "example" {
  api_name            = azurerm_api_management_api.hmi_apim_api.name
  api_management_name = azurerm_api_management.hmi_apim.name
  resource_group_name = azurerm_resource_group.hmi_apim_rg.name
  operation_id        = "request-hearing"
  xml_content = file("../template/api-request-hearings-policy.xml")
}