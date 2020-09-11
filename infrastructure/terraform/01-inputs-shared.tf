variable "prefix" {}
variable "product" {}
variable "environment" {}
variable "location" {}
variable "tags" {}
variable "repo_branch" {}
variable "virtual_network_type" {
  description = "Network type: None / External / Internal"
  default     = null
}
variable "hostname_configuration_proxy" {
  description = "Custom domain configuration"
  default     = null
}