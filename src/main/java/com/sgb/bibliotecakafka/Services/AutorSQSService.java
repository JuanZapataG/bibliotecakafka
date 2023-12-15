package com.sgb.bibliotecakafka.Services;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class AutorSQSService {

    private final AmazonSQS sqsClient;

    private String getQueueURL(String queueName){
        return sqsClient.getQueueUrl(queueName).getQueueUrl();
    }

    public List<Message> receiveMessagesFromQueue(String queueName, Integer maxNumberMessages, Integer waitTimeSeconds){
        ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest((this.getQueueURL(queueName)))
                .withMaxNumberOfMessages(maxNumberMessages)
                .withMessageAttributeNames(List.of("All"))
                .withWaitTimeSeconds(waitTimeSeconds);
        return sqsClient.receiveMessage(receiveMessageRequest).getMessages();
    }
}
