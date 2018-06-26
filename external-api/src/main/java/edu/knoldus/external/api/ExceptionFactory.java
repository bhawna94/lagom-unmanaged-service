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

        String errorMessage = error.getMessage();
        if (StringUtils.isNotEmpty(errorMessage) && errorMessage.contains("Requires authentication")) {
            System.out.println("\n............bhawna......\n");
            return new AuthenticationException(error);
        }
        return new GenericException(error);

    }


    public static class AuthenticationException extends RuntimeException {
        Error error;

        public AuthenticationException(Error error) {
            this.error = error;
            System.out.println("\n\n erroe is.... "+this.error+"\n\n\n");
        }
    }

    public static class GenericException extends RuntimeException {
        Error error;


        public GenericException(Error error) {

            this.error = error;
        }
    }
}

