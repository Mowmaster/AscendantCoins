package com.mowmaster.ascendantcoins.items.coins;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.math.BigDecimal;

public class BaseCoinItem extends Item
{

    public BaseCoinItem(Settings settings) {
        super(settings);
    }

    public double coinValue()
    {
        //return AscendantCoinsConfig.COMMON.coins_small_copper.get();
        return 0.0D;
    }

    private static double getCoinAmount(ItemStack p_150779_) {
        if(p_150779_.getItem() instanceof BaseCoinItem coin)
        {
            return coin.coinValue();
        }
        return 0.0D;
    }

    /*public void appendHoverText(ItemStack p_150749_, Level p_150750_, List<Component> p_150751_, TooltipFlag p_150752_) {

        MutableComponent componentAmountTextSingle = Component.translatable("toooltip.coin_amount_single");
        MutableComponent componentAmountTextStack = Component.translatable("toooltip.coin_amount_stack");

        componentAmountTextSingle.withStyle(ChatFormatting.GOLD);
        MutableComponent componentAmountSingle = Component.literal(""+ BigDecimal.valueOf(getCoinAmount(p_150749_)).toPlainString());
        componentAmountSingle.withStyle(ChatFormatting.GREEN);
        componentAmountTextSingle.append(componentAmountSingle);
        MowLibTooltipUtils.addTooltipMessage(p_150751_,componentAmountTextSingle);

        //MowLibTooltipUtils.addTooltipMessage(p_150751_,Component.literal(""));

        componentAmountTextStack.withStyle(ChatFormatting.LIGHT_PURPLE);
        MutableComponent componentAmountStack = Component.literal(""+ BigDecimal.valueOf(getCoinAmount(p_150749_)*p_150749_.getCount()).toPlainString());
        componentAmountStack.withStyle(ChatFormatting.WHITE);
        componentAmountTextStack.append(componentAmountStack);
        MowLibTooltipUtils.addTooltipMessage(p_150751_,componentAmountTextStack);
    }*/
}
