variable "api_policies" {
    default = [
    { 
      operationId     = "Request Hearing"
      method          = "POST"
      format          = "rawxml-link"
      urlTemplate     = "/hearings"
      templateFile    = "api-op-req-hearing-policy.xml"
    },
        { 
      operationId     = "request-hearing-2"
      method          = "POST"
      format          = "rawxml-link"
      urlTemplate     = "/hearings2"
      templateFile    = "api-op-req-hearing-policy.xml"
    }
    ]
}