package com.mowmaster.ascendantcoins.items.coins;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

import java.math.BigDecimal;
import java.util.List;

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

    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {

        MutableText componentAmountTextSingle = Text.translatable("toooltip.coin_amount_single");
        MutableText componentAmountTextStack = Text.translatable("toooltip.coin_amount_stack");

        componentAmountTextSingle.formatted(Formatting.GOLD);
        MutableText componentAmountSingle = Text.literal(""+ BigDecimal.valueOf(getCoinAmount(stack)).toPlainString());
        componentAmountSingle.formatted(Formatting.GREEN);
        componentAmountTextSingle.append(componentAmountSingle);
        tooltip.add(componentAmountTextSingle);

        componentAmountTextStack.formatted(Formatting.LIGHT_PURPLE);
        MutableText componentAmountStack = Text.literal(""+ BigDecimal.valueOf(getCoinAmount(stack)*stack.getCount()).toPlainString());
        componentAmountStack.formatted(Formatting.WHITE);
        componentAmountTextStack.append(componentAmountStack);
        tooltip.add(componentAmountTextStack);
    }
}
