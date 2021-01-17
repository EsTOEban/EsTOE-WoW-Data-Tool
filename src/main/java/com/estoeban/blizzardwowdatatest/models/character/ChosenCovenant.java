package com.estoeban.blizzardwowdatatest.models.character;

import lombok.Data;

/**
 *   "chosen_covenant": {
 *       "key": {
 *         "href": "https://us.api.blizzard.com/data/wow/covenant/2?namespace=static-9.0.2_36532-us"
 *       },
 *       "name": "Venthyr",
 *       "id": 2
 *   },
 */
@Data
public class ChosenCovenant {
    String id;
    String name;
}
