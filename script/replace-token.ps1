$RootPath = Split-Path -Parent $MyInvocation.MyCommand.Path | Split-Path
Write-Host 'Replacing tokens  in Policy files. Please wait...'
Get-ChildItem $RootPath -Filter '*-policy.xml' -Recurse | 
Foreach-Object {
    $file = Get-Content $_.FullName
    $containsWord = $file | %{$_ -match "#{hostName}#"}
    if ($containsWord -contains $true) {
      (Get-Content $_.FullName) -replace  '#{hostName}#','http://milan.org' | Set-Content $_.FullName
        Write-Host "** Updated Token in File:  " $_.FullName

        Write-Host 'After'  (Get-Content $_.FullName)
    } 
}
Write-Host 'Replacement complete.'