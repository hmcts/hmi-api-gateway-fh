data "azurerm_key_vault" "infra_key_vault" {
  name                = "hmi-shared-kv-${var.environment}"
  resource_group_name = "hmi-sharedservices-${var.environment}-rg"
}

data "azurerm_key_vault_secret" "certificate_secret" {
  name         = "apim-hostname-certificate"
  key_vault_id = data.azurerm_key_vault.infra_key_vault.id
}

resource "azurerm_api_management" "hmi_apim" {
  name                = "${var.prefix}-${var.product}-svc-${var.environment}"
  location            = azurerm_resource_group.hmi_apim_rg.location
  resource_group_name = azurerm_resource_group.hmi_apim_rg.name
  publisher_name      = var.publisher_name
  publisher_email     = var.publisher_email
  sku_name            = "${var.apim_sku_name}_${var.apim_sku_capacity}"
  tags                = var.tags

  identity {
    type = "SystemAssigned"
  }

  virtual_network_type = var.virtual_network_type

  dynamic "virtual_network_configuration" {
    for_each = var.virtual_network_type == "None" ? [] : [data.azurerm_subnet.hmi_apim_subnet.id]

    content {
      subnet_id = data.azurerm_subnet.hmi_apim_subnet.id
    }
  }

  hostname_configuration {
    proxy {
      default_ssl_binding = true
      host_name           = var.hostname
      key_vault_id        = replace(data.azurerm_key_vault_secret.certificate_secret.id, "/${data.azurerm_key_vault_secret.certificate_secret.version}", "")
    }
  }
}

resource "azurerm_key_vault_access_policy" "shared_kv_premissions" {
  key_vault_id            = data.azurerm_key_vault.infra_key_vault.id
  tenant_id               = azurerm_key_vault.hmi_apim_kv.tenant_id
  object_id               = azurerm_api_management.hmi_apim.identity[0].principal_id
  certificate_permissions = var.certificate_permissions
  key_permissions         = var.key_permissions
  secret_permissions      = var.secret_permissions
  storage_permissions     = var.storage_permissions
}