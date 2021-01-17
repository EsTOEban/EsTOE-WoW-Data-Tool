package com.estoeban.blizzardwowdatatest.models.character;

import lombok.Data;

@Data
public class CharacterGenericPvpInformation {
    public String rating;
    public Bracket ratedBracket;
    public WeeklyMatchStatistics weeklyMatchStatistics;
    public SeasonMatchStatistics seasonMatchStatistics;
}
