output "hmi_apim_svc_id" {
  description = "The ID of the API Management Service created"
  value       = azurerm_api_management.hmi_apim.id
}

output "hmi_apim_gateway_url" {
  description = "The URL of the Gateway for the API Management Service"
  value       = azurerm_api_management.hmi_apim.gateway_url
}

output "hmi_apim_svc_public_ip_addresses" {
  description = "The Public IP addresses of the API Management Service"
  value       = azurerm_api_management.hmi_apim.public_ip_addresses
}

output "api_outputs" {
  description = "The IDs, state, and version outputs of the APIs created"
  value = {
    id             = azurerm_api_management_api.hmi_apim_api.id
    is_current     = azurerm_api_management_api.hmi_apim_api.is_current
    is_online      = azurerm_api_management_api.hmi_apim_api.is_online
    version        = azurerm_api_management_api.hmi_apim_api.version
    version_set_id = azurerm_api_management_api.hmi_apim_api.version_set_id
  }
}

output "product_ids" {
  description = "The ID of the Product created"
  value = azurerm_api_management_product.hmi_apim_product.id
}

output "subscription_key" {
  description = "Subscription Primary Key"
  value       = azurerm_api_management_subscription.hmi_apim_subscription.primary_key
  sensitive   = true
}
