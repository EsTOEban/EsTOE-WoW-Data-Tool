package com.estoeban.blizzardwowdatatest.models;

import lombok.Data;
import lombok.extern.log4j.Log4j2;

@Data
@Log4j2
public class CharacterPvpInformation {

    private String name;
    private String covenant;
    private String twosRating;
    private String threesRating;
    private String rbgRating;
}
