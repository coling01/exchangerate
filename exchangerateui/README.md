## Approach
Was not intending to add UI for reasons outline in the gateway README.md.
Added this basic ui at the very last minute.
Still things to address
* Add controller and BDD test
* Strengthen client tests to mock and check parsing of client response
* Add security between UI and API ( presuming thats where we want to use basic authentication )
* Styling
* Check error handling
* ... lots of questions around functionality hence not fully implemented


## Queries / Clarifications
There are several issues with this assignment that make it difficult to implement accurately without discussion.
I dont believe this build activity would have got into build in normal team structure because we would have picked up the oddities and addressed them by
* Discussion with BA, PO
* Discussion with build team
* Looking at existing architecture and discussion with any tech architects

Queries / Clarifications
* Page3 on the spec PDF is blank. Is there something missing here ?

* AC is heavy on errors.
3 of the 5 AC relate to "application error". Rather than focus on application error we should refocus our energy to ensuring that there are no business related errors.
The only errors would be "runtime" or unexpected errors such as external api being unavailable, bad data returned and theres nothing the user can do to rectify this in the short term.
Hence we should display "An unexpected error occurred" type message to the user and log the error details, ensuring that the issue will be picked up by Ops monitoring for investigation.
And probably a route for the user to also raise the issue, depending who / where / what kind of user we have.

* AC Changes
In reality, we would not actually build this as since we have too many queries.
However, since we have acceptance criteria, it would not be right for us to unilaterally change the acs without business agreement.

* An inconsistancy in the Example breakdown currency column that shows both GBP and UK, also USA not USD.

* Hitting external api
We can retrieve rates for any number of currencies for a single date in one external api call.
For the default page ( displaying today ) this would be a single hit to the external api.
For the historical page ( display last 6 months ) this would be 6 hits to the external api.
The external rates api does state
... Please cache results whenever possible this will allow us to keep the service without any rate limits or api key requirements.

* Caching
We are given no guidance relating to expected volumes which we really should understand before we do any unneccessary work.
If volume is very low there really is no point introducing code to cache the data.
Though the data is historical and thus static so could be cached forever, thus reducing the hits on the external service.
It would be an easy logical step to add the caching into the exchangerategw if definitely required.

* Rates api
Feels odd that the rates API does not accept multiple dates in a single request this would simplify things greatly.
If we are going to cache then this is no big deal, after an initial data load, we would only do one hit per new day.

* For a ui application I would expect more details and ideally some screenshots. Also some indication of preferred ui stack.
Hence we will ignore the UI requirements other than driving the initial api spec.

* Day of month
Since there is no webpage option for selecting the target day, the day of month will be assumed to be todays day-of-month
Since not every month will have a day for todays day-of-month, we will show the previous existing day-of-month
( Otherwise there would be no way of showing rates for 29/30/31 in Feb for example )
The webpage button to "display the next page" is expected to flip between single month view and multiple month view.
for the past six months for the same day.
It would make more sense to either
a) Allow entry of day-of-month and thus show n/a for none existing months
b) Add previous / next buttons to move backwards to a day-of-month that does exist
c) Use a fixed number of days deduction i.e. 4 weeks ago, 8 weeks ago.
d) Use an algorithm for finding the nth week in the month
e) When a day-of-month does not exist for any month, use the last existing day-of-month. i.e. When showing for 31, use 30-Sept, 28-Feb, 29-Feb etc.
and put the date in the top heading.
