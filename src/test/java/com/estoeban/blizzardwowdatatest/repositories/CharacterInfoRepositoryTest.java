package com.estoeban.blizzardwowdatatest.repositories;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

//import static org.hamcrest.core.Is.is;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest()
@WebAppConfiguration
@ActiveProfiles("local")
@TestPropertySource(properties = {
        "amazon.dynamodb.endpoint=http://localhost:8000/",
        "amazon.aws.accesskey=test1",
        "amazon.aws.secretkey=test231" })
public class CharacterInfoRepositoryTest {

    private DynamoDBMapper dynamoDBMapper;

    @Autowired
    private AmazonDynamoDB amazonDynamoDB;

    @Autowired
    CharacterInfoRepository repository;

    private static final String EXPECTED_COST = "20";
    private static final String EXPECTED_PRICE = "50";

    @Before
    public void setup() {
        dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);

        CreateTableRequest tableRequest = dynamoDBMapper.generateCreateTableRequest(CharacterInfo.class);
        tableRequest.setProvisionedThroughput(new ProvisionedThroughput(1L, 1L));
        amazonDynamoDB.createTable(tableRequest);

        //...

        dynamoDBMapper.batchDelete(repository.findAll());
    }

    @Test
    public void givenDefaultCharacterInfo_whenRunFindAll_thenItemIsFound() {
        CharacterInfo characterInfo = new CharacterInfo();
        repository.save(characterInfo);
        List<CharacterInfo> result = (List<CharacterInfo>) repository.findAll();

        assertThat(result.size(), is(greaterThan(0)));
        assertThat(result.get(0).getCharacter(), is(equalTo(characterInfo.getCharacter())));
    }
}
