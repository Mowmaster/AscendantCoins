package com.mowmaster.ascendantcoins.utils;

import com.mowmaster.ascendantcoins.ascendantcoins;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.mowmaster.ascendantcoins.utils.Reference.MODID;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Forge's config APIs
@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AscendantCoinsConfig
{
    public static class Common
    {
        //public final ForgeConfigSpec.IntValue augment_t1CapacityItem;
        //public final ForgeConfigSpec.BooleanValue cobbleGeneratorRequireTools;
        //public final ForgeConfigSpec.DoubleValue upgrade_blockbreaker_energyMultiplier;

        Common(ForgeConfigSpec.Builder builder)
        {
            builder.comment("Pedestal Defaults").push("Global Pedestal Defaults");

            //pedestal_baseItemTransferRate = builder.comment("Base Item Transfer Rate").defineInRange("pedestal_baseItemTransferRate", 4, 1, Integer.MAX_VALUE);

            builder.pop();
        }
    }

    public static class Client
    {
        //public final ForgeConfigSpec.BooleanValue pedestalRotateItems;

        Client(ForgeConfigSpec.Builder builder)
        {
            builder.comment("Pedestal rendering options").push("Pedestal");
            /*pedestalRotateItems = builder
                    .comment("Should items on pedestal rotate")
                    .comment("§a§lLOW§f impact when §a§lTRUE")
                    .comment("§2§lLOWEST§f impact when §c§lFALSE")
                    .define("Rotate Items", true);*/
            builder.pop();
        }
    }

    public static final ForgeConfigSpec commonSpec;
    public static final Common COMMON;
    public static final ForgeConfigSpec clientSpec;
    public static final Client CLIENT;

    static {
        final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
        commonSpec = specPair.getRight();
        COMMON = specPair.getLeft();

        final Pair<Client, ForgeConfigSpec> clientSpecPair = new ForgeConfigSpec.Builder().configure(Client::new);
        clientSpec = clientSpecPair.getRight();
        CLIENT = clientSpecPair.getLeft();
    }

    @SubscribeEvent
    public static void onLoad(final ModConfigEvent.Loading configEvent) {
        ascendantcoins.LOGGER.debug("Loaded Pedestals config file {}", configEvent.getConfig().getFileName());
    }

    @SubscribeEvent
    public static void onFileChange(final ModConfigEvent.Reloading configEvent) {
        ascendantcoins.LOGGER.debug("Pedestals config just got changed on the file system!");
    }


}
