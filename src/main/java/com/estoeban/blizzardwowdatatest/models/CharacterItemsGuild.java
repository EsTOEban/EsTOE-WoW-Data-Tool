package com.estoeban.blizzardwowdatatest.models;

import com.estoeban.blizzardwowdatatest.models.character.Guild;
import com.estoeban.blizzardwowdatatest.models.character.Items;
import lombok.Data;

/**
 * Expands on the base {@link Character} class and adds support for {@link Items} and {@link Guild} instances.
 */
@Data
public class CharacterItemsGuild extends Character {
    private Items items;
    private Guild guild;
}
