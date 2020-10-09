resource "azurerm_api_management" "hmi_apim" {
  name                = "${var.prefix}-${var.product}-svc-${var.environment}"
  location            = azurerm_resource_group.hmi_apim_rg.location
  resource_group_name = azurerm_resource_group.hmi_apim_rg.name
  publisher_name      = var.publisher_name
  publisher_email     = var.publisher_email
  sku_name            = "${var.apim_sku_name}_${var.apim_sku_capacity}"
  tags                = var.tags

  identity {
    type = "SystemAssigned"
  }

  virtual_network_type = var.virtual_network_type
  virtual_network_configuration {
    subnet_id = var.environment == "sbox" ? data.azurerm_subnet.hmi_apim_subnet.id : 0
  }

}
