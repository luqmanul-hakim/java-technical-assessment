package com.technical.assessment;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.kafka.test.EmbeddedKafkaBroker;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@EmbeddedKafka(partitions = 1, topics = {"technical.assessment"})
public class AssessmentApplicationTests {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;

    @Test
    public void testKafkaMessageSentAndReceived() {
        // Send the message
        kafkaTemplate.send("technical.assessment", "Kafka is Ready :)");

        // Set up the consumer properties
        var consumerProps = KafkaTestUtils.consumerProps("test-group", "false", embeddedKafkaBroker);
        var consumer = new KafkaConsumer<>(consumerProps, new StringDeserializer(), new StringDeserializer());
        consumer.subscribe(List.of("technical.assessment"));

        // Fetch all records (more than one could be present)
        var records = KafkaTestUtils.getRecords(consumer);

        // Assert that at least one record is consumed
        assertThat(records.count()).isGreaterThanOrEqualTo(1);

        // Perform assertions on each record
        records.forEach(record -> {
            assertThat(record.value()).isEqualTo("Kafka is Ready :)");
        });
    }
}
