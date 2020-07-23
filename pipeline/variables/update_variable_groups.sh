#!/usr/bin/env bash
set -e

if [ $# -lt 2 ]
  then
    echo "Please provide username and PAT"
    exit 1
fi

for fileName in *.json; do

groupName=$(cat ${fileName} | jq -r .name)
groupId=$(curl -s -X GET -u ${USER}:${PAT} "https://dev.azure.com/hmcts/Shared%20Services/_apis/distributedtask/variablegroups/?groupName=${groupName}&api-version=5.0-preview.1" | jq -r "if (.count == 1 ) then .value[0].id else empty end")

    if [[ ${groupId} ]]; then
        curl -X PUT -u ${USER}:${PAT} https://dev.azure.com/hmcts/Shared%20Services/_apis/distributedtask/variablegroups/${groupId}?api-version=5.0-preview.1 --data-binary @${fileName} -H "Content-Type:application/json"
    else
        curl -X POST -u ${USER}:${PAT} https://dev.azure.com/hmcts/Shared%20Services/_apis/distributedtask/variablegroups?api-version=5.0-preview.1 --data-binary @${fileName} -H "Content-Type:application/json"
    fi
done