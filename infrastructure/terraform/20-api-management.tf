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

  dynamic "virtual_network_configuration" {
    for_each = var.virtual_network_type == "None" ? [] : [data.azurerm_subnet.hmi_apim_subnet.id]

    content {
      subnet_id = data.azurerm_subnet.hmi_apim_subnet.id
    }
  }

  lifecycle {
    ignore_changes = [
      "hostname_configuration.proxy.default_ssl_binding",
      "hostname_configuration.proxy.host_name",
      "hostname_configuration.proxy.key_vault_id",
      "hostname_configuration.proxy.negotiate_client_certificate"
    ]
  }
}
