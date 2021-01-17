package com.estoeban.blizzardwowdatatest.service;

import com.estoeban.blizzardwowdatatest.authorization.ApiCrawler;
import com.estoeban.blizzardwowdatatest.models.Character;
import com.estoeban.blizzardwowdatatest.models.CharacterPvpInformation;
import com.estoeban.blizzardwowdatatest.models.character.CharacterGenericPvpInformation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

@Service
@Log4j2
public class WowCharacterInformationServiceImpl implements WowCharacterInformationService {

    final ApiCrawler apiCrawler;

    private String THREES = "3v3";
    private String TWOS = "2v2";
    private String RBG = "rbg";

    @Autowired
    public WowCharacterInformationServiceImpl(ApiCrawler apiCrawler) {
        this.apiCrawler = apiCrawler;
    }

    @Override
    public Character getCharacterInformation(String characterName, String realmName)
        throws IOException, URISyntaxException {

        Map<String, String> params = new HashMap<>();
        params.put("namespace", "profile-us");
        params.put("locale", "en_US");

        return getDataForUrl(String.format("/profile/wow/character/%s/%s", realmName.toLowerCase(), characterName
            .toLowerCase()), params, Character.class);
    }

    @Override
    public CharacterPvpInformation getCharacterPvpInformation(String characterName, String realmName)
        throws IOException, URISyntaxException {

        Character character = getCharacterInformation(characterName, realmName);
        CharacterPvpInformation characterPvpInformation = new CharacterPvpInformation(characterName, realmName);

        CharacterGenericPvpInformation twosInformation = getCharacterRatedPvpInformation(characterName, realmName, TWOS);
        CharacterGenericPvpInformation threesInformation = getCharacterRatedPvpInformation(characterName, realmName, THREES);
        CharacterGenericPvpInformation ratedBGInformation = getCharacterRatedPvpInformation(characterName, realmName, RBG);

        characterPvpInformation.setCovenant(character.getCovenantProgress().getChosenCovenant().getName());
        characterPvpInformation.setTwosRating(twosInformation.getRating());
        characterPvpInformation.setThreesRating(threesInformation.getRating());
        characterPvpInformation.setRbgRating(ratedBGInformation.getRating());

        return characterPvpInformation;
    }

    private CharacterGenericPvpInformation getCharacterRatedPvpInformation(String characterName, String realmName, String bracket)
        throws IOException, URISyntaxException {

        Map<String, String> params = new HashMap<>();
        params.put("namespace", "profile-us");
        params.put("locale", "en_US");

        return getDataForUrl(
            String.format("/profile/wow/character/%s/%s/pvp-bracket/%s",
                realmName.toLowerCase(),
                characterName.toLowerCase(),
                bracket),
            params,
            CharacterGenericPvpInformation.class);
    }

    private <T> T getDataForUrl(final String path, final Map<String, String> params, final Class<T> klazz) throws
        IOException, URISyntaxException {
        try {
            T result = apiCrawler.getDataFromRelativePath(path, params, klazz, true);
            log.trace(String.format("%s", result));
            return result;
        } catch (IOException | URISyntaxException | HttpServerErrorException e) {
            log.error(String.format("%s: %s", e.getClass().getSimpleName(), e.getMessage()));
            throw e;
        }
    }
}
