package DLX;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

@Slf4j
public class DLXConfig {
    // DLX
    private static final String DLX_NAME = "DLX_EXCHANGE";
    private static final String DLX_QUEUE = "DLX_QUEUE";
    private static final String DLX_BINDING_KEY = "DLX_RK";
    // EXCHANGE MAIN
    private static final String EXCHANGE_NAME = "MAIN_EXCHANGE";
    // CONSUMER
    private static final String CONSUMER_QUEUE = "QUEUE_CONSUMER";
    private  static final String CONSUMER_BINDING_KEY = "BK_CONSUMER";


    public static void main(String[] args) throws IOException, TimeoutException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setUsername("admin");
        factory.setPassword("admin");
        factory.setPort(5672);
        Connection connection = factory.newConnection();
        log.info("Conexão foi estabelecida com hashcode: < " + connection.hashCode() + " >");
        Channel channel = connection.createChannel();
        log.info("Canal utilizado é o: " + channel.toString());

        // Declarar as exchanges (main e dlx)
        channel.exchangeDeclare(DLX_NAME, "topic");
        channel.exchangeDeclare(EXCHANGE_NAME, "topic");

        // Declarar as filas
        channel.queueDeclare(DLX_QUEUE, false, false, false, null);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("x-message-ttl", 10000);
        map.put("x-dead-letter-exchange", DLX_NAME);
        map.put("x-dead-letter-routing-key", DLX_BINDING_KEY);
        channel.queueDeclare(CONSUMER_QUEUE, false, false, false, map);

        // Binding Key DLX e CONSUMER
        channel.queueBind(DLX_QUEUE, DLX_NAME, DLX_BINDING_KEY+".#");
        channel.queueBind(CONSUMER_QUEUE, EXCHANGE_NAME, CONSUMER_BINDING_KEY+".#");

        connection.close();
        log.info("-CONEXÃO FINALIZADA COM SUCESSO-");

    }
}
