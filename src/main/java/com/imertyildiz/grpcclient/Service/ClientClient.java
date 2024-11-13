package com.imertyildiz.grpcclient.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.imertyildiz.grpcproto.HelloWorldRequest;
import com.imertyildiz.grpcproto.HelloWorldResponse;
import com.imertyildiz.grpcproto.HelloWorldServiceGrpc.HelloWorldServiceBlockingStub;

import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.web.client.RestTemplate;

@Service
public class ClientClient {
    private static final Logger logger = LoggerFactory.getLogger(ClientClient.class);

    RestTemplate restTemplate = new RestTemplate();
    String url = "http://localhost:8099/api/rest/send-message-rest";

    @GrpcClient("grpc-server")
    private HelloWorldServiceBlockingStub helloWorldServiceStub;

    public void sayHello(String msg, String sender, String message) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            HelloWorldRequest helloWorldRequest = HelloWorldRequest.newBuilder().setClientName(sender)
                    .setRequestMessage(message+" "+i).build();
            HelloWorldResponse helloWorldResponse = this.helloWorldServiceStub.helloWorld(helloWorldRequest);
            logger.info(String.format("Server sent a response: %1s", helloWorldResponse.getResponseMessage()));
        }
        long end = System.currentTimeMillis();
        long totalTime =  end - start;
        System.out.println("GRPC TIME: "+totalTime/1000 + " seconds");
    }

    public void sayHelloFromRest(String msg){
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            ResponseEntity<String> response = restTemplate.postForEntity(url, msg, String.class);
        }
        long end = System.currentTimeMillis();
        long totalTime =  end - start;
        System.out.println("REST TIME: "+totalTime/1000 + " seconds");
    }

    @Async
    public void restVsGrpc(String msg){
        sayHello(msg, "Client", "Start!");
        sayHelloFromRest(msg);
    }

}