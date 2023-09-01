package com.example.mongodbservice.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Map;

@Getter
@Setter
@Builder
@Document
public class TranslatedList {
    @Id
    private String id;
    @Field
    private Map<String, String> translations;
    @Field
    private Long userId;
}
