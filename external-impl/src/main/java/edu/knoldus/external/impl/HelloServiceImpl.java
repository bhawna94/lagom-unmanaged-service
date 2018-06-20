package edu.knoldus.external.impl;

import akka.NotUsed;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.persistence.cassandra.CassandraSession;
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
    public ServiceCall<NotUsed, Information> getInformation(int id) {
        info = externalService.getUser(id).invoke().thenApply(row -> row).toCompletableFuture().join();
        return request -> CompletableFuture.completedFuture(info);
    }




}
