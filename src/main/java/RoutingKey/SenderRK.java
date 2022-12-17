package RoutingKey;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class SenderRK {

    private static String ROUTING_KEY = "RoutingKeyTest";

    private static String ROUTING_KEY_2 = "SecondRoutingKeyTest";
    private static String NAME_EXCHANGE = "DIRECT Exchange broadcast";


    public static void main(String[] args0) throws Exception{

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setUsername("admin");
        factory.setPassword("admin");
        factory.setPort(5672);

        Connection connection = factory.newConnection();
        log.info("Conexão foi estabelecida com hashcode: < " + connection.hashCode() + " >");

        Channel channel = connection.createChannel();
        log.info("Canal utilizado é o: " + channel.toString());

        channel.exchangeDeclare(NAME_EXCHANGE, "direct");

        String message = "[ROUTING_KEY], Created Spring RabbitMQ sender SYSTEM ROUTING KEY";
        String message2 = "[ROUTING_KEY - 2 ], Created Spring RabbitMQ sender SYSTEM ROUTING KEY [2] ";

        // Enviar messagem com EXCHANGE
        channel.basicPublish(NAME_EXCHANGE, ROUTING_KEY, null, message.getBytes());

        channel.basicPublish(NAME_EXCHANGE, ROUTING_KEY_2, null, message2.getBytes());

        log.info("--- [*] SENDED ----> " + message);
        log.info("--- [*] SENDED ----> " + message2);
    }
}
