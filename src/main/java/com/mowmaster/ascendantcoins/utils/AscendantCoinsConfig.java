package com.mowmaster.ascendantcoins.utils;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import org.apache.commons.lang3.tuple.Pair;

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

        public final ForgeConfigSpec.DoubleValue coins_small_copper;
        public final ForgeConfigSpec.DoubleValue coins_medium_copper;
        public final ForgeConfigSpec.DoubleValue coins_large_copper;

        public final ForgeConfigSpec.DoubleValue coins_small_iron;
        public final ForgeConfigSpec.DoubleValue coins_large_iron;

        public final ForgeConfigSpec.DoubleValue coins_small_gold;
        public final ForgeConfigSpec.DoubleValue coins_large_gold;

        public final ForgeConfigSpec.DoubleValue coins_small_netherite;
        public final ForgeConfigSpec.DoubleValue coins_large_netherite;

        Common(ForgeConfigSpec.Builder builder)
        {
            builder.comment("Coins").push("Values");

            //coins_small_copper = builder.comment("Base Item Transfer Rate").defineInRange("pedestal_baseItemTransferRate", 4, 1, Integer.MAX_VALUE);
            coins_small_copper = builder
                    .comment("Small Copper Coin Value")
                    .defineInRange("coins_small_copper_value", 1.0D, 0.0D, (double)Integer.MAX_VALUE);
            coins_medium_copper = builder
                    .comment("Medium Copper Coin Value")
                    .defineInRange("coins_medium_copper_value", 9.0D, 0.0D, (double)Integer.MAX_VALUE);
            coins_large_copper = builder
                    .comment("Large Copper Coin Value")
                    .defineInRange("coins_large_copper_value", 81.0D, 0.0D, (double)Integer.MAX_VALUE);

            coins_small_iron = builder
                    .comment("Small Iron Coin Value")
                    .defineInRange("coins_small_iron_value", 729.0D, 0.0D, (double)Integer.MAX_VALUE);
            coins_large_iron = builder
                    .comment("Large Iron Coin Value")
                    .defineInRange("coins_large_iron_value", 6561.0D, 0.0D, (double)Integer.MAX_VALUE);

            coins_small_gold = builder
                    .comment("Small Gold Coin Value")
                    .defineInRange("coins_small_gold_value", 59049.0D, 0.0D, (double)Integer.MAX_VALUE);
            coins_large_gold = builder
                    .comment("Large Gold Coin Value")
                    .defineInRange("coins_large_gold_value", 531441.0D, 0.0D, (double)Integer.MAX_VALUE);

            coins_small_netherite = builder
                    .comment("Small Netherite Coin Value")
                    .defineInRange("coins_small_netherite_value", 4782969.0D, 0.0D, (double)Integer.MAX_VALUE);
            coins_large_netherite = builder
                    .comment("Large Netherite Coin Value")
                    .defineInRange("coins_large_netherite_value", 43046721.0D, 0.0D, (double)Integer.MAX_VALUE);

            builder.pop();
        }
    }

    public static class Client
    {
        //public final ForgeConfigSpec.BooleanValue pedestalRotateItems;

        Client(ForgeConfigSpec.Builder builder)
        {
            //builder.comment("Pedestal rendering options").push("Pedestal");
            /*pedestalRotateItems = builder
                    .comment("Should items on pedestal rotate")
                    .comment("§a§lLOW§f impact when §a§lTRUE")
                    .comment("§2§lLOWEST§f impact when §c§lFALSE")
                    .define("Rotate Items", true);*/
            //builder.pop();
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
        //ascendantcoins.LOGGER.debug("Loaded Ascendant Coins config file {}", configEvent.getConfig().getFileName());
    }

    @SubscribeEvent
    public static void onFileChange(final ModConfigEvent.Reloading configEvent) {
        //ascendantcoins.LOGGER.debug("Ascendant Coins config just got changed on the file system!");
    }


}
