
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
    },
    {
      operationId  = "update-hearing"
      format       = "rawxml-link"
      templateFile = "api-op-update-hearing-policy.xml"
    },
    {
      operationId  = "retrieve-hearings"
      format       = "rawxml-link"
      templateFile = "api-op-retrieve-hearings-policy.xml"
    },
    {
      operationId     = "schedule"
      format          = "rawxml-link"
      templateFile    = "api-op-retrieve-hearing-sched-policy.xml"
    },
    {
      operationId     = "resource-by-id"
      format          = "rawxml-link"
      templateFile    = "api-op-retrieve-resource-by-id-policy.xml"
    },
    {
      operationId     = "resources"
      format          = "rawxml-link"
      templateFile    = "api-op-retrieve-resources-policy.xml"
    },
    {
      operationId     = "delete-hearing"
      format          = "rawxml-link"
      templateFile    = "api-op-delete-hearing-policy.xml"
    },
    {
      operationId     = "delete-resource"
      format          = "rawxml-link"
      templateFile    = "api-op-delete-resource-policy.xml"
    },
    {
      operationId     = "get-sessions"
      format          = "rawxml-link"
      templateFile    = "api-op-get-sessions-policy.xml"
    },
    {
      operationId     = "update-session"
      format          = "rawxml-link"
      templateFile    = "api-op-update-session-policy.xml"
    }
  ]
}