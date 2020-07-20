variable "prefix" {}
variable "product" {}
variable "environment" {}
variable "location" {
  default = "uksouth"
}
variable "tags" {
  description = "The tags to associate with your resources."
  default = {
    createdby = "HMI"
    purpose   = "PoC"
  }
}