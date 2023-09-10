package com.example.mongodbservice.models;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    private List<Test> tests;
    @Field
    private Date testDate;
    @Field
    private Long userId;
    @Field
    private Integer passedTimes;
    @Field
    private String listId;
    @Field
    private Boolean notified;
    @Field
    private Boolean firstNotify;
}
