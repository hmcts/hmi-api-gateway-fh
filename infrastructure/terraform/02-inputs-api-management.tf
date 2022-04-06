variable "publisher_name" {
  description = "The name of publisher/company."
}
variable "publisher_email" {
  description = "The email of publisher/company."
}
variable "apim_sku_name" {
  description = "Desired SKU for the API Management"
}
variable "apim_sku_capacity" {
  description = "Desired number of Units of SKU for the API Management"
}
variable "protocols" {
  description = "A list of protocols the operations in this API can be invoked. Possible values are http and https."
}
variable "revision" {
  description = "The Revision which used for this API."
}
variable "open_api_spec_content_format" {
  description = "The format of the content from which the API Definition should be imported. Possible values are: openapi, openapi+json, openapi+json-link, openapi-link, swagger-json, swagger-link-json, wadl-link-json, wadl-xml, wsdl and wsdl-link."
}
variable "open_api_health_spec_content_value" {
  description = "The format of the content from which the Health Check API Definition should be imported. Possible values are: openapi, openapi+json, openapi+json-link, openapi-link, swagger-json, swagger-link-json, wadl-link-json, wadl-xml, wsdl and wsdl-link."
}
variable "open_api_spec_content_value" {
  description = "The Content from which the API Definition should be imported. When a content_format of *-link-* is specified this must be a URL, otherwise this must be defined inline."
}
variable "service_url" {
  description = "Absolute URL of the backend service implementing this API."
}
variable "enable_mock_header_string" {
  description = "mock header static string for adding development headers injected to requests"
}

variable key_vault_rg {
  description = "group name for KV"
}
