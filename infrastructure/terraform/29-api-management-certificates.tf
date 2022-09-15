

resource "azurerm_api_management_certificate" "this" {
  for_each            = var.custom_certificates
  name                = each.value.id
  api_management_name = azurerm_api_management.hmi_apim.name
  resource_group_name = azurerm_resource_group.hmi_apim_rg.name
  data                = filebase64("certificates/${each.value.certificate_filename}")
  password            = each.value.certificate_password
}

resource "azurerm_key_vault_secret" "apim_cert" {
  for_each     = var.custom_certificates
  name         = "apim-cert-${each.value.id}"
  value        = each.value.certificate_password
  key_vault_id = data.azurerm_key_vault.infra_key_vault.id
  tags         = local.common_tags
}
