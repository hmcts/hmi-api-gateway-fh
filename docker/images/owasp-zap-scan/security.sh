#!/usr/bin/env bash
echo ${TEST_URL}
zap-api-scan.py -t ${TEST_URL} -f openapi -S -d -u ${SecurityRules} -P 1001 -l FAIL
cat zap.out
zap-cli --zap-url http://0.0.0.0 -p 1001 report -o /zap/api-report.html -f html
cp /zap/api-report.html /zap/wrk/
curl --fail http://0.0.0.0:1001/OTHER/core/other/jsonreport/?formMethod=GET --output report.json
cp *.* /zap/wrk/
zap-cli --zap-url http://0.0.0.0 -p 1001 alerts -l High --exit-code False