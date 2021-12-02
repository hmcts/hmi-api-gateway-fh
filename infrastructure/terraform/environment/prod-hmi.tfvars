apim_sku_name                      = "Premium"
apim_sku_capacity                  = "1"
publisher_name                     = "HMCTS HMI"
publisher_email                    = "hmi-team@HMCTS.NET"
protocols                          = ["http", "https"]
open_api_spec_content_format       = "swagger-link-json"
open_api_spec_content_value        = "https://raw.githubusercontent.com/hmcts/reform-api-docs/master/docs/specs/future-hearings-hmi-api.json"
open_api_health_spec_content_value = "https://raw.githubusercontent.com/hmcts/reform-api-docs/master/docs/specs/future-hearings-hmi-api-health.json"
revision                           = "27"
service_url                        = ""

enable_mock_header_string = "<set-header name=\"_EnableMocks\" exists-action=\"override\"><value>true</value></set-header>"
virtual_network_type      = "Internal"