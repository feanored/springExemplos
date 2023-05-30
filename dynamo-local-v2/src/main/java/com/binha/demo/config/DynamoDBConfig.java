package com.binha.demo.config;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.*;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableDynamoDBRepositories(basePackages = "com.binha.demo.repository")
public class DynamoDBConfig {

    @Value("${amazon.dynamodb.endpoint}")
    private String amazonDynamoDBEndpoint;

    @Value("${amazon.aws.region}")
    private String amazonAWSRegion;

    @Value("${amazon.aws.accesskey}")
    private String amazonAWSAccessKey;

    @Value("${amazon.aws.secretkey}")
    private String amazonAWSSecretKey;

    @Bean
    public AmazonDynamoDB amazonDynamoDB(AWSCredentialsProvider awsCredentialsProvider) {
        AmazonDynamoDB amazonDynamoDB
            = AmazonDynamoDBClientBuilder.standard()
            .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(amazonDynamoDBEndpoint, amazonAWSRegion))
            .withCredentials(awsCredentialsProvider).build();
        dropTables(amazonDynamoDB);
        createTables(amazonDynamoDB);
        return amazonDynamoDB;
    }

    @Bean
    public AWSCredentialsProvider awsCredentialsProvider() {
        return new AWSStaticCredentialsProvider(new BasicAWSCredentials(amazonAWSAccessKey, amazonAWSSecretKey));
    }

    public void dropTables(AmazonDynamoDB amazonDynamoDB) {
        try {
            amazonDynamoDB.deleteTable("book");
            amazonDynamoDB.deleteTable("author");
            amazonDynamoDB.deleteTable("lend");
            amazonDynamoDB.deleteTable("member");
            amazonDynamoDB.deleteTable("appUser");
        } catch (Exception ignored) {}
    }

    public void createTables(AmazonDynamoDB amazonDynamoDB) {
        try {
            createTable(amazonDynamoDB, "book");
            createTable(amazonDynamoDB, "author");
            createTable(amazonDynamoDB, "lend");
            createTable(amazonDynamoDB, "member");
            createTable(amazonDynamoDB, "appUser");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void createTable(AmazonDynamoDB clientDB, String tableName) throws InterruptedException {
        DynamoDB dynamoDB = new DynamoDB(clientDB);

        List<AttributeDefinition> attributeDefinitions= new ArrayList<>();
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

}
