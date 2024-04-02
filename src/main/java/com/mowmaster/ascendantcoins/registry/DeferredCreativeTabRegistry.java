package com.mowmaster.ascendantcoins.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import static com.mowmaster.ascendantcoins.utils.Reference.MODID;

public class DeferredCreativeTabRegistry
{
    public static final DeferredRegister<CreativeModeTab> DEF_REG = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    public static final RegistryObject<CreativeModeTab> TAB = DEF_REG.register(MODID, () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup." + MODID))
            .icon(() -> makeIcon())
            .displayItems((enabledFeatures, output) -> {
                for(RegistryObject<Item> item : DeferredRegistryItems.ITEMS.getEntries()){
                    output.accept(item.get());
                }
            })
            .build());

    private static ItemStack makeIcon() {
        ItemStack stack = new ItemStack(Items.BOOK);
        CompoundTag tag = new CompoundTag();
        tag.putInt("3DRender", 1);
        stack.setTag(tag);
        return stack;
    }
}
