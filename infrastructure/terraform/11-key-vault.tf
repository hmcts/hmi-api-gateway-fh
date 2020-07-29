data "azurerm_key_vault" "devops_keyvault" {
  name                = var.devops_keyvault
  resource_group_name = var.devops_resource_group
}