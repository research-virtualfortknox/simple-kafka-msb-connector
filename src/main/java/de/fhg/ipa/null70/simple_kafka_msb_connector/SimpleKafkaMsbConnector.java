package de.fhg.ipa.null70.simple_kafka_msb_connector;

import de.fhg.ipa.null70.simple_kafka_msb_connector.model.Message;
import de.fhg.ipa.null70.simple_kafka_msb_connector.msb.MsbConnection;
import de.fhg.ipa.null70.simple_kafka_msb_connector.msb.MsbEvents;
import de.fhg.ipa.vfk.msb.client.api.messages.EventPriority;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;


/**
 * @author Arthur Grigorjan (Fraunhofer IPA)
 */
@Component
public class SimpleKafkaMsbConnector {

    private final static Logger LOG = LoggerFactory.getLogger(SimpleKafkaMsbConnector.class);

    @Value("${kafka.host}")
    public String kafkaHost;

    @Value("${kafka.port}")
    public Integer kafkaPort;

    @Value("${kafka.client.id}")
    public String kafkaClientId;

    @Value("${subscribed-kafka-topics}")
    public String subscribedKafkaTopics;

    @Autowired
    MsbConnection msbConnection;

    public KafkaProducer<Integer, String> kafkaProducer;
    public KafkaConsumer<String, String> kafkaConsumer;

    @PostConstruct
    public void init() {

        LOG.info("------------- CONFIG -------------------");
        LOG.info("kafkaHost = " + kafkaHost);
        LOG.info("kafkaPort = " + kafkaPort);
        LOG.info("kafkaClientId = " + kafkaClientId);
        LOG.info("subscribedKafkaTopics = " + subscribedKafkaTopics);
        LOG.info("----------------------------------------");

        // Init and start kafka producer
        kafkaProducer = initKafkaProducer(kafkaHost, kafkaPort, kafkaClientId);

        // Init and start kafka consumer
        kafkaConsumer = initKafkaConsumer(kafkaHost, kafkaPort, kafkaClientId, subscribedKafkaTopics);

    }

    private KafkaProducer<Integer, String> initKafkaProducer(String kafkaHost, Integer kafkaPort, String kafkaClientId) {
        LOG.trace("Creating Kafka Producer...");
        Properties props = new Properties();
        props.put(ProducerConfig.CLIENT_ID_CONFIG, kafkaClientId);
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaHost + ":" + kafkaPort);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        KafkaProducer<Integer, String> kafkaProducer = new KafkaProducer<>(props);
        LOG.trace("Kafka producer ready to produce...");
        return kafkaProducer;
    }

    private KafkaConsumer<String, String> initKafkaConsumer(String kafkaHost, Integer kafkaPort, String kafkaClientId, String subscribedKafkaTopics) {
        LOG.trace("Creating Kafka Consumer...");
        Properties props = new Properties();
        props.put(ConsumerConfig.CLIENT_ID_CONFIG, kafkaClientId);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaClientId);
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaHost + ":" + kafkaPort);
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, 1000);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);

        consumer.subscribe(Arrays.asList(subscribedKafkaTopics.split(";")));
        LOG.trace("Kafka consumer ready to consume...");

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {

                while (true) {
                    ConsumerRecords<String, String> records = consumer.poll(100);
                    for (ConsumerRecord<String, String> record : records){
                        LOG.info(String.format(">>> offset = %d, key = %s, topic = %s, value = %s%n", record.offset(), record.key(), record.topic(), record.value()));
                        try {
                            msbConnection.getMsbClientHandler().publish(MsbEvents.KAFKA_EVENT, new Message(record.topic(), record.value()), EventPriority.HIGH);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        t.start();

        return consumer;
    }

}
