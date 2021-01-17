package com.estoeban.blizzardwowdatatest.service;

import com.estoeban.blizzardwowdatatest.models.Character;
import com.estoeban.blizzardwowdatatest.models.CharacterPvpInformation;

import java.io.IOException;
import java.net.URISyntaxException;

public interface WowCharacterInformationService {

    Character getCharacterInformation(final String characterName, final String realmName)
        throws IOException, URISyntaxException;

    CharacterPvpInformation getCharacterPvpInformation(final String characterName, final String realmName)
        throws IOException, URISyntaxException;
}
