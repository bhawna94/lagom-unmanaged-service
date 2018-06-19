package edu.knoldus.external.api;

import lombok.Builder;
import lombok.Value;

@Value


public class Information {
    int userId;
    int id;
    String title;
    String body;
}
