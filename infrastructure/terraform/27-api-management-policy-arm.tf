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
                "value": "<policies>\r\n  <inbound>\r\n    <set-header name=\"Company-Name\" exists-action=\"override\">\r\n      <value>HMCTS</value>\r\n    </set-header>\r\n    <base />\r\n    <set-backend-service base-url=\"https://www.hmcts.com/request-hearings/health-check\" />\r\n  </inbound>\r\n  <backend>\r\n    <base />\r\n  </backend>\r\n  <outbound>\r\n    <base />\r\n  </outbound>\r\n  <on-error>\r\n    <base />\r\n  </on-error>\r\n</policies>",
                "format": "xml"
            }
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
