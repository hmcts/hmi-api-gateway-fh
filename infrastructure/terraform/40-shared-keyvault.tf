data "azurerm_key_vault" "infra_key_vault" {
  name                = var.infra_kv_name
  resource_group_name = var.infra_kv_rg
}
