package de.fhg.ipa.null70.simple_kafka_msb_connector.msb;

import de.fhg.ipa.null70.simple_kafka_msb_connector.model.Message;
import de.fhg.ipa.vfk.msb.client.annotation.EventDeclaration;
import org.springframework.stereotype.Component;

/**
 * @author Arthur Grigorjan (Fraunhofer IPA)
 */
@Component
@de.fhg.ipa.vfk.msb.client.annotation.Events({
        @EventDeclaration(dataType = Message.class, description = "This event will be thrown when a message comes in through a published kafka topic.",
                name = MsbEvents.KAFKA_EVENT, eventId = MsbEvents.KAFKA_EVENT),

        @EventDeclaration(dataType = Message.class, description = "This event will be consumed by the msb function when a kafka message has been consumed.",
                name = MsbEvents.MSB_EVENT, eventId = MsbEvents.MSB_EVENT),

})
public class MsbEvents {

    public final static String MSB_EVENT = "MSB_EVENT";

    public final static String KAFKA_EVENT= "KAFKA_EVENT";

}

