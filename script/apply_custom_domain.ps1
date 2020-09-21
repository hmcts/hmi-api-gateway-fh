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

Write-Host "Installing Az.ApiManagement Module..."
Install-Module -Name Az.ApiManagement -Force
Write-Host "Az.ApiManagement Module successfully installed..."
$proxy = New-AzApiManagementCustomHostnameConfiguration -Hostname $Hostname -HostnameType $HostnameType -KeyVaultId $KeyVaultId -DefaultSslBinding
$apim = Get-AzApiManagement -ResourceGroupName $ResourceGroupName
$apim.ProxyCustomHostnameConfiguration = $proxy
Write-Host "Applyig Custom Domain configuration..."
Set-AzApiManagement -InputObject $apim -SystemAssignedIdentity
Write-Host "Custom domain successfully applied..."