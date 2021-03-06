[CmdletBinding()]
Param (
[Parameter(Mandatory=$true)]
[string] $Hostname,

[Parameter(Mandatory=$true)]
[string] $HostnameType,

[Parameter(Mandatory=$true)]
[string] $Environment,

[Parameter(Mandatory=$true)]
[string] $Tenant,

[Parameter(Mandatory=$true)]
[string] $ServicePrincipalId,

[Parameter(Mandatory=$true)]
[string] $ServicePrincipalSecret,

[Parameter(Mandatory=$true)]
[string] $SubscriptionId
)


Install-Module -Name Az.Accounts -Force -Verbose

$SPSecretSecure = ConvertTo-SecureString -String $ServicePrincipalSecret -AsPlainText -Force
$psCred = New-Object System.Management.Automation.PSCredential($ServicePrincipalId, $SPSecretSecure)
Connect-AzAccount -Credential $psCred -TenantId $Tenant -ServicePrincipal

Set-AzContext -SubscriptionId $SubscriptionId

$KeyVaultName = "hmi-shared-kv-$Environment"
$ResourceGroupName = "hmi-apim-$Environment-rg"

if (!(Get-Module -Name Az.ApiManagement)){
    Write-Host "Installing Az.ApiManagement Module..." -ForegroundColor Yellow
    Install-Module -Name Az.ApiManagement -Force -Verbose
    Write-Host "Az.ApiManagement Module successfully installed..."
    } else {
        Write-Host "Az.ApiManagement already installed, skipping" -ForegroundColor Green
    }
$KeyVaultId = "https://$KeyVaultName.vault.azure.net/secrets/apim-hostname-certificate"
$proxy = (New-AzApiManagementCustomHostnameConfiguration -Hostname $Hostname -HostnameType $HostnameType -KeyVaultId $KeyVaultId -DefaultSslBinding -NegotiateClientCertificate)
$apim = (Get-AzApiManagement -ResourceGroupName $ResourceGroupName -Debug)
if ($apim.ProxyCustomHostnameConfiguration.Hostname -notcontains $proxy.Hostname) {
    $apim.ProxyCustomHostnameConfiguration = $proxy
    Write-Host "Applying Custom Domain configuration..." $proxy.Hostname -ForegroundColor Yellow
    Set-AzApiManagement -InputObject $apim -SystemAssignedIdentity -Confirm:$false
    Write-Host "Custom domain successfully applied..."
    Write-Host "Listing custom domains..."
    $apim = Get-AzApiManagement -ResourceGroupName $ResourceGroupName
    foreach ($_ in $apim.ProxyCustomHostnameConfiguration.Hostname) {
        Write-Host $_
    }
} else {
    Write-Host "Listing custom domains..."
    foreach ($_ in $apim.ProxyCustomHostnameConfiguration.Hostname) {
        Write-Host $_
    }
    Write-Host "Custom domain already applied, skipping..." -ForegroundColor Green
}
