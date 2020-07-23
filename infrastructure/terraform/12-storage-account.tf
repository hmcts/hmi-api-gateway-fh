resource "azurerm_storage_account" "hmi_apim_storage" {
  name                     = "${var.prefix}${var.product}${var.environment}sa"
  location                 = var.location
  resource_group_name      = azurerm_resource_group.hmi_apim_rg.name
  account_tier             = "Standard"
  account_replication_type = "GRS"
  tags                     = var.tags

  static_website {
    index_document     = "index.html"
    error_404_document = null
  }
}

resource "azurerm_storage_share" "hmi_apim_fileshare" {
  name                 = "${var.prefix}${var.product}${var.environment}vol"
  storage_account_name = azurerm_storage_account.hmi_apim_storage.name
  quota                = 1
}