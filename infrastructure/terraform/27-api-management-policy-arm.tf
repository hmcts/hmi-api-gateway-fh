resource "azurerm_template_deployment" "apim-policy" {
    name                = "apim-policy-01"
    resource_group_name = azurerm_resource_group.hmi_apim_rg.name
    depends_on          = [azurerm_api_management_product.hmi_apim_product]
    deployment_mode 	= "Incremental"
    parameters          = {
        "ApimServiceName" = "hmi-apim-svc-sbox" # azurerm_api_management.hmi_apim.name
    	}


    template_body 	    = <<DEPLOY
{
    "$schema": "https://schema.management.azure.com/schemas/2015-01-01/deploymentTemplate.json#",
    "contentVersion": "1.0.0.0",
    "parameters": {
        "ApimServiceName": {
            "type": "string",
            "metadata": {"description": "The name of the API Management"}
            },
    "variables": {}, 
    "resources": [
        {
            "type": "Microsoft.ApiManagement/service/apis/operations/policies",
            "apiVersion": "2019-12-01",
            "name": "[concat(parameters('ApimServiceName'), '/hmi-apim-api-sbox/request-hearing/policy')]",
            "properties": {
                "policyContent": "[../template/product-unlimited.policy.xml]",
                "contentFormat": "rawxml-link"
                },
            "dependsOn": [
                "[resourceId('Microsoft.ApiManagement/service/apis/operations', parameters('ApimServiceName'), 'hmi-apim-api-sbox', 'request-hearing')]",
                "[resourceId('Microsoft.ApiManagement/service/apis', parameters('ApimServiceName'), 'hmi-apim-api-sbox')]",
                "[resourceId('Microsoft.ApiManagement/service', parameters('ApimServiceName'))]"
            ]
            }
        ],
    "outputs": {}
    }
}
DEPLOY
}
