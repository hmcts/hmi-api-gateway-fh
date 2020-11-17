variable "prefix" {}
variable "product" {}
variable "environment" {}
variable "location" {}
variable "tags" {}
variable "build_id" {}
variable "virtual_network_type" {
  description = "Network type: None / External / Internal"
  default     = "None"
}
