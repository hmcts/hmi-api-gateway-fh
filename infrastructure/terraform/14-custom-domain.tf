data "azurerm_key_vault" "cert_key_vault" {
  name                = var.key_vault_name
  resource_group_name = var.key_vault_rg
}
