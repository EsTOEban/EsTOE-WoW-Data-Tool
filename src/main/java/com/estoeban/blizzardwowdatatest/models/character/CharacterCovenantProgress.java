package com.estoeban.blizzardwowdatatest.models.character;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * "covenant_progress": {
 *     "chosen_covenant": {
 *       "key": {
 *         "href": "https://us.api.blizzard.com/data/wow/covenant/2?namespace=static-9.0.2_36532-us"
 *       },
 *       "name": "Venthyr",
 *       "id": 2
 *     },
 *     "renown_level": 24,
 *     "soulbinds": {
 *       "href": "https://us.api.blizzard.com/profile/wow/character/wildhammer/reycorgi/soulbinds?namespace=profile-us"
 *     }
 *   }
 */
@Data
public class CharacterCovenantProgress {

    @JsonProperty(value = "chosen_covenant")
    public ChosenCovenant chosenCovenant;

    @JsonProperty(value = "renown_level")
    public String renownLevel;
}
