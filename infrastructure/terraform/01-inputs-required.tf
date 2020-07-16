variable "prefix" {}
variable "environment" {}
variable "location" {
  default = "uksouth"
  }
variable "sku_name" {
  description = "Desired SKU for the API Management"
}
variable "open_api_spec_content_format" {
  description = "The format of the content from which the API Definition should be imported. Possible values are: openapi, openapi+json, openapi+json-link, openapi-link, swagger-json, swagger-link-json, wadl-link-json, wadl-xml, wsdl and wsdl-link."
}
variable "open_api_spec_content_value" {
  description = "The Content from which the API Definition should be imported. When a content_format of *-link-* is specified this must be a URL, otherwise this must be defined inline."
}
variable "tags" {
  description = "The tags to associate with your resources."
  default     = {
      createdby = "HMI"
      purpose   = "PoC"
  }
}
variable "service_url" {}