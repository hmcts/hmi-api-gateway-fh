variable "prefix" {}
variable "product" {}
variable "environment" {}
variable "location" {}
variable "tags" {
  description = "The tags to associate with your resources."
  default = {
    createdby   = "HMI"
    managedby   = "HMI"
    application = "Azure APIM Management"
  }
}