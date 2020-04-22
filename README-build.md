[Back to parent README](./README.md)

## Build

This restful api is maven java8 project built with SpringBoot. It requires access to maven central repository to build.



To build the project:

```
mvn clean package
```


To run the project:

```
java -jar target/exchangerategw-1.0.0-SNAPSHOT-client.jar
```



To build and run the project:

```
mvn spring-boot:run
```


Access the api specification here
```
mvn spring-boot:run
```


Access the running application here

[Exchange Rates](http://localhost:8080/exchangerates)



#### Build / Running Tests in Intellij
Running in intellij you may need to exclude the generated client tests.
target/classes/generated-sources/swagger/src/test/java
Right click and mark directory as Excluded

