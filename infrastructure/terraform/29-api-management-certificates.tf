
resource "azurerm_api_management_certificate" "ISRG-Root-X1" {
  name                = "ISRG-Root-X1"
  api_management_name = azurerm_api_management.hmi_apim.name
  resource_group_name = azurerm_resource_group.hmi_apim_rg.name

  data = filebase64("certificates/isrgrootx1.cer")
}

resource "azurerm_api_management_certificate" "lets-encrypt-r3" {
  name                = "lets-encrypt-r3"
  api_management_name = azurerm_api_management.hmi_apim.name
  resource_group_name = azurerm_resource_group.hmi_apim_rg.name

  data = filebase64("certificates/lets-encrypt-r3.cer")
}