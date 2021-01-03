package com.estoeban.blizzardwowdatatest.models.character.guild;

import lombok.Data;

/**
 * Used to support the Guild model.
 */
@Data
public class GuildEmblem {
    private Integer icon;
    private String iconColor;               // hex
    private Integer iconColorId;
    private Integer border;
    private String borderColor;             // hex
    private Integer borderColorId;
    private String backgroundColor;         // hex
    private Integer backgroundColorId;
}
