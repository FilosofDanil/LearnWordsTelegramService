package com.example.mongodbservice.models;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
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
    @NotNull
    private Map<String, String> translations;
    @Field
    @NotNull
    private Map<String, String> definitions;
    @Field
    @NotNull
    private Long userId;
    @Field
    @NotNull
    @Length(max = 3)
    private String langFrom;
    @Field
    @NotNull
    @Length(max = 3)
    private String langTo;
}
