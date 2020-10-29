[CmdletBinding()]
Param (
[Parameter(Mandatory=$true)]
[string] $Hostname,

[Parameter(Mandatory=$true)]
[string] $HostnameType,

[Parameter(Mandatory=$true)]
[string] $KeyVaultName,

[Parameter(Mandatory=$true)]
[string] $ResourceGroupName
)

if (!(Get-Module -Name Az.ApiManagement)){
    Write-Host "Installing Az.ApiManagement Module..." -ForegroundColor Yellow
    Install-Module -Name Az.ApiManagement -Force -Verbose
    Write-Host "Az.ApiManagement Module successfully installed..."
    } else {
        Write-Host "Az.ApiManagement already installed, skipping" -ForegroundColor Green
    }
$KeyVaultId = "https://$KeyVaultName.vault.azure.net/secrets/apim-hostname-certificate"
$proxy = New-AzApiManagementCustomHostnameConfiguration -Hostname $Hostname -HostnameType $HostnameType -KeyVaultId $KeyVaultId -DefaultSslBinding
$apim = Get-AzApiManagement -ResourceGroupName $ResourceGroupName
if ($apim.ProxyCustomHostnameConfiguration.Hostname -notcontains $proxy.Hostname) {
    $apim.ProxyCustomHostnameConfiguration = $proxy
    Write-Host "Applying Custom Domain configuration..." $proxy.Hostname -ForegroundColor Yellow
    $apim | Select-Object -Expand Identity | Select -ExpandProperty PrincipalId
    $apimObjectId = ($apim | Select-Object -Expand Identity | Select -ExpandProperty PrincipalId)
    Write-Host $apimObjectId
    Set-AzKeyVaultAccessPolicy -VaultName $KeyVaultName -ResourceGroupName $ResourceGroupName -ObjectId $apimObjectId -PermissionsToSecrets Get,List -Verbose -Debug
    Set-AzApiManagement -InputObject $apim -Verbose -Debug
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
