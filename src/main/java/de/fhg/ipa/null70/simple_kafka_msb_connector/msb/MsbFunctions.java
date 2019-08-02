package de.fhg.ipa.null70.simple_kafka_msb_connector.msb;

import de.fhg.ipa.null70.simple_kafka_msb_connector.SimpleKafkaMsbConnector;
import de.fhg.ipa.null70.simple_kafka_msb_connector.model.Message;
import de.fhg.ipa.vfk.msb.client.annotation.FunctionCall;
import de.fhg.ipa.vfk.msb.client.annotation.FunctionHandler;
import de.fhg.ipa.vfk.msb.client.annotation.FunctionParam;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Arthur Grigorjan (Fraunhofer IPA)
 */

@Component
@FunctionHandler(path = "/functionHandler")
public class MsbFunctions {

    @Autowired
    SimpleKafkaMsbConnector simpleKafkaMsbConnector;

    private final Logger LOG = LoggerFactory.getLogger(MsbFunctions.class);

    @FunctionCall(path = "/publishToKafka", name = "publishToKafka", description = "Publishes a message to kafka")
    public void publishToKafka(@FunctionParam(name = "publishToKafka") Message message) {
        LOG.info("MSB called /publishToKafka ~ topic = " + message.getTopic() + " ~~~ payload = " + message.getPayload());
        simpleKafkaMsbConnector.kafkaProducer.send(new ProducerRecord<>(message.getTopic(), message.getPayload()));
    }

}
