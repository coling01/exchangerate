#!/bin/sh
# Simple script to illustrate how we call the external service to get rates for a regular weekday
curl https://api.ratesapi.io/api/2020-01-06?base=EUR\&symbols=GBP,HKD,USD
# response ... the day before the bank holiday
# {"base":"EUR","rates":{"GBP":0.85215,"HKD":8.6978,"USD":1.1194},"date":"2020-01-06"}

curl -I https://api.ratesapi.io/api/2020-01-06?base=EUR\&symbols=GBP,HKD,USD
HTTP/1.1 200 OK
Date: Thu, 23 Apr 2020 14:30:57 GMT
Content-Type: application/json
Content-Length: 84
Connection: keep-alive
Set-Cookie: __cfduid=d475f506c54eb795483a7ad39276b41691587652257; expires=Sat, 23-May-20 14:30:57 GMT; path=/; domain=.ratesapi.io; HttpOnly; SameSite=Lax
Access-Control-Allow-Methods: GET
Access-Control-Allow-Credentials: true
Access-Control-Allow-Origin: *
Cache-Control: max-age=14400
CF-Cache-Status: HIT
Age: 8
Accept-Ranges: bytes
Expect-CT: max-age=604800, report-uri="https://report-uri.cloudflare.com/cdn-cgi/beacon/expect-ct"
Server: cloudflare
CF-RAY: 588849126d44bfde-MAN
cf-request-id: 02490bff7d0000bfde1a366200000001
