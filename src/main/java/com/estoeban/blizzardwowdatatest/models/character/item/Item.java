package com.estoeban.blizzardwowdatatest.models.character.item;

import lombok.Data;

import java.util.Collection;

/**
 * A partially complete list of elements included in the Item object returned when requesting the items field.
 *
 * Noticeable missing fields are artifactTraits and relics
 */
@Data
public class Item {
    private Long id;
    private String name;
    private String icon;
    private Integer quality;
    private Integer itemLevel;
    private TooltipParams tooltipParams;
    private Collection<StatBundle> stats;
    private Integer armor;
    private String context;
    private Collection<Integer> bonusLists;
    private Integer artifactId;
    private Long displayInfoId;
    private Integer artifactAppearanceId;
    private Appearance appearance;
//    private AzeriteItem azeriteItem;
//    private AzeriteEmpoweredItem azeriteEmpoweredItem;
}
