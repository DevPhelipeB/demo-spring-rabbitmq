package DLX;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReceiverDLX {

    private static final String CONSUMER_QUEUE = "QUEUE_CONSUMER";

    public static void main(String[] args0) throws Exception{

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setUsername("admin");
        factory.setPassword("admin");
        factory.setPort(5672);

        // Realiza a conexão e gera um hashcode dela
        Connection connection = factory.newConnection();
        log.info("Conexão foi estabelecida com hashcode: < " + connection.hashCode() + " >");

        // Criar um novo canal
        Channel channel = connection.createChannel();
        log.info("Canal utilizado é o: " + channel.toString());

        // Callback - O que vou receber
        DeliverCallback deliverCallback = (ConsumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            log.info("Received Message With: " + message);
        };

//        // Consumindo messagem *** MODO ON/OFF ***
//        channel.basicConsume(CONSUMER_QUEUE, true, deliverCallback, ConsumerTag -> {});
//        log.info("PREPARADO PARA CONSUMO [[DLX]]");
    }

}
