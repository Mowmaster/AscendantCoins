package com.mowmaster.ascendantcoins.util;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = "ascendantcoins")
@Config.Gui.Background("minecraft:textures/block/stone.png")
public class AscendantCoinConfig implements ConfigData {

    //ConfigRegistry.CONFIG.coins_small_copper
    @Comment("Coin Base Values")
    public double coins_small_copper = 1.0D;
    public double coins_medium_copper = 9.0D;
    public double coins_large_copper = 81.0D;

    public double coins_small_iron = 729.0D;
    public double coins_large_iron = 6561.0D;

    public double coins_small_gold = 59049.0D;
    public double coins_large_gold = 531441.0D;

    public double coins_small_netherite = 4782969.0D;
    public double coins_large_netherite = 43046721.0D;


}

