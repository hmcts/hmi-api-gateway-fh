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
}
