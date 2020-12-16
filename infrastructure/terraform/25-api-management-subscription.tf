resource "azurerm_api_management_user" "hmi_apim_user" {
  api_management_name = azurerm_api_management.hmi_apim.name
  resource_group_name = azurerm_api_management.hmi_apim.resource_group_name
  user_id             = "5931a75ae4bbd512288c680b"
  first_name          = "Test"
  last_name           = "User"
  email               = "test@hmcts.dev.null"
  state               = "active"
}

resource "azurerm_api_management_subscription" "hmi_apim_subscription" {
  api_management_name = azurerm_api_management.hmi_apim.name
  resource_group_name = azurerm_api_management.hmi_apim.resource_group_name
  user_id             = azurerm_api_management_user.hmi_apim_user.id
  product_id          = azurerm_api_management_product.hmi_apim_product.id
  display_name        = "Test Subscription"
  state               = "active"
  allow_trace         = var.environment == "sbox" || var.environment == "dev" || var.environment == "test" ? true : false
}

resource "azurerm_key_vault_secret" "subscription_key" {
  name         = "${var.prefix}-${var.product}-sub-key"
  value        = azurerm_api_management_subscription.hmi_apim_subscription.primary_key
  key_vault_id = azurerm_key_vault.hmi_apim_kv.id
  tags         = var.tags
}
