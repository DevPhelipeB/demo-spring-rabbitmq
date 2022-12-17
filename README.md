# demo-spring-rabbitmq
Utilizando Spring para criar um ambiente de comunicação assíncrona com o sistema RabbitMQ

## Estrutura geral 
- demo - exemplo simplificado de troca de mensagens em sistema de filas "puro";
- WorkQueue - simulando processamento decorrente de mensagens com payload "pesado" (ex:simulando imagens);
- Pub/Sub - comunicação utilizando a intermediação da exchange do tipo fanout;
- RoutingKey - defindo labels através da routing key;
- Topic - utilizando exchange do tipo topic na comunicação;
- PublisherConfirmation - utilizando confirmação de envio de mensagens do produtor à exchange;
- DLX - Criando e utilizando uma Dead Letter Exchange para mensagens com TTL expirado.
