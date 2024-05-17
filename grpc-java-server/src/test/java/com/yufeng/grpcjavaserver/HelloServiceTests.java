package com.yufeng.grpcjavaserver;

import com.yufeng.grpcjavastub.HelloRequest;
import com.yufeng.grpcjavastub.HelloResponse;
import com.yufeng.grpcjavastub.HelloServiceGrpc;
import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.inprocess.InProcessServerBuilder;
import io.grpc.testing.GrpcCleanupRule;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class HelloServiceTests {

    @Rule
    public final GrpcCleanupRule grpcCleanup = new GrpcCleanupRule();

    @Test
    public void helloTest() throws IOException {
        String firstName = "Yufeng";
        String lastName = "Fan";

        //server
        HelloServiceImpl service = new HelloServiceImpl();
        String serverName = InProcessServerBuilder.generateName();
        grpcCleanup.register(InProcessServerBuilder
                .forName(serverName)
                .directExecutor()
                .addService(service)
                .build()
                .start());

        //stub
        HelloServiceGrpc.HelloServiceBlockingStub stub = HelloServiceGrpc.newBlockingStub(
                grpcCleanup.register(InProcessChannelBuilder.forName(serverName).directExecutor().build()));

        //response
        HelloResponse response = stub.hello(
                HelloRequest.newBuilder().setFirstName(firstName).setLastName(lastName).build());

        //assert
        String greeting = new StringBuilder()
                .append("Hello, ")
                .append(firstName)
                .append(" ")
                .append(lastName)
                .toString();
        assertEquals(greeting, response.getGreeting());
    }
}
