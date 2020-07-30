resource "azurerm_template_deployment" "apim-policy" {
    name                = "apim-policy-01"
    resource_group_name = azurerm_resource_group.hmi_apim_rg.name
    depends_on          = [azurerm_api_management_product.hmi_apim_product]
    deployment_mode 	= "Incremental"
    parameters          = {
        "apimServiceName" = azurerm_api_management.hmi_apim.name
        "apiName"         = azurerm_api_management_api.hmi_apim_api.name 
        "operationId"     = "request-hearing"
        "method"          = "POST"
        "format"          = "rawxml-link"
        "urlTemplate"     = "/hearing"
        "templateLink"    = "https://raw.githubusercontent.com/hmcts/hmi-api-gateway-fh/HMIS-152_SANDBOX_CI/CD_Pipeline-temp/infrastructure/template/api-op-req-hearing-policy.xml"
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
