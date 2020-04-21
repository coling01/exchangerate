#!/bin/sh
# Simple script to illustrate how we call the external service to get rates for a regular weekday
curl https://api.ratesapi.io/api/2020-01-06?base=EUR\&symbols=GBP,HKD,USD
# response ... the day before the bank holiday
# {"base":"EUR","rates":{"GBP":0.85215,"HKD":8.6978,"USD":1.1194},"date":"2020-01-06"}
