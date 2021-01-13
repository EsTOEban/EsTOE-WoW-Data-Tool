package com.estoeban.blizzardwowdatatest.service;

import com.estoeban.blizzardwowdatatest.models.Character;

import java.io.IOException;
import java.net.URISyntaxException;

public interface WowCharacterInformationService {

    Character getCharacterInformation(final String characterName, final String realmName)
        throws IOException, URISyntaxException;

}
