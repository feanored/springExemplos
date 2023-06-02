package com.binha.demo.config;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.*;

import java.util.ArrayList;
import java.util.List;

public class TableUtils {

    private final List<String> tables = new ArrayList<>();

    public List<String> getTables() {
        return tables;
    }

    public TableUtils() {
        tables.add("book");
        tables.add("author");
        tables.add("lend");
        tables.add("member");
        tables.add("appUser");
    }

    public void createTable(AmazonDynamoDB clientDB, String tableName) throws InterruptedException {
        DynamoDB dynamoDB = new DynamoDB(clientDB);

        List<AttributeDefinition> attributeDefinitions = new ArrayList<>();
        attributeDefinitions.add(new AttributeDefinition().withAttributeName("id").withAttributeType("S"));

        List<KeySchemaElement> keySchema = new ArrayList<>();
        keySchema.add(new KeySchemaElement().withAttributeName("id").withKeyType(KeyType.HASH));

        CreateTableRequest request = new CreateTableRequest()
                .withTableName(tableName)
                .withKeySchema(keySchema)
                .withAttributeDefinitions(attributeDefinitions)
                .withProvisionedThroughput(new ProvisionedThroughput()
                        .withReadCapacityUnits(5L)
                        .withWriteCapacityUnits(6L));

        Table table = dynamoDB.createTable(request);
        table.waitForActive();
    }

    public void deleteTable(AmazonDynamoDB clientDB, String tableName) {
        clientDB.deleteTable(tableName);
    }
}
