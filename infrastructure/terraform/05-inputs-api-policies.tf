variable "api_policies" {
  default = [
    {
      operationId  = "Request-Hearing"
      format       = "rawxml-link"
      templateFile = "api-op-req-hearing-policy.xml"
    },
    {
      operationId  = "Health-Check"
      format       = "rawxml-link"
      templateFile = "api-op-health-check-policy.xml"
    }
  ]
}