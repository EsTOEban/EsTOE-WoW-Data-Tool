package com.estoeban.blizzardwowdatatest.repositories;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.Data;

@Data
@DynamoDBTable(tableName = "CharacterInfo")
public class CharacterInfo {

    @DynamoDBHashKey
    @DynamoDBAttribute
    private Integer id;

    @DynamoDBAttribute
    private Character character;
}
