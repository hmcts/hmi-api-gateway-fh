[CmdletBinding()]
param (
    [string]
    [ValidateNotNullOrEmpty()]
    $ResourceGroupName,
    [string]
    [ValidateNotNullOrEmpty()]
    $ApiManagementName
)

$ApiManagement = Get-AzApiManagement -ResourceGroupName $ResourceGroupName -Name $ApiManagementName
$ApiManagementContext = New-AzApiManagementContext -ResourceGroupName $ResourceGroupName -ServiceName $ApiManagementName
$ApiManagementAPIs = Get-AzApiManagementApi -Context $ApiManagementContext
$Subscriptions = Get-AzApiManagementSubscription -Context $ApiManagementContext

foreach ($_ in $ApiManagementAPIs.where{$_.ApiId -like "hmi*"}) {
    $Products = Get-AzApiManagementProduct -Context $ApiManagementContext -ApiId $_.ApiId
    foreach ($_ in $Products) {
        $Subscription = Get-AzApiManagementSubscription -Context $ApiManagementContext -ProductId $_.ProductId
        $SubscriptionKey = (Get-AzApiManagementSubscriptionKey -Context $ApiManagementContext -SubscriptionId $Subscription.SubscriptionId).PrimaryKey
        Write-Host "Product name: " $_.Title
        Write-Host "Product id: " $_.ProductId
        Write-Host "Service url: " $ApiManagementAPIs.where{$_.ApiId -like "hmi*"}.ServiceUrl
        Write-Host "Subscription key: " $SubscriptionKey
    }
}