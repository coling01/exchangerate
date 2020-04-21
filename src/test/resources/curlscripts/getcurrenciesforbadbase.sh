#!/bin/sh
# Simple script to illustrate how we call the external service to get rates for a bad base currency
curl https://api.ratesapi.io/api/2020-02-28?base=BAD\&symbols=UKP
# response ...
# {"error":"Base 'BAD' is not supported."}

