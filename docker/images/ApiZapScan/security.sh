#!/usr/bin/env bash
echo ${TEST_URL}
UNIQUE_NAME=`echo ${TEST_URL} | sed -e 's/[^A-Za-z0-9._-]/_/g'`
zap-api-scan.py -t ${TEST_URL} -f openapi -S -d -u ${SecurityRules} -P 80 -l FAIL
cat zap.out
zap-cli --zap-url http://0.0.0.0 -p 80 report -o /zap/$UNIQUE_NAME-api-report.html -f html
cp /zap/$UNIQUE_NAME-api-report.html /zap/wrk/
curl --fail http://0.0.0.0:80/OTHER/core/other/jsonreport/?formMethod=GET --output $UNIQUE_NAME-report.json
cp *.* /zap/wrk/
zap-cli --zap-url http://0.0.0.0 -p 80 alerts -l High --exit-code False
