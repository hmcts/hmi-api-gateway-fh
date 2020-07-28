resource "azurerm_template_deployment" "apim-policy" {
    name                = "apim-policy-01"
    resource_group_name = azurerm_resource_group.hmi_apim_rg.name
    depends_on          = [azurerm_api_management_product.hmi_apim_product]
    deployment_mode 	= "Incremental"
    parameters          = {
        "ApimServiceName" = azurerm_api_management.hmi_apim.name
        "ApiName"         = azurerm_api_management_api.hmi_apim_api.name 
        "OperationId"     = "request-hearing"
    	}


    template_body 	    = <<DEPLOY
{
    "$schema": "https://schema.management.azure.com/schemas/2015-01-01/deploymentTemplate.json#",
    "contentVersion": "1.0.0.0",
    "parameters": {
        "ApimServiceName": {
            "type": "String"
        },
        "ApiName": {
            "type": "String"
        },
        "OperationId": {
            "type": "String"
        }
    },
    "resources": [
        {
            "type": "Microsoft.ApiManagement/service/apis/operations",
            "apiVersion": "2019-12-01",
            "name": "[concat(parameters('ApimServiceName'), '/', parameters('ApiName'), '/', parameters('OperationId'))]",
            "properties": {
                "displayName": "Hearing",
                "method": "POST",
                "urlTemplate": "/hearing"
            }
        },
        {
            "type": "Microsoft.ApiManagement/service/apis/operations/policies",
            "apiVersion": "2019-12-01",
            "name": "[concat(parameters('ApimServiceName'), '/', parameters('ApiName'), '/', parameters('OperationId'), '/policy')]",
            "properties": {
                "value": "infrastructure/template/api-op-req-hearing-policy.xml",
                "format": "xml-link"
            }
        }
    ],
    "outputs": {}
}
DEPLOY
}
