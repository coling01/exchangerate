#!/bin/sh
# Simple script to illustrate how we call the external service to get rates for a bad target currency
curl https://api.ratesapi.io/api/2020-02-28?base=EUR\&symbols=BAD
# response ...
# {"error":"Symbols 'BAD' are invalid for date 2020-02-28."}

