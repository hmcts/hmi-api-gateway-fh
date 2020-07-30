#!/usr/bin/env bash
username=$1
password=$2
subscriptionname=DTS-SHAREDSERVICES-SBOX
vaultname=hmi-apim-kv-test
secretname=hmi-apim-svc-test

az login -u ${username} -p ${password}

az account set --subscription ${subscriptionname}

az keyvault secret list --vault-name ${vaultname}

TEST_SECRET=$(az keyvault secret show --vault-name $vaultname --name ${secretname} --query value)
export TEST_SECRET
echo ${TEST_SECRET}