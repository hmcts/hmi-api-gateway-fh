terraform {
  backend "azurerm" {}

  required_version = ">= 1.0.7"
  required_providers {
    azurerm = ">= 2.42.0"
  }
}


provider "azurerm" {
  features {}
}
