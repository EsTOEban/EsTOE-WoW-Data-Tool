package com.estoeban.blizzardwowdatatest.controllers;

import com.estoeban.blizzardwowdatatest.models.Character;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "/character")
public class CharacterController {

    @GetMapping("/{characterName}")
    public Character getCharacter(@PathVariable String characterName) {
        Character character = new Character();
        character.setName(characterName);
        return character;
    }
}
