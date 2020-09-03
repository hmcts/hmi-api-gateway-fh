resource "azurerm_storage_account" "hmi_apim_storage" {
  name                     = "${var.prefix}${var.product}${var.environment}sa"
  location                 = var.location
  resource_group_name      = azurerm_resource_group.hmi_apim_rg.name
  account_tier             = "Standard"
  account_replication_type = "GRS"
  tags                     = var.tags
}