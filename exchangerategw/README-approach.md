[Back to parent README](./README.md)

## Approach
Since this is a big task with several queries ... split it down and deliver the first part.
No point doing too much build until requirements are absoutely clear.
Prototpying of the solution will also allow the data, pages etc. to be better visualised in order to refine the requirements.

* API first driven by API specs for client and server.
* Discuss and agree the reduced / stepwise approach with team / business
* Ignore web layer pending refinement. Including authentication
* API controller will accept get request with no parameters with day-of-month defaulted to todays day-of-month.
* API controller will call down to service layer to retrieve mapped data as json
* API service layer will make multiple calls to api client to retrieve data
* No caching in initial delivery
* Testing / demo / show and tell will be able to demonstrate limited functionality via browser and/or postman.

