package de.fhg.ipa.null70.simple_kafka_msb_connector.model;

/**
 * @author Arthur Grigorjan (Fraunhofer IPA)
 */
public class Message {

    private String topic;
    private String payload;

    public Message() {
    }

    public Message(String topic, String payload) {
        this.topic = topic;
        this.payload = payload;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }
}
