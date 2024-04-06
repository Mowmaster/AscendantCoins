package com.mowmaster.ascendantcoins.registry;

import com.mowmaster.ascendantcoins.items.wallets.WalletBase;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import static com.mowmaster.ascendantcoins.utils.Reference.MODID;

public class ACItemModelProperties
{
    public static void itemModelProps(Item item)
    {
        ItemProperties.register(item, new ResourceLocation(MODID + ":filled"),(p_174625_, p_174626_, p_174627_, p_174628_) -> {
            return WalletBase.getFullnessDisplay(p_174625_);});

        /*ItemProperties.register(item, new ResourceLocation(MODID + ":upgrade_mode"),(p_174625_, p_174626_, p_174627_, p_174628_) -> {
            return ItemUpgradeImport.getUpgradeModeForRender(p_174625_);});*/
    }
}
