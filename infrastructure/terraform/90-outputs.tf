output "gateway_url" {
  description = "The URL of the Gateway for the API Management Service"
  value       = azurerm_api_management.hmi_apim.gateway_url
}

output "apim_id" {
  value = azurerm_api_management.hmi_apim.id
}

output "subscription_key" {
  description = "Subscription Primary Key"
  value       = azurerm_api_management_subscription.hmi_apim_subscription.primary_key
  sensitive   = true
}

output "resource_group" {
  description = "APIM Resource Group"
  value       = azurerm_resource_group.hmi_apim_rg.name
}

output "storage_account" {
  description = "Storage Account for APIM"
  value       = azurerm_storage_account.hmi_apim_storage.name
  sensitive   = true
}
