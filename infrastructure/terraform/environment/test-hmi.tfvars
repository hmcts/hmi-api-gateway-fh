apim_sku_name                      = "Developer"
apim_sku_capacity                  = "1"
publisher_name                     = "HMCTS HMI"
publisher_email                    = "hmi-team@HMCTS.NET"
protocols                          = ["http", "https"]
open_api_spec_content_format       = "swagger-link-json"
open_api_spec_content_value        = "https://raw.githubusercontent.com/hmcts/reform-api-docs/HMIS-884/docs/specs/future-hearings-hmi-api.json"
open_api_health_spec_content_value = "https://raw.githubusercontent.com/hmcts/reform-api-docs/master/docs/specs/future-hearings-hmi-api-health.json"
revision                           = "12"
service_url                        = "https://www.hmcts.com/request-hearings/request-listings"
tags = {
  "businessarea" : "cross-cutting",
  "application" : "hearing-management-interface",
  "environment" : "testing"
}
enable_mock_header_string = "<set-header name=\"_EnableMocks\" exists-action=\"override\"><value>true</value></set-header>"
virtual_network_type      = "Internal"