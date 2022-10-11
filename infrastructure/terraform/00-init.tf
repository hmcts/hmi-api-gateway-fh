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

provider "azurerm" {
  alias = "control"
  features {}
  subscription_id = "04d27a32-7a07-48b3-95b8-3c8691e1a263"
}
