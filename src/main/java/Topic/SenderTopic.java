package Topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class SenderTopic {
    private static String NAME_EXCHANGE = "Topic Exchange broadcast";


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

        channel.exchangeDeclare(NAME_EXCHANGE, "topic");

        String message = "[TOPIC], Created Spring RabbitMQ sender SYSTEM [TOPIC]";
        String message2 = "2[TOPIC], Created Spring RabbitMQ sender SYSTEM [TOPIC]2";
        String routingKey = "quick.orange.rabbit";
        String routingKey2 = "quick.rabbit";

        channel.basicPublish(NAME_EXCHANGE, routingKey, null, message.getBytes());
        channel.basicPublish(NAME_EXCHANGE, routingKey2, null, message2.getBytes());

        log.info("--- [*] ENDED ----> " + message);
    }
}
