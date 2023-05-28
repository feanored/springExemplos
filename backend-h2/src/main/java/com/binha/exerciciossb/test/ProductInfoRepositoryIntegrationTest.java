package com.binha.exerciciossb.test;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.binha.exerciciossb.model.entities.ProductInfo;
import com.binha.exerciciossb.model.repositories.ProductInfoRepository;

public class ProductInfoRepositoryIntegrationTest {

	private DynamoDBMapper dynamoDBMapper;

	@Autowired
	private AmazonDynamoDB amazonDynamoDB;

	@Autowired
	ProductInfoRepository repository;

	private static final String EXPECTED_COST = "20";
	private static final String EXPECTED_PRICE = "50";

	public void setup() throws Exception {
		dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);

		CreateTableRequest tableRequest = dynamoDBMapper.generateCreateTableRequest(ProductInfo.class);
		tableRequest.setProvisionedThroughput(new ProvisionedThroughput(1L, 1L));
		amazonDynamoDB.createTable(tableRequest);

		dynamoDBMapper.batchDelete((List<ProductInfo>) repository.findAll());
	}

	public void run() {
		ProductInfo productInfo = new ProductInfo(EXPECTED_COST, EXPECTED_PRICE);
		repository.save(productInfo);
		List<ProductInfo> result = (List<ProductInfo>) repository.findAll();

		System.out.println("Qtde de produtos: " + result.size());
		System.out.println("Preço do primeiro produto: " + result.get(0).getCost());
	}

	public static void main(String[] args) {
		ProductInfoRepositoryIntegrationTest obj = new ProductInfoRepositoryIntegrationTest();
		try {
			obj.setup();
			obj.run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
