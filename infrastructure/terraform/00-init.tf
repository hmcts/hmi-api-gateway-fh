terraform {
  required_version = ">= 0.12.0"
  backend "azurerm" {}
}

provider "azurerm" {
  version = "2.37.0"
  features {}
}