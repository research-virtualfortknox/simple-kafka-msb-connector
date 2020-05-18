# A simple MSB to Apache Kafka Connector 
[![FOSSA Status](https://app.fossa.com/api/projects/git%2Bgithub.com%2Fresearch-virtualfortknox%2Fsimple-kafka-msb-connector.svg?type=shield)](https://app.fossa.com/projects/git%2Bgithub.com%2Fresearch-virtualfortknox%2Fsimple-kafka-msb-connector?ref=badge_shield)

This application writes MSB events into Kafka and publishes Kafka messages as MSB events to the Manufacturing Service Bus.

## Usage

#### Prerequisites
* Java 8 or higher

#### Configuration

* Edit the application.properties-file

* kafka.host = IP THROUGH WHICH THE KAFKA BROKER WILL BE REACHED [HOSTNAME|IP]
* kafka.port = [INTEGER]
* kafka.client.id = [STRING]
* subscribed-kafka-topics = kafka topics which will be subscribed (separated by a ; )

#### How to build

* Run: `mvn clean install`


#### How to run

* Place the jar with dependencies and your edited application.properties-file in the same directory

* Open a bash or CMD in the same directory as the .jar

* Run: `java -jar simple_kafka_msb_connector-0.0.1-SNAPSHOT.jar`


## Usage with Docker

* Docker Hub https://hub.docker.com/r/arthurgrigo/simple-kafka-msb-connector

* Run (edit environment variables to your needs!) : `docker run -d -t -i -e KAFKA_HOST='KAFKA' -e KAFKA_PORT=9092 -e KAFKA_CLIENT_ID='testing-kafka-producer-1' -e SUBSCRIBED_KAFKA_TOPICS='topicA;topicB;topicC' --name simple-kafka-msb-connector arthurgrigo/simple-kafka-msb-connector:latest`


## Usage with Docker-Compose

#### Standalone

* [docker-compose.yml](docker-compose/standalone/docker-compose.yml)

* Run: `docker-compose up -d`


## License
See [LICENSE](LICENSE) file for License

[![FOSSA Status](https://app.fossa.com/api/projects/git%2Bgithub.com%2Fresearch-virtualfortknox%2Fsimple-kafka-msb-connector.svg?type=large)](https://app.fossa.com/projects/git%2Bgithub.com%2Fresearch-virtualfortknox%2Fsimple-kafka-msb-connector?ref=badge_large)