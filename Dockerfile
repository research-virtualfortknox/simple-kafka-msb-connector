FROM openjdk:8u181-jdk-slim

ENV SERVER_INSTALL_FOLDER=/app/kafka-msb-connector/
ENV JAR_FILE_NAME=simple_kafka_msb_connector-0.0.1-SNAPSHOT.jar

# Application propertis
ENV KAFKA_HOST=localhosts
ENV KAFKA_PORT=9092
ENV KAFKA_CLIENT_ID=testing-kafka-producer-1

ENV SUBSCRIBED_KAFKA_TOPICS=topicA;topicB;topicC

RUN mkdir -p "${SERVER_INSTALL_FOLDER}log"

#SERVER:
ADD src/main/resources/application.properties ${SERVER_INSTALL_FOLDER}
ADD target/${JAR_FILE_NAME} ${SERVER_INSTALL_FOLDER}

ADD docker/setConfiguration.sh ${SERVER_INSTALL_FOLDER}

WORKDIR ${SERVER_INSTALL_FOLDER}


CMD bash ${SERVER_INSTALL_FOLDER}setConfiguration.sh "${SERVER_INSTALL_FOLDER}application.properties" \
                                  "$KAFKA_HOST" "$KAFKA_PORT" "$KAFKA_CLIENT_ID" \
                                  "$SUBSCRIBED_KAFKA_TOPICS" \
    && java -jar -Xmx1024m -Xms512m ${JAR_FILE_NAME}

