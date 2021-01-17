package com.estoeban.blizzardwowdatatest.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class CharacterPvpInformation {

    @NonNull
    private String name;
    @NonNull
    private String realm;
    private String covenant;
    private String twosRating;
    private String threesRating;
    private String rbgRating;
}
