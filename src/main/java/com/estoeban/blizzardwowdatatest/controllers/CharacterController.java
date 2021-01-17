package com.estoeban.blizzardwowdatatest.controllers;

import com.estoeban.blizzardwowdatatest.config.AppConfig;
import com.estoeban.blizzardwowdatatest.models.Character;
import com.estoeban.blizzardwowdatatest.models.CharacterPvpInformation;
import com.estoeban.blizzardwowdatatest.service.WowCharacterInformationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URISyntaxException;

@Slf4j
@RestController(value = "character")
@RequestMapping("character")
public class CharacterController {

    private final AppConfig appConfig;
    private final WowCharacterInformationService wowCharacterInformationService;

    @Autowired
    CharacterController(
        AppConfig appConfig,
        WowCharacterInformationService wowCharacterInformationService) {
        this.appConfig = appConfig;
        this.wowCharacterInformationService = wowCharacterInformationService;
    }

    @GetMapping()
    public ResponseEntity<Character> getCharacter(
        @RequestParam String characterName,
        @RequestParam String realmName
    ) throws IOException, URISyntaxException {
        Character character = new Character();
        HttpStatus httpStatus = HttpStatus.OK;

        try {
            character = wowCharacterInformationService.getCharacterInformation(characterName, realmName);
        } catch (Exception e) {
            httpStatus = HttpStatus.NO_CONTENT;
            log.error(e.getMessage());
        }

        return new ResponseEntity<>(character, httpStatus);
    }

    @GetMapping("/pvp")
    public ResponseEntity<CharacterPvpInformation> getCharacterPvpInfo(
        @RequestParam String characterName,
        @RequestParam String realmName
    ) throws IOException, URISyntaxException {
        return new ResponseEntity<>(wowCharacterInformationService.getCharacterPvpInformation(characterName, realmName),
            HttpStatus.OK);
    }
}
