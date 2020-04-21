#!/bin/sh
# Simple script to illustrate how we call the external service to get rates for an invalid date
curl https://api.ratesapi.io/api/2020-02-30?base=EUR\&symbols=GBP,HKD,USD
# response ...
# {"error":"day is out of range for month"}

