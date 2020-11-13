variable "api_policies" {
  default = [
    {
      operationId  = "request-hearing"
      format       = "rawxml-link"
      templateFile = "api-op-request-hearing-policy-${var.environment}.xml"
    },
    {
      operationId  = "health-check"
      format       = "rawxml-link"
      templateFile = "api-op-health-check-policy-${var.environment}.xml"
    },
    {
      operationId  = "update-hearing"
      format       = "rawxml-link"
      templateFile = "api-op-update-hearing-policy-${var.environment}.xml"
    },
    {
      operationId  = "retrieve-hearings"
      format       = "rawxml-link"
      templateFile = "api-op-retrieve-hearings-policy-${var.environment}.xml"
    },
    {
      operationId  = "schedule"
      format       = "rawxml-link"
      templateFile = "api-op-retrieve-hearing-sched-policy-${var.environment}.xml"
    },
    {
      operationId  = "resource-by-id"
      format       = "rawxml-link"
      templateFile = "api-op-retrieve-resource-by-id-policy-${var.environment}.xml"
    },
    {
      operationId  = "resources"
      format       = "rawxml-link"
      templateFile = "api-op-retrieve-resources-policy-${var.environment}.xml"
    },
    {
      operationId  = "delete-hearing"
      format       = "rawxml-link"
      templateFile = "api-op-delete-hearing-policy-${var.environment}.xml"
    },
    {
      operationId  = "delete-resource"
      format       = "rawxml-link"
      templateFile = "api-op-delete-resource-policy-${var.environment}.xml"
    },
    {
      operationId  = "get-listings"
      format       = "rawxml-link"
      templateFile = "api-op-get-listing-policy-${var.environment}.xml"
    },
    {
      operationId  = "update-resource-location"
      format       = "rawxml-link"
      templateFile = "api-op-update-resource-location-policy-${var.environment}.xml"
    },
    {
      operationId  = "update-resource-user"
      format       = "rawxml-link"
      templateFile = "api-op-update-resource-user-policy-${var.environment}.xml"
    },
    {
      operationId  = "get-listings"
      format       = "rawxml-link"
      templateFile = "api-op-get-listing-policy-${var.environment}.xml"
    },
    {
      operationId  = "get-sessions"
      format       = "rawxml-link"
      templateFile = "api-op-get-sessions-policy-${var.environment}.xml"
    },
    {
      operationId  = "update-session"
      format       = "rawxml-link"
      templateFile = "api-op-update-session-policy-${var.environment}.xml"
    },
    {
      operationId  = "get-session-by-id"
      format       = "rawxml-link"
      templateFile = "api-op-get-sessions-by-id-policy-${var.environment}.xml"
    },
    {
      operationId  = "get-listing-by-id"
      format       = "rawxml-link"
      templateFile = "api-op-get-listing-by-id-policy-${var.environment}.xml"
    },
    {
      operationId  = "create-session"
      format       = "rawxml-link"
      templateFile = "api-op-create-session-policy-${var.environment}.xml"
    },
    {
      operationId  = "delete-session"
      format       = "rawxml-link"
      templateFile = "api-op-delete-session-policy-${var.environment}.xml"
    },
    {
      operationId  = "secure"
      format       = "rawxml-link"
      templateFile = "api-op-get-secure-policy-${var.environment}.xml"
    },
    {
      operationId  = "test-wiremock-connection"
      format       = "rawxml-link"
      templateFile = "api-op-test-wiremock-policy-${var.environment}.xml"
    },
    {
      operationId  = "create-resource-location"
      format       = "rawxml-link"
      templateFile = "api-op-create-resource-location-policy-${var.environment}.xml"
    },
    {
      operationId  = "create-resource-user"
      format       = "rawxml-link"
      templateFile = "api-op-create-resource-user-policy-${var.environment}.xml"
    },
    {
      operationId  = "update-listing"
      format       = "rawxml-link"
      templateFile = "api-op-update-listing-policy-${var.environment}.xml"
    },
    {
      operationId  = "delete-listing"
      format       = "rawxml-link"
      templateFile = "api-op-delete-listing-policy-${var.environment}.xml"
    }
  ]
}
