variable "infra_kv_name" {
  default = null
}
variable "infra_kv_rg" {
  default = null
}
variable "custom_hostname" {
  default = "hmi-apim-sbox.sandbox.platform.hmcts.net"
}
variable "custom_hostname_type" {
  default = "Proxy"
}
variable "custom_hostname_certificate" {
  default = "https://hmi-sharedinfra-kv-sbox.vault.azure.net/secrets/temp-cert"
}
