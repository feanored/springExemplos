package com.binha.demo.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConvertedJson;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@DynamoDBTable(tableName = "funcionario")
public class Funcionario {

    @DynamoDBHashKey
    private String funcionarioId;

    @DynamoDBAttribute
    private String nome;

    @DynamoDBAttribute
    @DynamoDBTypeConvertedJson(targetType = Departamento.class)
    private Departamento departamento;

}