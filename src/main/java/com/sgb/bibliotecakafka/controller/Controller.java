package com.sgb.bibliotecakafka.controller;

import com.amazonaws.services.sqs.model.Message;
import com.sgb.bibliotecakafka.Services.AutorSQSService;
import com.sgb.bibliotecakafka.Services.AutorSqsKafkaService;
import com.sgb.bibliotecakafka.models.Topicos;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/bibloteca/autores")
@AllArgsConstructor
public class Controller {

private AutorSQSService autorSQSService;
private AutorSqsKafkaService autorSqsKafkaService;
    @PostMapping("/sqs")
    public String enviarProductoDesdeSQSHaciaKafka(){

        List<Message> awsSqsMessages = autorSQSService.receiveMessagesFromQueue("productos-aguinaldos", 10, 10);
        return autorSqsKafkaService.sendAWSSqsListMessagesToKafka(awsSqsMessages, String.valueOf(Topicos.AUTORES_SQS));
    }
}
