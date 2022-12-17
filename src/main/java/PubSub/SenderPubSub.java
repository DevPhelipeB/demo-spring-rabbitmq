package PubSub;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class SenderPubSub {
    private static String NAME_EXCHANGE = "FANOUT Exchange broadcast";


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

        channel.exchangeDeclare(NAME_EXCHANGE, "fanout");

        String message = "[PUB/SUB], Created Spring RabbitMQ sender SYSTEM PUB/SUB";

        channel.basicPublish(NAME_EXCHANGE, "", null, message.getBytes());

        log.info("--- [*] ENDED ----> " + message);
    }
}
