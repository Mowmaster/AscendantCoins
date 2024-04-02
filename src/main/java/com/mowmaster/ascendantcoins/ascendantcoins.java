package com.mowmaster.ascendantcoins;

import com.mojang.logging.LogUtils;
import com.mowmaster.ascendantcoins.registry.ClientRegistry;
import com.mowmaster.ascendantcoins.registry.DeferredCreativeTabRegistry;
import com.mowmaster.ascendantcoins.registry.DeferredRegistryItems;
import com.mowmaster.ascendantcoins.utils.AscendantCoinsConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;

import static com.mowmaster.ascendantcoins.utils.Reference.MODID;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(MODID)
public class ascendantcoins
{
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    public ascendantcoins()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the commonSetup method for modloading
        //modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        //modEventBus.addListener(this::addCreative);

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> modEventBus.register(new ClientRegistry()));
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, AscendantCoinsConfig.commonSpec);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, AscendantCoinsConfig.clientSpec);

        DeferredRegistryItems.ITEMS.register(modEventBus);
        DeferredCreativeTabRegistry.DEF_REG.register(modEventBus);
    }

    /*private void commonSetup(final FMLCommonSetupEvent event)
    {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");

        if (AscendantCoinsConfig.logDirtBlock)
            LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));

        LOGGER.info(AscendantCoinsConfig.magicNumberIntroduction + AscendantCoinsConfig.magicNumber);

        AscendantCoinsConfig.items.forEach((item) -> LOGGER.info("ITEM >> {}", item.toString()));
    }*/

    // Add the example block item to the building blocks tab
    /*private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        //if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS)
            //event.accept(EXAMPLE_BLOCK_ITEM);
    }*/

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    /*@SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
        LOGGER.info("Ascendant Coins Server Starting");
    }*/

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    /*@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            // Some client setup code
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
            //ClientRegistry.registerBlockEntityRenderers();
        }
    }*/
}
