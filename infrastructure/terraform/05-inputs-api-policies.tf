variable "api_policies" {
    default = [
    { 
      operationId     = "request-hearing-1"
      method          = "POST"
      format          = "rawxml-link"
      urlTemplate     = "/hearings1"
      templateLink    = "https://raw.githubusercontent.com/hmcts/hmi-api-gateway-fh/HMIS-152_SANDBOX_CI/CD_Pipeline-temp/infrastructure/template/api-op-req-hearing-policy.xml"
    },
        { 
      operationId     = "request-hearing-2"
      method          = "POST"
      format          = "rawxml-link"
      urlTemplate     = "/hearings2"
      templateLink    = "https://raw.githubusercontent.com/hmcts/hmi-api-gateway-fh/HMIS-152_SANDBOX_CI/CD_Pipeline-temp/infrastructure/template/api-op-req-hearing-policy.xml"
    }
    ]
}