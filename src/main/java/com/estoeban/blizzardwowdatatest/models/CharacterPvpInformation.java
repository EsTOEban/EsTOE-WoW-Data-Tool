package com.estoeban.blizzardwowdatatest.models;

import lombok.Data;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;

@Data
public class CharacterPvpInformation {

    @NonNull private String name;
    @NonNull private String realm;
    private String covenant;
    private String twosRating;
    private String threesRating;
    private String rbgRating;
}
