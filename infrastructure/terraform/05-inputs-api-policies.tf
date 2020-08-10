variable "api_policies" {
<<<<<<< HEAD
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
=======
    default = [
    { 
      operationId     = "request-hearing"
      format          = "rawxml-link"
      templateFile    = "api-op-request-hearing-policy.xml"
    },
        { 
      operationId     = "health-check"
      format          = "rawxml-link"
      templateFile    = "api-op-health-check-policy.xml"
>>>>>>> c67044d8205b97556a2e3d86c264ac3f20d870c6
    }
  ]
}