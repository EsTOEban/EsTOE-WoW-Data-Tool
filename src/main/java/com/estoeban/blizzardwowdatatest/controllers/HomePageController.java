package com.estoeban.blizzardwowdatatest.controllers;

import com.estoeban.blizzardwowdatatest.models.dto.CharacterNameRealmDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomePageController {

    @GetMapping("/home")
    public String homeForm(
            Model model) {
        model.addAttribute("characterNameRealmDTO", new CharacterNameRealmDTO());
        return "home";
    }

    @PostMapping("/home")
    public String homeSubmit(@ModelAttribute CharacterNameRealmDTO characterNameRealmDTO, Model model) {
        model.addAttribute("characterNameRealmDTO", characterNameRealmDTO);
        return "result";
    }
}
