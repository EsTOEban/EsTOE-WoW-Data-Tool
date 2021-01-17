package com.estoeban.blizzardwowdatatest.models;

import com.estoeban.blizzardwowdatatest.models.character.CharacterCovenantProgress;
import com.estoeban.blizzardwowdatatest.models.character.Race;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

/**
 * Base Character Object
 */
@Data
@Log4j2
public class Character {
    private Long lastModified;
    private String name;
    private Realm realm;
    private String battlegroup;

    @JsonProperty(value = "class")
    private Integer classId;

    private Race race;
    private Integer level;
    private Integer achievementPoints;
    private String thumbnail;
    @JsonProperty(value="calcClass")
    private String localizedClassName;
    private Integer totalHonorableKills;

    @JsonProperty(value = "covenant_progress")
    private CharacterCovenantProgress covenantProgress;

    //    private Integer faction;
    private Gender gender;

}
