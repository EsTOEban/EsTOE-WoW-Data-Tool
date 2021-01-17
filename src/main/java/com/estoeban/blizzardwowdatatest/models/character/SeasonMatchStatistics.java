package com.estoeban.blizzardwowdatatest.models.character;

import lombok.Data;

/**
 *   "season_match_statistics": {
 *     "played": 120,
 *     "won": 65,
 *     "lost": 55
 *   }
 */
@Data
public class SeasonMatchStatistics {
    Integer played;
    Integer won;
    Integer lost;
}
