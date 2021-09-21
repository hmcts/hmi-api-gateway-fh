

data "azurerm_key_vault" "kv" {
  provider            = azurerm.cft_platform
  name                = "acmedcdcftapps${var.environment}"
  resource_group_name = "cft-platform-${var.environment}-rg"
}
data "azurerm_key_vault_certificate" "cert" {
  provider     = azurerm.cft_platform
  name         = "hmi-apim.${local.env_long_name}.platform.hmcts.net"
  key_vault_id = data.azurerm_key_vault.kv.id
}

resource "azurerm_api_management_custom_domain" "custom_domain" {
  api_management_id = azurerm_api_management.hmi_apim.id

  proxy {
    host_name    = "hmi-apim.${var.environment}.platform.hmcts.net"
    key_vault_id = data.azurerm_key_vault_certificate.cert.secret_id
  }
}