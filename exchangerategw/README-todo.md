[Back to parent README](./README.md)

## TODO

* ~~Use a generated client api rather than the restTemplate currently used. We have proved the client spec model.~~
* Extend the spec to cover errors
* Limit the number of months on the incoming controller, best in the spec.
* Discuss / agree information exposed on errors / error message.
* Tidy the Mapper its too complicated
* Use immutable builders to create mapping objects rather than the wide open generated classes.
* ~~UI - Lots to agree on this so not a quick job.~~
* CI Build - We would likely want to build the artifacts and run tests in a ci pipe thus need a Jenkinsfile or gitlab-ci.yml definition
* The CI build should include running unit and integration tests as well as sonar, checkmarx, style checks, owasp cve
* Actuator - Expose version and health information
* Docker - We may want to spin the api up in a docker instance thus may need a docker-compose file
* Docker image - We may want to deploy the built artfiacts as docker containers thus may need a Dockerfile
* Stubbing - We may want to create an internal stubbed version of the external service to allow integration testing
* Correlation-id - We may want to consider using a consistent trace id across ui and apis.
* Api specs - We may want to consider a central location for all api-specs
* Security between gateway and external service, presumably a proxy involved.
* Security between ui and gateway, do we bake the OAUTH into this or rely on an endpoint security provider such as kong, apexone or mulesoft.
* Who will be using the ui / api. Internal or external ? Volumes ? what stack to use for the ui ?
* User access ? How will we secure access to the ui and through to the api ( and presumably other apis in the future ) ?
* Versioning, lets leave versioning till if the api has a breaking change. The subject of this exercise ( exchange rates ) should be highly stable and unlikely to change.

