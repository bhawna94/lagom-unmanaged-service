package edu.knoldus.external.impl;

import akka.NotUsed;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.api.deser.ExceptionMessage;
import com.lightbend.lagom.javadsl.api.transport.TransportErrorCode;
import com.lightbend.lagom.javadsl.api.transport.TransportException;
import com.lightbend.lagom.javadsl.persistence.cassandra.CassandraSession;
import edu.knoldus.external.api.ExceptionFactory;
import edu.knoldus.external.api.ExternalService;
import edu.knoldus.external.api.HelloService;
import edu.knoldus.external.api.Information;

import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;

public class HelloServiceImpl implements HelloService {

    private final ExternalService externalService;
    private final CassandraSession cassandraSession;
    private Information info;

    @Inject
    public HelloServiceImpl(ExternalService externalService, CassandraSession cassandraSession) {
        this.externalService = externalService;
        this.cassandraSession = cassandraSession;
    }

    @Override
    public ServiceCall<NotUsed, Information> getInformation() {
        return req-> externalService.getUser().invoke().thenApply(row -> row).exceptionally(throwable -> {
            System.out.println("hahahahah");
            Throwable cause = throwable.getCause();
            System.out.println("\n\n cause is " + cause + "\n\n");
            if (cause instanceof ExceptionFactory.AuthenticationException) {
                System.out.println("hiiiiiiiiiiiiiiiiiii");
                throw new TransportException(TransportErrorCode.InternalServerError,
                        new ExceptionMessage("AuthenticationException", "Authentication is required."));
            }

            throw new TransportException(TransportErrorCode.InternalServerError,
                    new ExceptionMessage("error", cause.getMessage()));
        });


    }


    @Override
    public ServiceCall<NotUsed, String> getUserTitle() {
        return request -> externalService.getUser().invoke().thenApply(Information::getTitle);
    }
}
