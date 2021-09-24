terraform {
  backend "azurerm" {}

  required_version = ">= 0.13.0"
  required_providers {
    azurerm = ">= 2.42.0"
  }
}


provider "azurerm" {
  features {}
}
