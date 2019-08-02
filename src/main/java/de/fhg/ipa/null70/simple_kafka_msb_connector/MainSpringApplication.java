package de.fhg.ipa.null70.simple_kafka_msb_connector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author Arthur Grigorjan (Fraunhofer IPA)
 */

@Configuration
@ComponentScan(basePackages = { "de.fhg.ipa.null70.simple_kafka_msb_connector"})
@SpringBootApplication
@Component
public class MainSpringApplication {

    private static final Logger LOG = LogManager.getLogger(MainSpringApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(MainSpringApplication.class, args);
    }

}
