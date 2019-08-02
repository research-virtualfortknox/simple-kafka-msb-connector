#!/usr/bin/env bash

# Read arguments
application_properties_file=${1}

KAFKA_HOST=${2}
KAFKA_PORT=${3}
KAFKA_CLIENT_ID=${4}


SUBSCRIBED_KAFKA_TOPICS=${5}


echo "Configuration:"
echo "Application properties file: $application_properties_file"
echo "KAFKA_HOST: $KAFKA_HOST"
echo "KAFKA_PORT: $KAFKA_PORT"
echo "KAFKA_CLIENT_ID: $KAFKA_CLIENT_ID"
echo "SUBSCRIBED_KAFKA_TOPICS: SUBSCRIBED_KAFKA_TOPICS"

# overwrite properties
sed -i "/kafka.host/c\kafka.host = $KAFKA_HOST" ${application_properties_file}
sed -i "/kafka.port/c\kafka.port = $KAFKA_PORT" ${application_properties_file}
sed -i "/kafka.client.id/c\kafka.client.id = $KAFKA_CLIENT_ID" ${application_properties_file}


sed -i "/subscribed-kafka-topics/c\subscribed-kafka-topics = SUBSCRIBED_KAFKA_TOPICS" ${application_properties_file}
