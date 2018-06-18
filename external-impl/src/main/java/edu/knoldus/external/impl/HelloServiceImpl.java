package edu.knoldus.external.impl;

import akka.NotUsed;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import edu.knoldus.external.api.ExternalService;
import edu.knoldus.external.api.HelloService;
import edu.knoldus.external.api.Information;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;

public class HelloServiceImpl implements HelloService {

    ExternalService externalService;

    @Inject
    public HelloServiceImpl(ExternalService externalService) {
        this.externalService = externalService;
    }

    @Override
    public ServiceCall<NotUsed, Information> getInformation() {
        CompletionStage<Information> info = externalService.getUser().invoke().thenApply(row -> row);
        return request -> info;
    }

    @Override
    public ServiceCall<NotUsed, String> getUserTitle() {
        return request -> externalService.getUser().invoke().thenApply(userDetail -> userDetail.getTitle());
    }
}
