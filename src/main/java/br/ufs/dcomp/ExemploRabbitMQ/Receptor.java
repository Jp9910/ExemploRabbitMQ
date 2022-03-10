package br.ufs.dcomp.ExemploRabbitMQ;

import com.rabbitmq.client.*;

import java.io.IOException;

public class Receptor {

  private final static String QUEUE_NAME = "minha-fila";

  public static void main(String[] argv) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("3.80.41.145"); // Alterar
    factory.setUsername("jp"); // Alterar
    factory.setPassword("9910"); // Alterar
    factory.setVirtualHost("/");
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

                      //(queue-name, durable, exclusive, auto-delete, params); 
    channel.queueDeclare(QUEUE_NAME, false,   false,     false,       null);
    
    System.out.println(" [*] Esperando recebimento de mensagens...");

    Consumer consumer = new DefaultConsumer(channel) 
    {
        public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException 
        {
          //O QUE O RECEPTOR FARÁ QUANDO CHEGAR UMA MENSAGEM. "body" é o conteúdo da mensagem (representado em bytes)
          String message = new String(body, "UTF-8"); //transformar em string
          System.out.println(" [x] Mensagem recebida: '" + message + "'"); //imprimir
  
                          //(deliveryTag,               multiple);
          //channel.basicAck(envelope.getDeliveryTag(), false);
        }
    };
                      //(queue-name, autoAck, consumer);    
    channel.basicConsume(QUEUE_NAME, true, consumer); //NOME DA FILA QUE O RECEPTOR "consumer" IRÁ OUVIR
  }
}