package edu.knoldus.external.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lightbend.lagom.javadsl.api.deser.ExceptionSerializer;
import com.lightbend.lagom.javadsl.api.deser.RawExceptionMessage;
import com.lightbend.lagom.javadsl.api.transport.MessageProtocol;

import java.io.IOException;
import java.util.Collection;

public class ExternalServiceExceptionSerializer implements ExceptionSerializer {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    public static final ExternalServiceExceptionSerializer INSTANCE = new ExternalServiceExceptionSerializer();

    @Override
    public RawExceptionMessage serialize(Throwable exception, Collection<MessageProtocol> accept) {
        return null;
    }

    @Override
    public Throwable deserialize(RawExceptionMessage message) {
        return ExceptionFactory.getInstance(mapExceptionToError(message));
    }

    Error mapExceptionToError(RawExceptionMessage message) {
        Error error;
        try {
            String errorJson = message.messageAsText();
            error = MAPPER.readValue(errorJson, Error.class);
        } catch (IOException exception) {
            error = Error.builder().errorMessage("null").build();
        }
        return error;
    }
}
