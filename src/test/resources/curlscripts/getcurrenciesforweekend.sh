#!/bin/sh
# Simple script to illustrate how we call the external service to get rates for a day on the weekend
curl https://api.ratesapi.io/api/2020-01-12?base=EUR\&symbols=GBP,HKD,USD
# response ... note that we asked for a 01-12 a sunday but got back fridays 01-10
# {"base":"EUR","rates":{"GBP":0.8481,"HKD":8.6137,"USD":1.1091},"date":"2020-01-10"}
