

resource "azurerm_api_management_certificate" "this" {
  for_each            = var.custom_certificates
  name                = each.value.id
  api_management_name = azurerm_api_management.hmi_apim.name
  resource_group_name = azurerm_resource_group.hmi_apim_rg.name
  data                = filebase64("certificates/${each.value.certificate_filename}")
  password            = each.value.certificate_password
}