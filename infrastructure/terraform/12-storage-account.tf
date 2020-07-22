resource "azurerm_storage_account" "hmi_apim_storage" {
  name                     = var.storageaccount
  location                 = var.location
  resource_group_name      = azurerm_resource_group.hmi_apim_rg.name
  account_tier             = "Standard"
  account_replication_type = "GRS"
  account_kind             = "StorageV2"
  tags                     = var.tags

  static_website {
    index_document     = "index.html"
    error_404_document = null
  }
}

resource "azurerm_storage_share" "hmi_apim_fileshare" {
  name                 = var.storagevolume
  storage_account_name = azurerm_storage_account.hmi_apim_storage.name
  quota                = 1
}