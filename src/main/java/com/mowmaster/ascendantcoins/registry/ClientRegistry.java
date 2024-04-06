package com.mowmaster.ascendantcoins.registry;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.mowmaster.ascendantcoins.utils.Reference.MODID;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientRegistry
{
    @SubscribeEvent
    public static void registerItemColor(RegisterColorHandlersEvent.Item event)
    {
        /*event.register((stack, color) ->
        {if (color == 1) {return BaseItemBulkStorageItem.getItemColor();} else {return -1;}}, DeferredRegisterItems.MECHANICAL_STORAGE_ITEM.get());
*/

        ACItemModelProperties.itemModelProps(DeferredRegistryItems.WALLET_SMALL.get());


    }



}
