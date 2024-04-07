package com.mowmaster.ascendantcoins.registry;

import com.mowmaster.ascendantcoins.util.AscendantCoinConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;

public class ConfigRegistry
{
    public static AscendantCoinConfig CONFIG = new AscendantCoinConfig();

    public static void init() {
        AutoConfig.register(AscendantCoinConfig.class, JanksonConfigSerializer::new);
        CONFIG = AutoConfig.getConfigHolder(AscendantCoinConfig.class).getConfig();
    }
}
