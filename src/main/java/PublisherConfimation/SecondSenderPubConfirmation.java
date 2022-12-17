package PublisherConfimation;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class SecondSenderPubConfirmation {
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
        String message = "This is my message ... ";
        int setOfMessages = 10;
        int outMessages = 0;
        String bodyMessage;

        for(int i = 1; i <= setOfMessages ; i++){
            bodyMessage = message + i;
            channel.basicPublish(NAME_EXCHANGE, "", null, bodyMessage.getBytes());
            log.info("--- [*] SENDED ----> " + bodyMessage);
            outMessages++;

            if (outMessages == setOfMessages) {
                // Wait for 5seconds
                channel.waitForConfirmsOrDie(10_000);
                log.info("<MESSAGE CONFIRMED>");
                outMessages = 0;
            }

        }
        if (outMessages != 0) {
            channel.waitForConfirmsOrDie(5_000);
            log.info("<MESSAGE CONFIRMED>");
        }

        log.info("-----------------------------");
        log.info("-----OPERATION FINALIZED-----");
        log.info("-----------------------------");
    }
}
