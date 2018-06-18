package edu.knoldus.external.impl;

import com.google.inject.AbstractModule;
import com.lightbend.lagom.javadsl.server.ServiceGuiceSupport;
import edu.knoldus.external.api.ExternalService;
import edu.knoldus.external.api.HelloService;

public class HelloModule extends AbstractModule implements ServiceGuiceSupport {


    @Override
    protected void configure() {
        bindService(HelloService.class, HelloServiceImpl.class);
        bindClient(ExternalService.class);
    }
}
