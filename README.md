# spring-streaming-poc

Showcase of streaming files over spring boot backend using blocking and non-blocking techniques.

The project contains 4 flavours of the application providing the file API

* Netty blocking
* Netty non-blocking
* Tomcat blocking
* Tomcat streaming

A load test will attempt to download a 30MB pdf file from each of the application flavours. Each container only has
200MB memory available and a 64MB max heap space size. The load test downloads 3 files concurrently from each
application flavour for 30 seconds.

## The naive approach - Blocking

```mermaid
sequenceDiagram
    participant jmeter
    participant application
    participant ngnix
    jmeter ->> application: request file
    application ->> ngnix: request file
    ngnix ->> application: receive all file data
    application ->> application: buffer file in memory
    application ->> jmeter: write headers
    application ->> jmeter: write file data
```

## The Non-blocking/streaming approach

```mermaid
sequenceDiagram
    participant jmeter
    participant application
    participant ngnix
    jmeter ->> application: request file
    application ->> ngnix: request file
    application ->> jmeter: write headers
    ngnix ->> application: receive data block
    application ->> jmeter: write data block
    ngnix ->> application: receive data block
    application ->> jmeter: write data block
    ngnix ->> application: end of stream
    application ->> jmeter: end of stream
```

## Requirements

* [OpenJDK 21](https://adoptium.net/temurin/releases/)
* [Docker](https://www.docker.com/)
* [JMeter](https://jmeter.apache.org/)

## How to run

1. Build the bootJars `./gradlew build`
2. Package the images `docker compose build`
3. Run the stack `docker compose up`
4. Put some load on it `jmeter -n -t jmeter/Streaming_PDF.jmx -l results/result.jtl -e -o results`
5. Inspect the HTML report / view docker logs to see any errors

## Pitfalls

* JMeter out of memory, check your available memory to JMeter in "apache-jmeter-{version}/bin/jmeter", update the
  following line by adding a larger max heap space `: "${HEAP:="-Xms1g -Xmx10g -XX:MaxMetaspaceSize=256m"}"`

## Learnings

The blocking approach will fail to deliver the PDF files in a reliable manner, throwing out of memory errors as there
isn't enough heap space available to buffer all the files in memory. 

The non-blocking approach has no issues to deliver the files reliably.