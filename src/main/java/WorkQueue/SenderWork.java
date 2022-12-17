package WorkQueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class SenderWork {

    private static String NAME_QUEUE = "Work Queue";


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

        channel.queueDeclare(NAME_QUEUE, false, false, false, null);

        String message = "WORK QUEUE, Created Spring RabbitMQ sender";

        channel.basicPublish("", NAME_QUEUE, null, message.getBytes());

        log.info("---  ENDED ----> " + message);
    }
}
