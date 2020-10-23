data "azurerm_subscription" "current" {
}

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

}

resource "null_resource" "clean-apim-api" {
  provisioner "local-exec" {
    command = "az rest -m delete -u \"https://management.azure.com/subscriptions/${data.azurerm_subscription.current.id}/resourceGroups/${azurerm_resource_group.hmi_apim_rg.name}/providers/Microsoft.ApiManagement/service/${azurerm_api_management.hmi_apim.name}/apis/echo-api?api-version=2019-01-01\""
  }
  depends_on = ["azurerm_api_management.apim"]
}

resource "null_resource" "clean-apim-product-starter" {
  provisioner "local-exec" {
    command = "az rest -m delete -u \"https://management.azure.com/subscriptions/${data.azurerm_subscription.current.id}/resourceGroups/${azurerm_resource_group.hmi_apim_rg.name}/providers/Microsoft.ApiManagement/service/${azurerm_api_management.hmi_apim.name}/products/Starter?api-version=2019-01-01\""
  }
  depends_on = ["azurerm_api_management.apim"]
}

resource "null_resource" "clean-apim-product-unlimited" {
  provisioner "local-exec" {
    command = "az rest -m delete -u \"https://management.azure.com/subscriptions/${data.azurerm_subscription.current.id}/resourceGroups/${azurerm_resource_group.hmi_apim_rg.name}/providers/Microsoft.ApiManagement/service/${azurerm_api_management.hmi_apim.name}/products/Unlimited?api-version=2019-01-01\""
  }
  depends_on = ["azurerm_api_management.apim"]
}