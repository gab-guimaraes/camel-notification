package route;

import model.Notification;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;

public class KafkaConsumerRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("kafka:{{kafka.topic}}?brokers={{kafka.host}}:{{kafka.port}}&groupId={{kafka.group.id}}")
                .unmarshal().json(JsonLibrary.Jackson, Notification.class)
                .process(exchange -> {
                    Notification notification = exchange.getIn().getBody(Notification.class);
                    System.out.println("Received message on Kafka: " + notification.toString());
                })
                .log("Received message on Kafka: ${body}");
    }


}
