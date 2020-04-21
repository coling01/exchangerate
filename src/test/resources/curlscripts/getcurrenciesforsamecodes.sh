#!/bin/sh
# Simple script to illustrate how we call the external service to get rates when symbol is the same as base
curl https://api.ratesapi.io/api/2020-02-28?base=USD\&symbols=USD
# response ... is 1.0 as expected
# {"base":"USD","rates":{"USD":1.0},"date":"2020-02-28"}
