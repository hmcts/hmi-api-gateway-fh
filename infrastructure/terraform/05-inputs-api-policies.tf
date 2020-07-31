variable "api_policies" {
    default = [
    { 
      operationId     = "Request-Hearing"
      operationName   = "Request Hearing"
      method          = "POST"
      format          = "rawxml-link"
      urlTemplate     = "/hearings"
      templateFile    = "api-op-req-hearing-policy.xml"
    },
        { 
      operationId     = "Health-Check"
      operationName   = "Health Check"
      method          = "GET"
      format          = "rawxml-link"
      urlTemplate     = "/"
      templateFile    = "api-op-health-check-policy.xml"
    }
    ]
}