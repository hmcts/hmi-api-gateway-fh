resource "azurerm_api_management" "hmi_apim" {
  name                = "${var.prefix}-${var.product}-svc-${var.environment}"
  location            = azurerm_resource_group.hmi_apim_rg.location
  resource_group_name = azurerm_resource_group.hmi_apim_rg.name
  publisher_name      = var.publisher_name
  publisher_email     = var.publisher_email
  sku_name            = "${var.apim_sku_name}_${var.apim_sku_capacity}"
  tags                = local.common_tags

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

  certificate {
    encoded_certificate = filebase64("certificates/isrgrootx1.cer")
    store_name          = "Root"
  }
  certificate {
    encoded_certificate = filebase64("certificates/lets-encrypt-r3.cer")
    store_name          = "CertificateAuthority"
  }
  depends_on = [
    module.cert
  ]

}
