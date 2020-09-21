[CmdletBinding()]
Param (
[Parameter(Mandatory=$true)]
[string] $Hostname,

[Parameter(Mandatory=$true)]
[string] $HostnameType,

[Parameter(Mandatory=$true)]
[string] $KeyVaultId,

[Parameter(Mandatory=$true)]
[string] $ResourceGroupName
)

Install-Module -Name Az.ApiManagement  -Force
$proxy = New-AzApiManagementCustomHostnameConfiguration -Hostname $Hostname -HostnameType $HostnameType -KeyVaultId $KeyVaultId -DefaultSslBinding
$apim = Get-AzApiManagement -ResourceGroupName $ResourceGroupName
$apim.ProxyCustomHostnameConfiguration = $proxy
Set-AzApiManagement -InputObject $apim -SystemAssignedIdentity