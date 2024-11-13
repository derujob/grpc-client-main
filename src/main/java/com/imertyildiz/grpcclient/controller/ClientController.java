package com.imertyildiz.grpcclient.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.imertyildiz.grpcclient.Service.ClientClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@EnableAsync
@RestController
@RequestMapping("/grpc")
public class ClientController {

    @Autowired(required = false)
    private ClientClient clientClient;

    @PostMapping("/send-message")
    public void sendMessage(@RequestBody String msg) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNodeRoot = objectMapper.readTree(msg);
        String str = jsonNodeRoot.get("msg").asText();
    }

    @PostMapping("/send-message2")
    public void sendMessageFromRest(@RequestBody String msg) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNodeRoot = objectMapper.readTree(msg);
        String str = jsonNodeRoot.get("msg").asText();
        System.out.println(str);
    }


    @PostMapping("/send")
    public void restVsGrpc(@RequestBody String msg){
        clientClient.restVsGrpc(msg);
    }

}
