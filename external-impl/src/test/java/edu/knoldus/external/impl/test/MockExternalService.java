package edu.knoldus.external.impl.test;

import akka.NotUsed;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import edu.knoldus.external.api.ExternalService;
import edu.knoldus.external.api.Information;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class MockExternalService implements ExternalService {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private String data = "{\n" +
            "  \"userId\": 1,\n" +
            "  \"id\": 1,\n" +
            "  \"title\": \"ABC\",\n" +
            "  \"body\": \"DEF\"\n" +
            "}";

    @Override
    public ServiceCall<NotUsed, Information> getUser() {

        return req -> {
            Information information = null;
            try {
                information = MAPPER.readValue(data,Information.class);

            } catch (IOException ex) {
                System.out.println("Its exception :)\n" + ex.getMessage());

            }
            return CompletableFuture.completedFuture(information);
        };
    }
}
