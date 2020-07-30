variable "api_policies" {
    default = [
    { 
      operationId     = "request-hearing",
      apimServiceName = "azurerm_api_management.hmi_apim.name",
      apiName         = "azurerm_api_management_api.hmi_apim_api.name",
      operationId     = "request-hearing",
      method          = "POST",
      format          = "rawxml-link",
      urlTemplate     = "/hearings",
      templateLink    = "https://raw.githubusercontent.com/hmcts/hmi-api-gateway-fh/HMIS-152_SANDBOX_CI/CD_Pipeline-temp/infrastructure/template/api-op-req-hearing-policy.xml"
    }
    ]
}