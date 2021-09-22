

data "azurerm_key_vault" "kv" {
  name                = "acmedtssds${var.environment}"
  resource_group_name = "sds-platform-${var.environment}-rg"
}
data "azurerm_key_vault_certificate" "cert" {
  name         = "hmi-apim.${local.env_long_name}.platform.hmcts.net"
  key_vault_id = data.azurerm_key_vault.kv.id
}

data "azurerm_api_management" "hmi_apim_svc" {
  name                = azurerm_api_management.hmi_apim.name
  resource_group_name = azurerm_resource_group.hmi_apim_rg.name
}
resource "azurerm_key_vault_access_policy" "policy" {
  key_vault_id            = data.azurerm_key_vault.kv.id
  tenant_id               = data.azurerm_client_config.current.tenant_id
  object_id               = data.azurerm_api_management.hmi_apim_svc.identity.0.principal_id
  key_permissions         = []
  secret_permissions      = ["Get", "Set", "List", "Delete"]
  certificate_permissions = []
  storage_permissions     = []
}

resource "azurerm_api_management_custom_domain" "custom_domain" {
  api_management_id = azurerm_api_management.hmi_apim.id

  proxy {
    host_name    = "hmi-apim.${var.environment}.platform.hmcts.net"
    key_vault_id = data.azurerm_key_vault_certificate.cert.secret_id
  }

  depends_on = [
    azurerm_key_vault_access_policy.policy
  ]
}