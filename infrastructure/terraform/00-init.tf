terraform {
  backend "azurerm" {}

<<<<<<< HEAD
  required_version = ">= 0.14.0"
=======
  required_version = ">= 1.0.4"
>>>>>>> master
  required_providers {
    azurerm = ">= 2.42.0"
  }
}


provider "azurerm" {
  features {}
}