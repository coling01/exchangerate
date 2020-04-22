[Back to parent README](./README.md)

## Acceptance Criteria
There are several niggles with the AC, outlined below. Unfortunately in the real world we cannot just change the AC, unilaterally.
BUT ... Rather than wasting much time building something that is not correct,

It is better to cut down the build to have something to get on with and find someone / anyone to agree a set of reduced AC.

Even if the BA is out of action for some inexplicable reason, I would find a PO or somebody / anybody from the business to agree a reduced AC as follows.

Thus allow build to proceed whilst we flash out a better approach to the UI and discuss volumes and caching.
As long as we define the spec for the API correctly and dont have to use a versioning to amend it.
And since we are not coding any ui or api client currently, it would not matter right now.
Seems the likely extensions to the API would be
* Accept a validated number of months to retrieve. A none breaking spec change.
* Accept a validated list of currencies to retrieve ( api will currently default to GBP,HKD,USD ). A none breaking spec change.
* Accept a validated single currency to act as base currency. ( api will currently default to EUR ). A none breaking spec change.
* Cache the responses. No spec change.
* Introduce endpoint security using kong, mulesoft or aws / gcp built ins.


Proposed AC
Given
    User wants to check exchange rates for GBP, USD, HKD against Euro
When
    User opens a browser and enters the url to access the endpoint
Then
    The raw response data is shown for today and the previous 6 months

