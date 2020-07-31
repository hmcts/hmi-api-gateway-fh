# resource "azurerm_api_management_api_operation" "apim_operation" {
#     api_name            = azurerm_api_management_api.hmi_apim_api.name
#     api_management_name = azurerm_api_management.hmi_apim.name
#     resource_group_name = azurerm_resource_group.hmi_apim_rg.name
#     count               = length(var.api_policies)
#     operation_id        = lookup(var.api_policies[count.index], "operationId")
#     display_name        = lookup(var.api_policies[count.index], "operationName")
#     method              = lookup(var.api_policies[count.index], "method")
#     url_template        = lookup(var.api_policies[count.index], "urlTemplate")
# }

resource "azurerm_template_deployment" "apim-policy" {
    name                = "apim-policy-${format("%02d",count.index)}"
    resource_group_name = azurerm_resource_group.hmi_apim_rg.name
    depends_on          = [azurerm_api_management_api.hmi_apim_api]
    deployment_mode 	= "Incremental"
    count               = length(var.api_policies)
    parameters          = {
        apimServiceName = azurerm_api_management.hmi_apim.name
        apiName         = azurerm_api_management_api.hmi_apim_api.name
        operationId     = lookup(var.api_policies[count.index], "operationId")
        format          = lookup(var.api_policies[count.index], "format")
        templateFile    = lookup(var.api_policies[count.index], "templateFile")
    	}
    template_body 	    = <<DEPLOY
{
    "$schema": "https://schema.management.azure.com/schemas/2015-01-01/deploymentTemplate.json#",
    "contentVersion": "1.0.0.0",
    "parameters": {
        "apiName": {
            "type": "String"
        },
        "apimServiceName": {
            "type": "String"
        },
        "operationId": {
            "type": "String"
        },
        "templateFile": {
            "type": "String"
        },
        "format": {
            "type": "String"
        },
        "repoBaseUrl": {
            "type": "String",
            "defaultValue": "https://raw.githubusercontent.com/hmcts/hmi-api-gateway-fh/master/infrastructure/template/"
        }
    },
    "variables": {
        "operationName": "[concat(parameters('apimServiceName'), '/', parameters('apiName'), '/', parameters('operationId'))]"
    },
    "resources": [
        {
            "type": "Microsoft.ApiManagement/service/apis/operations/policies",
            "apiVersion": "2019-12-01",
            "name": "[concat(parameters('apimServiceName'), '/', parameters('apiName'), '/', parameters('operationId'), '/policy')]",
            "properties": {
                "format": "[parameters('format')]",
                "value": "[concat(parameters('repoBaseUrl'), parameters('templateFile'))]"
            }
        }
    ],
    "outputs": {}
}
DEPLOY
}
