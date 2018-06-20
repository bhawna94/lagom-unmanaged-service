package edu.knoldus.external.api;

import akka.NotUsed;
import com.lightbend.lagom.javadsl.api.Descriptor;
import com.lightbend.lagom.javadsl.api.Service;
import com.lightbend.lagom.javadsl.api.ServiceCall;

import static com.lightbend.lagom.javadsl.api.Service.named;
import static com.lightbend.lagom.javadsl.api.transport.Method.GET;
import static com.lightbend.lagom.javadsl.api.transport.Method.POST;

public interface    HelloService extends Service {

    ServiceCall<NotUsed, Information> getInformation(int id);

    //ServiceCall<NotUsed, String> getUserTitle();

   // ServiceCall <NotUsed, String> postInformation();


    @Override
    default Descriptor descriptor() {
        return named("hello").withCalls(
                Service.restCall(GET, "/api/get/:id", this::getInformation)
        ).withAutoAcl(true);
    }
}
