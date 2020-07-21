resource "azurerm_storage_account" "hmi_apim_storage" {
  name                     = "${var.prefix}${var.product}${var.environment}sa"
  location                 = var.location
  resource_group_name      = azurerm_resource_group.hmi_apim_rg.name  account_tier             = "Standard"
  account_replication_type = "GRS"
  tags                     = var.tags
}

resource "azurerm_storage_container" "hmi_apim_container" {
  name                  = "$web"
  storage_account_name  = azurerm_storage_account.hmi_apim_storage.name
  container_access_type = "private"
}

resource "azurerm_storage_share" "hmi_apim_fileshare" {
  name                 = "acishare"
  storage_account_name = azurerm_storage_account.example.name
  quota                = 1
}