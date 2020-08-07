variable "api_policies" {
  default = [
    {
      operationId  = "request-hearing"
      format       = "rawxml-link"
      templateFile = "api-op-request-hearing-policy.xml"
    },
    {
      operationId  = "health-check"
      format       = "rawxml-link"
      templateFile = "api-op-health-check-policy.xml"
    }
  ]
}