package edu.knoldus.external.api;

import akka.NotUsed;
import com.lightbend.lagom.javadsl.api.Descriptor;
import com.lightbend.lagom.javadsl.api.Service;
import com.lightbend.lagom.javadsl.api.ServiceCall;

import static com.lightbend.lagom.javadsl.api.Service.named;
import static com.lightbend.lagom.javadsl.api.transport.Method.GET;

public interface ExternalService extends Service {

    ServiceCall<NotUsed, Information> getUser(int id);

    @Override
    default Descriptor descriptor() {
        return named("external-service").withCalls(
                Service.restCall(GET, "/posts/:id", this::getUser)
        ).withAutoAcl(true);

    }
}
