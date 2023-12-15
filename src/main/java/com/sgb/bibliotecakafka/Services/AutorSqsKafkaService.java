package com.sgb.bibliotecakafka.Services;

import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.MessageAttributeValue;
import com.sgb.bibliotecakafka.models.AutorAWSSqs;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Slf4j
@AllArgsConstructor
@Service
public class AutorSqsKafkaService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void send(String topico, AutorAWSSqs autores){
        var future = kafkaTemplate.send(topico, autores.getId(), autores.productoToString());

        future.whenComplete((resultadoEnvio, excepcion)->{
            if(excepcion != null){
                log.error(excepcion.getMessage());
                future.completeExceptionally(excepcion);
            } else {
                future.complete(resultadoEnvio);
                log.info("Autor enviado al topico -> " + topico + " en Kafka " + autores.productoToString());
            }
        });
    }


    private List<AutorAWSSqs> transformProductFromAWSSqsToProductoInterno(List<Message> messages) {
        List<AutorAWSSqs> autoresAWSSqs = new LinkedList<>();
        for(Message message: messages){
            Map<String, MessageAttributeValue> atributosMensaje = message.getMessageAttributes();
            AutorAWSSqs autorAWSSqs = new AutorAWSSqs(atributosMensaje);
            autoresAWSSqs.add(autorAWSSqs);
        }
        return autoresAWSSqs;
    }

    public String sendAWSSqsListMessagesToKafka(List<Message> messages, String topico){
        List<AutorAWSSqs> autoresAWSSqs = transformProductFromAWSSqsToProductoInterno(messages);
        for(AutorAWSSqs autorAWSSqs: autoresAWSSqs){
            send(topico, autorAWSSqs);
        }
        return "Se han enviado " + autoresAWSSqs.size() + " productos desde AWSSqs hacia Kafka";
    }
}
