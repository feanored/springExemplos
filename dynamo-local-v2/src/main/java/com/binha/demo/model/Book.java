package com.binha.demo.model;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@DynamoDBTable(tableName = "book")
public class Book{

    @DynamoDBHashKey
    @DynamoDBAutoGeneratedKey
    private String id;

    @DynamoDBAttribute
    private String name;

    @DynamoDBAttribute
    private String isbn;

    @DynamoDBAttribute
    private String authorId;

//    @Override
//    public boolean isNew() {
//        return id == null;
//    }
}
