resource "null_resource" "run-posh" {
  provisioner "local-exec" {
    command = "../../script/apply_custom_domain.ps1 -Hostname '${var.custom_hostname}' -HostnameType '${var.custom_hostname_type}' -KeyVaultId '${var.custom_hostname_certificate}' -ResourceGroupName '${var.infra_kv_name}"
    interpreter = ["PowerShell", "-Command"]
  }
}
