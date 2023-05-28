package com.binha.demo.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.binha.demo.model.Funcionario;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class FuncionarioDynamoRepo {

    private DynamoDBMapper dynamoDBMapper;

    public List<Funcionario> findAll() {
        return dynamoDBMapper.scan(Funcionario.class, new DynamoDBScanExpression());
    }

    public Funcionario getById(String funcionarioId) {
        return dynamoDBMapper.load(Funcionario.class, funcionarioId);
    }

    public Funcionario save(Funcionario funcionario) {
        dynamoDBMapper.save(funcionario);
        return funcionario;
    }

    public String delete(String funcionarioId) {
        Funcionario funcionario = getById(funcionarioId);
        if (funcionario == null)
            return  String.format("Não existe um funcionário com o Id %s.", funcionarioId);

        dynamoDBMapper.delete(funcionario);
        return String.format("Funcionário de Id: %s deletado!!", funcionarioId);
    }

    public String update(String funcionarioId, Funcionario funcionario) {
        dynamoDBMapper.save(funcionario,
                new DynamoDBSaveExpression().withExpectedEntry("funcionarioId",
                        new ExpectedAttributeValue(new AttributeValue().withS(funcionarioId))));
        return funcionario.getFuncionarioId();
    }
}
