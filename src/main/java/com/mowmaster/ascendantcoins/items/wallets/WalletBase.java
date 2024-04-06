package com.mowmaster.ascendantcoins.items.wallets;

import com.mowmaster.ascendantcoins.items.coins.BaseCoinItem;
import com.mowmaster.ascendantcoins.registry.DeferredRegistryItems;
import com.mowmaster.mowlib.MowLibUtils.MowLibTooltipUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.tooltip.BundleTooltip;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static com.mowmaster.ascendantcoins.utils.Reference.MODID;

public class WalletBase extends Item {
    private static final String TAG_ITEMS = "Items";
    public static final int MAX_WEIGHT = 576;
    private static final int BUNDLE_IN_BUNDLE_WEIGHT = 4;
    private static final int BAR_COLOR = Mth.color(0.4F, 0.4F, 1.0F);

    public WalletBase(Properties p_41383_) {
        super(p_41383_);
    }

    public static boolean isValidItem(ItemStack stack)
    {
        if(stack.getItem() instanceof BaseCoinItem)return true;
        else return false;
    }

    public static int getValidSlot(ItemStack stack)
    {
        if(stack.is(DeferredRegistryItems.COIN_SMALL_COPPER.get())) return 0;
        if(stack.is(DeferredRegistryItems.COIN_MEDIUM_COPPER.get())) return 1;
        if(stack.is(DeferredRegistryItems.COIN_LARGE_COPPER.get())) return 2;
        if(stack.is(DeferredRegistryItems.COIN_SMALL_IRON.get())) return 3;
        if(stack.is(DeferredRegistryItems.COIN_LARGE_IRON.get())) return 4;
        if(stack.is(DeferredRegistryItems.COIN_SMALL_GOLD.get())) return 5;
        if(stack.is(DeferredRegistryItems.COIN_LARGE_GOLD.get())) return 6;
        if(stack.is(DeferredRegistryItems.COIN_SMALL_NETHERITE.get())) return 7;
        if(stack.is(DeferredRegistryItems.COIN_LARGE_NETHERITE.get())) return 8;

        return -1;
    }

    public static float getFullnessDisplay(ItemStack p_150767_) {
        return (float)getContentWeight(p_150767_) / (float)MAX_WEIGHT;
    }

    public boolean overrideStackedOnOther(ItemStack p_150733_, Slot p_150734_, ClickAction p_150735_, Player p_150736_) {
        if (p_150733_.getCount() != 1 || p_150735_ != ClickAction.SECONDARY) {
            return false;
        } else {
            //System.out.println("CLICK ON ITEMS TO STORE");
            ItemStack itemstack = p_150734_.getItem();
            if (itemstack.isEmpty()) {
                this.playRemoveOneSound(p_150736_);
                removeOne(p_150733_).ifPresent((p_150740_) -> {
                    add(p_150733_, p_150734_.safeInsert(p_150740_));
                });
            } else if (itemstack.getItem().canFitInsideContainerItems()) {
                if(!isValidItem(itemstack)) return false;
                ItemStack currentStoredStackMatch = getContentStack(p_150733_,itemstack);
                int i = (currentStoredStackMatch.getMaxStackSize() - currentStoredStackMatch.getCount());
                int j = add(p_150733_, p_150734_.safeTake(itemstack.getCount(), i, p_150736_));
                if (j > 0) {
                    this.playInsertSound(p_150736_);
                }
            }

            return true;
        }
    }

    public boolean overrideOtherStackedOnMe(ItemStack p_150742_, ItemStack p_150743_, Slot p_150744_, ClickAction p_150745_, Player p_150746_, SlotAccess p_150747_) {
        if (p_150742_.getCount() != 1) return false;
        if (p_150745_ == ClickAction.SECONDARY && p_150744_.allowModification(p_150746_)) {
            //System.out.println("CLICK ITEMS ON BUNDLE TO STORE");
            if(!isValidItem(p_150743_)) return false;
            if (p_150743_.isEmpty()) {
                removeOne(p_150742_).ifPresent((p_186347_) -> {
                    this.playRemoveOneSound(p_150746_);
                    p_150747_.set(p_186347_);
                });
            } else {
                int i = add(p_150742_, p_150743_);
                if (i > 0) {
                    this.playInsertSound(p_150746_);
                    p_150743_.shrink(i);
                }
            }

            return true;
        } else {
            return false;
        }
    }

    public InteractionResultHolder<ItemStack> use(Level p_150760_, Player p_150761_, InteractionHand p_150762_) {
        ItemStack itemstack = p_150761_.getItemInHand(p_150762_);
        if (dropContents(itemstack, p_150761_)) {
            this.playDropContentsSound(p_150761_);
            p_150761_.awardStat(Stats.ITEM_USED.get(this));
            return InteractionResultHolder.sidedSuccess(itemstack, p_150760_.isClientSide());
        } else {
            return InteractionResultHolder.fail(itemstack);
        }
    }

    public boolean isBarVisible(ItemStack p_150769_) {
        return getContentWeight(p_150769_) > 0;
    }

    public int getBarWidth(ItemStack p_150771_) {
        return Math.min(1 + 12 * getContentWeight(p_150771_) / MAX_WEIGHT, 13);
    }

    public int getBarColor(ItemStack p_150773_) {
        return BAR_COLOR;
    }

    private static int add(ItemStack p_150764_, ItemStack p_150765_) {
        if(isValidItem(p_150765_))
        {
            if (!p_150765_.isEmpty() && p_150765_.getItem().canFitInsideContainerItems()) {
                CompoundTag compoundtag = p_150764_.getOrCreateTag();
                if (!compoundtag.contains(TAG_ITEMS)) {
                    compoundtag.put(TAG_ITEMS, new ListTag());
                }

                //weightOfBagContents
                int i = getContentWeight(p_150764_);
                //itemWeight
                int j = getWeight(p_150765_);
                //incoming itemStack count --> VS <-- max storage - current storage / item weight?
                //Returns:the smaller of a and b.
                int k = Math.min(p_150765_.getCount(), (MAX_WEIGHT - i));
                if (k == 0) {
                    return 0;
                } else {
                    ListTag listtag = compoundtag.getList(TAG_ITEMS, 10);
                    Optional<CompoundTag> optional = getMatchingItem(p_150765_, listtag);
                    if (optional.isPresent()) {
                        CompoundTag compoundtag1 = optional.get();
                        ItemStack itemstack = ItemStack.of(compoundtag1);
                        k = Math.min(p_150765_.getCount(), (itemstack.getMaxStackSize() - itemstack.getCount()));
                        if (k == 0) { return 0; }
                        itemstack.grow(k);
                        itemstack.save(compoundtag1);
                        listtag.remove(compoundtag1);
                        listtag.add(0, (Tag)compoundtag1);
                    } else {
                        ItemStack itemstack1 = p_150765_.copyWithCount(k);
                        CompoundTag compoundtag2 = new CompoundTag();
                        itemstack1.save(compoundtag2);
                        listtag.add(0, (Tag)compoundtag2);
                    }

                    return k;
                }
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }

    private static Optional<CompoundTag> getMatchingItem(ItemStack p_150757_, ListTag p_150758_) {
        return p_150757_.is(Items.BUNDLE) ? Optional.empty() : p_150758_.stream().filter(CompoundTag.class::isInstance).map(CompoundTag.class::cast).filter((p_186350_) -> {
            return ItemStack.isSameItemSameTags(ItemStack.of(p_186350_), p_150757_);
        }).findFirst();
    }

    private static int getWeight(ItemStack p_150777_) {
        return 1;
    }

    private static int getContentWeight(ItemStack p_150779_) {
        return getContents(p_150779_).mapToInt((p_186356_) -> {
            return getWeight(p_186356_) * p_186356_.getCount();
        }).sum();
    }

    private static ItemStack getContentStack(ItemStack bagStack,ItemStack matchStack) {
        ItemStack itemFromInv = ItemStack.EMPTY;
        itemFromInv = getContents(bagStack)
                .filter(itemStack -> itemStack.getItem().equals(matchStack.getItem()))
                .findFirst().orElse(ItemStack.EMPTY);

        return itemFromInv;
    }

    private static Optional<ItemStack> removeOne(ItemStack p_150781_) {
        CompoundTag compoundtag = p_150781_.getOrCreateTag();
        if (!compoundtag.contains(TAG_ITEMS)) {
            return Optional.empty();
        } else {
            ListTag listtag = compoundtag.getList(TAG_ITEMS, 10);
            if (listtag.isEmpty()) {
                return Optional.empty();
            } else {
                int i = 0;
                CompoundTag compoundtag1 = listtag.getCompound(0);
                ItemStack itemstack = ItemStack.of(compoundtag1);
                listtag.remove(0);
                if (listtag.isEmpty()) {
                    p_150781_.removeTagKey(TAG_ITEMS);
                }

                return Optional.of(itemstack);
            }
        }
    }

    private static boolean dropContents(ItemStack p_150730_, Player p_150731_) {
        CompoundTag compoundtag = p_150730_.getOrCreateTag();
        if (!compoundtag.contains(TAG_ITEMS)) {
            return false;
        } else {
            if (p_150731_ instanceof ServerPlayer) {
                ListTag listtag = compoundtag.getList(TAG_ITEMS, 10);

                for(int i = 0; i < listtag.size(); ++i) {
                    CompoundTag compoundtag1 = listtag.getCompound(i);
                    ItemStack itemstack = ItemStack.of(compoundtag1);
                    p_150731_.drop(itemstack, true);
                }
            }

            p_150730_.removeTagKey(TAG_ITEMS);
            return true;
        }
    }

    private static Stream<ItemStack> getContents(ItemStack p_150783_) {
        CompoundTag compoundtag = p_150783_.getTag();
        if (compoundtag == null) {
            return Stream.empty();
        } else {
            ListTag listtag = compoundtag.getList(TAG_ITEMS, 10);
            return listtag.stream().map(CompoundTag.class::cast).map(ItemStack::of);
        }
    }

    public Optional<TooltipComponent> getTooltipImage(ItemStack p_150775_) {
        NonNullList<ItemStack> nonnulllist = NonNullList.create();
        getContents(p_150775_).forEach(nonnulllist::add);
        return Optional.of(new BundleTooltip(nonnulllist, getContentWeight(p_150775_)));
    }


//    private static int getContentWeight(ItemStack p_150779_) {
//        return getContents(p_150779_).mapToInt((p_186356_) -> {
//            return getWeight(p_186356_) * p_186356_.getCount();
//        }).sum();
//    }

    private static double getContentAmount(ItemStack p_150779_) {
        return getContents(p_150779_).mapToDouble((p_186356_) -> {
            if(p_186356_.getItem() instanceof BaseCoinItem coin)
            {
                return coin.coinValue() * p_186356_.getCount();
            }
            else return 0.0D;
        }).sum();
    }

    public void appendHoverText(ItemStack p_150749_, Level p_150750_, List<Component> p_150751_, TooltipFlag p_150752_) {

        MutableComponent componentAmountText = Component.translatable("toooltip.wallet_amount");
        componentAmountText.withStyle(ChatFormatting.GOLD);
        MutableComponent componentAmount = Component.literal(""+ BigDecimal.valueOf(getContentAmount(p_150749_)).toPlainString());
        componentAmount.withStyle(ChatFormatting.GREEN);
        componentAmountText.append(componentAmount);
        MowLibTooltipUtils.addTooltipMessage(p_150751_,componentAmountText);

        //MowLibTooltipUtils.addTooltipMessage(p_150751_,p_150749_,Component.translatable("item.minecraft.bundle.fullness", getContentWeight(p_150749_), MAX_WEIGHT).withStyle(ChatFormatting.GRAY));
    }

    public void onDestroyed(ItemEntity p_150728_) {
        ItemUtils.onContainerDestroyed(p_150728_, getContents(p_150728_.getItem()));
    }

    private void playRemoveOneSound(Entity p_186343_) {
        p_186343_.playSound(SoundEvents.CHAIN_HIT, 0.8F, 0.8F + p_186343_.level().getRandom().nextFloat() * 0.4F);
    }

    private void playInsertSound(Entity p_186352_) {
        p_186352_.playSound(SoundEvents.CHAIN_PLACE, 0.8F, 0.8F + p_186352_.level().getRandom().nextFloat() * 0.4F);
    }

    private void playDropContentsSound(Entity p_186354_) {
        p_186354_.playSound(SoundEvents.CHAIN_BREAK, 0.8F, 0.8F + p_186354_.level().getRandom().nextFloat() * 0.4F);
    }
}
