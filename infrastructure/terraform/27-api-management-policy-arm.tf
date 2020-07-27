resource "azurerm_template_deployment" "apim-policy" {
    name                = "apim-policy-01"
    resource_group_name = azurerm_resource_group.hmi_apim_rg.name
    depends_on          = ["azurerm_api_management_product.hmi_apim_product"]
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
            "metadata": {
                "description": "The name of the API Management"
            }
        },
    "variables": {

    }, 
    "resources": [{
            "type": "Microsoft.ApiManagement/service/products/policies",
            "name": "[concat(parameters('ApimServiceName'), '/hmi-apim-product/policy')]",
            "apiVersion": "2018-01-01",
            "properties": {
                "policyContent": "[../template/product-unlimited.policy.xml')]",
                "contentFormat": "rawxml-link"
            },
            "targetResourceId": [
                "[resourceId('Microsoft.ApiManagement/service/products', parameters('ApimServiceName'), 'hmi-apim-product')]"
            ]
        }],
    "outputs": {}
    }
}
DEPLOY
}