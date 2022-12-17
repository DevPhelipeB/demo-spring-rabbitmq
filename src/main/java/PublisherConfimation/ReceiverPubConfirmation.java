package PublisherConfimation;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReceiverPubConfirmation {
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

        // Declarar a fila que será utilizada
        // nome da fila, exclusiva, autodelete, durable, map(args)
        String nameQueue = channel.queueDeclare().getQueue(); // nome randomico para fila temporaria

        // Declaração da EXCHANGE
        channel.exchangeDeclare(NAME_EXCHANGE, "fanout");

        // Declaração da BIND
        channel.queueBind(nameQueue, NAME_EXCHANGE, "");

        DeliverCallback deliverCallback = (ConsumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            log.info("Received Message With: " + message);
        };

        channel.basicConsume(nameQueue, true, deliverCallback, ConsumerTag -> {});
        log.info("[ REALIZADO CONSUMO ]");
    }
}
