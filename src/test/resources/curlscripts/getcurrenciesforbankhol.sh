#!/bin/sh
# Simple script to illustrate how we call the external service to get rates for a bank holiday
curl https://api.ratesapi.io/api/2020-01-01?base=EUR\&symbols=GBP,HKD,USD
# response ... the day before the bank holiday
# {"base":"EUR","rates":{"GBP":0.8508,"HKD":8.7473,"USD":1.1234},"date":"2019-12-31"}
