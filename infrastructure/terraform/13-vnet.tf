data "azurerm_virtual_network" "hmi_apim_vnet" {
  name                = "hmi-sharedinfra-vnet-${var.environment}"
  resource_group_name = "hmi-sharedinfra-${var.environment}-rg"
}

data "azurerm_subnet" "hmi_apim_subnet" {
  name                 = "apim-subnet-${var.environment}"
  virtual_network_name = data.azurerm_virtual_network.hmi_apim_vnet.name
  resource_group_name  = data.azurerm_virtual_network.hmi_apim_vnet.resource_group_name
}