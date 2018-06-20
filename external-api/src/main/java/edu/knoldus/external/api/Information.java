package edu.knoldus.external.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
@AllArgsConstructor
public class Information {
    int userId;
    int id;
    String title;
    String body;
}
