package edu.knoldus.external.impl;

import akka.NotUsed;
import com.lightbend.lagom.javadsl.api.transport.TransportException;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.api.deser.ExceptionMessage;
import com.lightbend.lagom.javadsl.api.transport.TransportErrorCode;
import com.lightbend.lagom.javadsl.persistence.cassandra.CassandraSession;
import edu.knoldus.external.api.ExceptionFactory;
import edu.knoldus.external.api.ExternalService;
import edu.knoldus.external.api.HelloService;
import edu.knoldus.external.api.Information;

import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

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
        info = externalService.getUser().invoke().thenApply(row -> row).toCompletableFuture().join();
        System.out.println("....................hi.......................");
        return request -> CompletableFuture.completedFuture(info).exceptionally(throwable -> {
            Throwable cause = throwable.getCause();

            if (cause instanceof ExceptionFactory.AuthenticationException)
                throw new TransportException(TransportErrorCode.InternalServerError,
                        new ExceptionMessage("AuthenticationException","Authentication is required."));
            throw new TransportException(TransportErrorCode.InternalServerError,
                    new ExceptionMessage("error",cause.getMessage()));
        });
    }


    @Override
    public ServiceCall<NotUsed, String> getUserTitle() {
        return request -> externalService.getUser().invoke().thenApply(Information::getTitle);
    }
}
