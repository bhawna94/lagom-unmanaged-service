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
    public ServiceCall<NotUsed, Information> getInformation() {
        info = externalService.getUser().invoke().thenApply(row -> row).toCompletableFuture().join();
        return request -> CompletableFuture.completedFuture(info);
    }

    @Override
    public ServiceCall<NotUsed, String> postInformation() {
        info = externalService.getUser().invoke().thenApply(row -> row).toCompletableFuture().join();

        return request -> cassandraSession.executeWrite("insert into user.external (user_id, id, title, body) values(?,?,?,?)",
                info.getUserId(),info.getId(),info.getTitle(),info.getBody()).thenApply(NotUsed-> "inserted");
//        return request -> cassandraSession.executeWrite(Operation.addUser(),info.thenApply(row->{
//            row.getUserId(),
//            row.getBody();
//            row.getTitle();
//            row.getId();
//        }));

    }

    @Override
    public ServiceCall<NotUsed, String> getUserTitle() {
        return request -> externalService.getUser().invoke().thenApply(Information::getTitle);
    }
}
