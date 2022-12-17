package PublisherConfimation;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Slf4j
public class SenderPubConfirmation {
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
        AMQP.Confirm.SelectOk selectOk = channel.confirmSelect();
        log.info(selectOk.toString());

        // Declarar a EXCHANGE que será utilizada
        // nome da ENCHANGE, exclusiva, autodelete, durable, map(args)
        channel.exchangeDeclare(NAME_EXCHANGE, "fanout");

        // Criar a mensagem
        List<String> message = new ArrayList<String>(3);
        message.add("EXAMPLE PUBLISHER CONFIRMATION, Created Spring RabbitMQ sender [1] - EXCEPTION HANDLE - PUBLISHER CONFIRMATION");
        message.add("EXAMPLE PUBLISHER CONFIRMATION, Created Spring RabbitMQ sender [2] - EXCEPTION HANDLE - PUBLISHER CONFIRMATION");
        message.add("EXAMPLE PUBLISHER CONFIRMATION, Created Spring RabbitMQ sender [3] - EXCEPTION HANDLE - PUBLISHER CONFIRMATION");

        // Enviar messagem com EXCHANGE
        for(int i = 0; i < 3 ; i++){
            String body = message.get(i);
            channel.basicPublish(NAME_EXCHANGE, "", null, body.getBytes());
            log.info("--- [*] SENDED ----> " + body);
            // Wait for 5seconds
            channel.waitForConfirmsOrDie(5_000);
            log.info("<MESSAGE CONFIRMED>");
        }
        log.info("-----------------------------");
        log.info("-----OPERATION FINALIZED-----");
    }
}
