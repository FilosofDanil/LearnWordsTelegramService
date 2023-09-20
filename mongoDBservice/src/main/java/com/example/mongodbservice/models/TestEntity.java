package com.example.mongodbservice.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;

@Builder
@Getter
@Setter
@Document
public class TestEntity {
    @Id
    private String id;
    @Field
    @NotNull
    private List<Test> tests;
    @Field
    @NotNull
    private Date testDate;
    @Field
    @NotNull
    private Long userId;
    @Field
    @NotNull
    @Min(0)
    @Max(8)
    private Integer passedTimes;
    @Field
    @NotNull
    private String listId;
    @Field
    @NotNull
    private Boolean notified;
    @Field
    @NotNull
    private Boolean firstNotify;
    @Field
    @NotNull
    private Boolean testReady;
}
