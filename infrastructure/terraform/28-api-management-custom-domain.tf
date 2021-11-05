
locals {
  env_long_name = var.environment == "sbox" ? "sandbox" : var.environment == "stg" ? "staging" : var.environment
  host_name     = local.env_long_name == "prod" ? "hmi-apim.platform.hmcts.net" : "hmi-apim.${local.env_long_name}.platform.hmcts.net"
  cert_name     = replace(local.host_name, ".", "-")
}

module "cert" {
  source        = "git::https://github.com/hmcts/terraform-module-certificate.git?ref=master"
  environment   = var.environment
  domain_prefix = "hmi-apim"
  object_id     = azurerm_api_management.hmi_apim.identity.0.principal_id
}

resource "azurerm_api_management_custom_domain" "custom_domain" {
  api_management_id = azurerm_api_management.hmi_apim.id

  proxy {
    host_name                    = local.host_name
    key_vault_id                 = module.cert.url
    negotiate_client_certificate = true
    default_ssl_binding          = true
  }

  depends_on = [
    module.cert
  ]
}