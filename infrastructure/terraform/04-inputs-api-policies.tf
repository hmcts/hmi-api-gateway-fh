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
      operationId  = "schedule"
      format       = "rawxml-link"
      templateFile = "api-op-retrieve-hearing-sched-policy.xml"
    },
    {
      operationId  = "resource-by-id"
      format       = "rawxml-link"
      templateFile = "api-op-retrieve-resource-by-id-policy.xml"
    },
    {
      operationId  = "resources"
      format       = "rawxml-link"
      templateFile = "api-op-retrieve-resources-policy.xml"
    },
    {
      operationId  = "delete-hearing"
      format       = "rawxml-link"
      templateFile = "api-op-delete-hearing-policy.xml"
    },
    {
      operationId  = "delete-resource"
      format       = "rawxml-link"
      templateFile = "api-op-delete-resource-policy.xml"
    },
    {
      operationId  = "get-listings"
      format       = "rawxml-link"
      templateFile = "api-op-get-listing-policy.xml"
    },
    {
      operationId  = "update-resource-location"
      format       = "rawxml-link"
      templateFile = "api-op-update-resource-location-policy.xml"
    },
    {
      operationId  = "update-resource-user"
      format       = "rawxml-link"
      templateFile = "api-op-update-resource-user-policy.xml"
    },
    {
      operationId  = "get-listings"
      format       = "rawxml-link"
      templateFile = "api-op-get-listing-policy.xml"
    },
    {
      operationId  = "get-sessions"
      format       = "rawxml-link"
      templateFile = "api-op-get-sessions-policy.xml"
    },
    {
      operationId  = "update-session"
      format       = "rawxml-link"
      templateFile = "api-op-update-session-policy.xml"
    },
    {
      operationId  = "get-session-by-id"
      format       = "rawxml-link"
      templateFile = "api-op-get-sessions-by-id-policy.xml"
    },
    {
      operationId  = "get-listing-by-id"
      format       = "rawxml-link"
      templateFile = "api-op-get-listing-by-id-policy.xml"
    },
    {
      operationId     = "create-session"
      format          = "rawxml-link"
      templateFile    = "api-op-create-session-policy.xml"
    },
    {
      operationId     = "delete-session"
      format          = "rawxml-link"
      templateFile    = "api-op-delete-session-policy.xml"
    },
    {
      operationId     = "secure"
      format          = "rawxml-link"
      templateFile    = "api-op-get-secure-policy.xml"
    }
  ]
}
