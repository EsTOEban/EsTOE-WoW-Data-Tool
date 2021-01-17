package com.estoeban.blizzardwowdatatest.models.character;

import lombok.Data;

/**
 *   "weekly_match_statistics": {
 *     "played": 4,
 *     "won": 3,
 *     "lost": 1
 *   }
 */
@Data
public class WeeklyMatchStatistics {
    String played;
    String won;
    String lost;
}
