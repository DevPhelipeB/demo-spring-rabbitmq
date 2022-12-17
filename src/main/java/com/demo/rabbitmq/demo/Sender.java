package com.demo.rabbitmq.demo;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class Sender {

    private static String NAME_QUEUE = "Hello Rabbit";


    public static void main(String[] args0) throws Exception{

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setUsername("admin");
        factory.setPassword("admin");
        factory.setPort(5672);

        Connection connection = factory.newConnection();
        log.info("Conexão foi estabelecida com hashcode: < " + connection.hashCode() + " >");

        // Criar um novo canal
        Channel channel = connection.createChannel();
        log.info("Canal utilizado é o: " + channel.toString());

        // Declarar a fila que será utilizada
        // nome da fila, exclusiva, autodelete, durable, map(args)
        channel.queueDeclare(NAME_QUEUE, false, false, false, null);

        // Criar a mensagem
        String message = "Hello world, this is my first created Spring RabbitMQ sender";

        // Enviar mensagem
        channel.basicPublish("", NAME_QUEUE, null, message.getBytes());

        log.info("---  [ENDED] --- " + message);
    }
}
