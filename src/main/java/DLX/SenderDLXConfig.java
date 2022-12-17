package DLX;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;



@Slf4j
public class SenderDLXConfig {
    private static String NAME_EXCHANGE = "MAIN_EXCHANGE";


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

        String message = "---- EXAMPLE DLX TTL ----";
        String routingKey = "BK_CONSUMER.test";


        channel.basicPublish(NAME_EXCHANGE, routingKey, null, message.getBytes());

        log.info("-----------------------------");
        log.info("-----OPERATION FINALIZED-----");
        log.info("-----------------------------");
    }
}
