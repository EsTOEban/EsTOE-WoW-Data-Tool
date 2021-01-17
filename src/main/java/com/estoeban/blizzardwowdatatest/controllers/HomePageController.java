package com.estoeban.blizzardwowdatatest.controllers;

import com.estoeban.blizzardwowdatatest.models.CharacterPvpInformation;
import com.estoeban.blizzardwowdatatest.models.dto.CharacterDTO;
import com.estoeban.blizzardwowdatatest.service.WowCharacterInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.net.URISyntaxException;

@Controller
public class HomePageController {

    final WowCharacterInformationService characterInformationService;

    public HomePageController(
        WowCharacterInformationService characterInformationService) {
        this.characterInformationService = characterInformationService;
    }

    @GetMapping("/home")
    public String homeForm(
            Model model) {
        model.addAttribute("characterDTO", new CharacterPvpInformation());
        return "home";
    }

    @PostMapping("/home")
    public String homeSubmit(@ModelAttribute CharacterPvpInformation characterDTO, Model model)
        throws IOException, URISyntaxException {

        characterDTO = characterInformationService.getCharacterPvpInformation(characterDTO.getName(), characterDTO.getRealm());

        model.addAttribute("characterDTO", characterDTO);
        return "result";
    }
}
