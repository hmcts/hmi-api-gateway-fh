apim_sku_name = "Developer"
apim_sku_capacity = "1"
publisher_name = "HMCTS HMI"
publisher_email = "test@hmcts.placeholder"
protocols = ["http", "https"]
revision = "1"
open_api_spec_content_format = "swagger-link-json"
open_api_spec_content_value = "https://raw.githubusercontent.com/hmcts/reform-api-docs/master/docs/specs/future-hearings-hmi-api.json"
service_url = "https://www.hmcts.com/request-hearings/request-listings"
tags = {
    "businessarea":"cross-cutting",
    "application":"hearing-management-interface",
    "environment":"testing"
  }
enable_mock_header_string = "<set-header name=\"_EnableMocks\" exists-action=\"override\"><value>true</value></set-header>"
