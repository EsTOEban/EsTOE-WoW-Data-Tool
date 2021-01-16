package com.estoeban.blizzardwowdatatest.repositories;

import com.estoeban.blizzardwowdatatest.models.Character;
import org.junit.After;
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

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest()
@WebAppConfiguration
@ActiveProfiles("local")
@TestPropertySource(properties = {
        "amazon.dynamodb.endpoint=http://localhost:8000/",
        "amazon.aws.accesskey=test1",
        "amazon.aws.secretkey=test231" })
public class CharacterInfoRepositoryTest {

    @Autowired
    CharacterInfoRepository repository;

    private static final String EXPECTED_COST = "20";
    private static final String EXPECTED_PRICE = "50";

    @After
    public void tearDown() throws Exception {
        repository.deleteAll();
    }

    @Before
    public void setup() {
        Character character = new Character();
        character.setName("BeepBoopTest");
        character.setLevel(60);

        CharacterInfo characterInfo = new CharacterInfo();
        characterInfo.setCharacter(character);
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
