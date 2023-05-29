package com.binha.demo.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import lombok.*;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@DynamoDBDocument
public class Departamento {

    @DynamoDBAttribute
    private String nome;

    @DynamoDBAttribute
    private String codigo;

}
