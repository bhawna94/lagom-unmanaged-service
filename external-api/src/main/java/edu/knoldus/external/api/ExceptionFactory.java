package edu.knoldus.external.api;

import org.apache.commons.lang3.StringUtils;

public class ExceptionFactory {
    public static Throwable getInstance(Error error) {
        if (error == null) {
            return null;
        }
        return determineException(error);


    }

    private static Throwable determineException(Error error) {

        String errorMessage = error.getErrorMessage();
        if (StringUtils.isNotEmpty(errorMessage) && errorMessage.contains("Requires authentication")) {

            return new AuthenticationException(error);
        }
        return new GenericException(error);

    }


    public static class AuthenticationException extends RuntimeException {
        Error error;

        public AuthenticationException(Error error) {
            this.error = error;
        }
    }

    public static class GenericException extends RuntimeException {
        Error error;


        public GenericException(Error error) {

            this.error = error;
        }
    }
}

