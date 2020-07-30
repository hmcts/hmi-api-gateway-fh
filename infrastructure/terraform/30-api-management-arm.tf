resource "azurerm_template_deployment" "apim-policy" {
    name                = "apim-policy-01"
    resource_group_name = azurerm_resource_group.hmi_apim_rg.name
    depends_on          = [azurerm_api_management_api.hmi_apim_api]
    deployment_mode 	= "Incremental"
    count               = "${length(var.api_policies)}"
    parameters          = {
        apimServiceName = "${lookup(var.api_policies[count.index], "apimServiceName")}"
        apiName         = "${lookup(var.api_policies[count.index], "apiName")}"
        operationId     = "${lookup(var.api_policies[count.index], "operationId")}"
        method          = "${lookup(var.api_policies[count.index], "method")}"
        format          = "${lookup(var.api_policies[count.index], "format")}"
        urlTemplate     = "${lookup(var.api_policies[count.index], "urlTemplate")}"
        templateLink    = "${lookup(var.api_policies[count.index], "templateLink")}"
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
        "templateLink": {
            "type": "String"
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
                "value": "[parameters('templateLink')]"
            }
        }
    ],
    "outputs": {}
}
DEPLOY
}
