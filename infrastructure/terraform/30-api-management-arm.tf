resource "azurerm_template_deployment" "apim-policy" {
    name                = "apim-policy-${format("%02d",count.index)}"
    resource_group_name = azurerm_resource_group.hmi_apim_rg.name
    depends_on          = [azurerm_api_management_api.hmi_apim_api]
    deployment_mode 	= "Incremental"
    count               = length(var.api_policies)
    parameters          = {
        apimServiceName = azurerm_api_management.hmi_apim.name
        apiName         = azurerm_api_management_api.hmi_apim_api.name
        operationId     = "${lookup(var.api_policies[count.index], "operationId")}"
        method          = "${lookup(var.api_policies[count.index], "method")}"
        format          = "${lookup(var.api_policies[count.index], "format")}"
        urlTemplate     = "${lookup(var.api_policies[count.index], "urlTemplate")}"
        templateFile    = "${lookup(var.api_policies[count.index], "templateFile")}"
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
        "method": {
            "type": "String"
        },
        "format": {
            "type": "String"
        },
        "urlTemplate": {
            "type": "String"
        },
        "templateFile": {
            "type": "String"
        },
        "repoBaseUrl": {
            "type": "string",
            "value": "https://github.com/hmcts/hmi-api-gateway-fh/tree/master/infrastructure/template/"
            }
        }
    },
    "resources": [
        {
            "type": "Microsoft.ApiManagement/service/apis/operations",
            "apiVersion": "2019-12-01",
            "name": "[concat(parameters('apimServiceName'), '/', parameters('apiName'), '/', parameters('operationId'))]",
            "properties": {
                "displayName": "[parameters('operationId')]",
                "method": "[parameters('method')]",
                "urlTemplate": "[parameters('urlTemplate')]"
            }
        },
        {
            "type": "Microsoft.ApiManagement/service/apis/operations/policies",
            "apiVersion": "2019-12-01",
            "name": "[concat(parameters('apimServiceName'), '/', parameters('apiName'), '/', parameters('operationId'), '/policy')]",
            "properties": {
                "format": "[parameters('format')]",
                "value": "[concat(parameters('repoBaseUrl'), parameters('templateFile')]"
            }
        }
    ],
    "outputs": {}
}
DEPLOY
}
