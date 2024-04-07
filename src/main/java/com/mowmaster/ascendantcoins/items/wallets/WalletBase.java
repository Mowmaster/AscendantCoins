package com.mowmaster.ascendantcoins.items.wallets;

import com.mowmaster.ascendantcoins.items.coins.BaseCoinItem;
import net.minecraft.client.item.BundleTooltipData;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.item.TooltipData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.ClickType;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public class WalletBase extends Item {

    private static final String ITEMS_KEY = "Items";
    public static final int MAX_STORAGE = 576;
    private static final int BUNDLE_ITEM_OCCUPANCY = 4;
    private static final int ITEM_BAR_COLOR = MathHelper.packRgb(0.4F, 0.4F, 1.0F);

    public WalletBase(Settings settings) {
        super(settings);
    }


    public static boolean isValidItem(ItemStack stack)
    {
        if(stack.getItem() instanceof BaseCoinItem)return true;
        else return false;
    }

    public static float getAmountFilled(ItemStack stack) {
        return (float)getBundleOccupancy(stack) / (float)MAX_STORAGE;
    }

    public boolean onStackClicked(ItemStack stack, Slot slot, ClickType clickType, PlayerEntity player) {
        if (clickType != ClickType.RIGHT) {
            return false;
        } else {
            ItemStack itemStack = slot.getStack();
            if (itemStack.isEmpty()) {
                this.playRemoveOneSound(player);
                removeFirstStack(stack).ifPresent((removedStack) -> {
                    addToBundle(stack, slot.insertStack(removedStack));
                });
            } else if (itemStack.getItem().canBeNested()) {
                int i = (64 - getBundleOccupancy(stack)) / getItemOccupancy(itemStack);
                int j = addToBundle(stack, slot.takeStackRange(itemStack.getCount(), i, player));
                if (j > 0) {
                    this.playInsertSound(player);
                }
            }

            return true;
        }
    }

    public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
        if (clickType == ClickType.RIGHT && slot.canTakePartial(player)) {
            if (otherStack.isEmpty()) {
                removeFirstStack(stack).ifPresent((itemStack) -> {
                    this.playRemoveOneSound(player);
                    cursorStackReference.set(itemStack);
                });
            } else {
                int i = addToBundle(stack, otherStack);
                if (i > 0) {
                    this.playInsertSound(player);
                    otherStack.decrement(i);
                }
            }

            return true;
        } else {
            return false;
        }
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if (dropAllBundledItems(itemStack, user)) {
            this.playDropContentsSound(user);
            user.incrementStat(Stats.USED.getOrCreateStat(this));
            return TypedActionResult.success(itemStack, world.isClient());
        } else {
            return TypedActionResult.fail(itemStack);
        }
    }

    public boolean isItemBarVisible(ItemStack stack) {
        return getBundleOccupancy(stack) > 0;
    }

    public int getItemBarStep(ItemStack stack) {
        return Math.min(1 + 12 * getBundleOccupancy(stack) / 64, 13);
    }

    public int getItemBarColor(ItemStack stack) {
        return ITEM_BAR_COLOR;
    }

    private static int addToBundle(ItemStack bundle, ItemStack stack) {
        if (!stack.isEmpty() && stack.getItem().canBeNested()) {
            NbtCompound nbtCompound = bundle.getOrCreateNbt();
            if (!nbtCompound.contains("Items")) {
                nbtCompound.put("Items", new NbtList());
            }

            int i = getBundleOccupancy(bundle);
            int j = getItemOccupancy(stack);
            int k = Math.min(stack.getCount(), (64 - i) / j);
            if (k == 0) {
                return 0;
            } else {
                NbtList nbtList = nbtCompound.getList("Items", 10);
                Optional<NbtCompound> optional = canMergeStack(stack, nbtList);
                if (optional.isPresent()) {
                    NbtCompound nbtCompound2 = (NbtCompound)optional.get();
                    ItemStack itemStack = ItemStack.fromNbt(nbtCompound2);
                    itemStack.increment(k);
                    itemStack.writeNbt(nbtCompound2);
                    nbtList.remove(nbtCompound2);
                    nbtList.add(0, nbtCompound2);
                } else {
                    ItemStack itemStack2 = stack.copyWithCount(k);
                    NbtCompound nbtCompound3 = new NbtCompound();
                    itemStack2.writeNbt(nbtCompound3);
                    nbtList.add(0, nbtCompound3);
                }

                return k;
            }
        } else {
            return 0;
        }
    }

    private static Optional<NbtCompound> canMergeStack(ItemStack stack, NbtList items) {
        if (stack.isOf(Items.BUNDLE)) {
            return Optional.empty();
        } else {
            Stream var10000 = items.stream();
            Objects.requireNonNull(NbtCompound.class);
            var10000 = var10000.filter(NbtCompound.class::isInstance);
            Objects.requireNonNull(NbtCompound.class);
            return var10000.map(NbtCompound.class::cast).filter((item) -> {
                return ItemStack.canCombine(ItemStack.fromNbt(item), stack);
            }).findFirst();
        }
    }

    private static int getItemOccupancy(ItemStack stack) {
        if (stack.isOf(Items.BUNDLE)) {
            return 4 + getBundleOccupancy(stack);
        } else {
            if ((stack.isOf(Items.BEEHIVE) || stack.isOf(Items.BEE_NEST)) && stack.hasNbt()) {
                NbtCompound nbtCompound = BlockItem.getBlockEntityNbt(stack);
                if (nbtCompound != null && !nbtCompound.getList("Bees", 10).isEmpty()) {
                    return 64;
                }
            }

            return 64 / stack.getMaxCount();
        }
    }

    private static int getBundleOccupancy(ItemStack stack) {
        return getBundledStacks(stack).mapToInt((itemStack) -> {
            return getItemOccupancy(itemStack) * itemStack.getCount();
        }).sum();
    }

    private static Optional<ItemStack> removeFirstStack(ItemStack stack) {
        NbtCompound nbtCompound = stack.getOrCreateNbt();
        if (!nbtCompound.contains("Items")) {
            return Optional.empty();
        } else {
            NbtList nbtList = nbtCompound.getList("Items", 10);
            if (nbtList.isEmpty()) {
                return Optional.empty();
            } else {
                int i = false;
                NbtCompound nbtCompound2 = nbtList.getCompound(0);
                ItemStack itemStack = ItemStack.fromNbt(nbtCompound2);
                nbtList.remove(0);
                if (nbtList.isEmpty()) {
                    stack.removeSubNbt("Items");
                }

                return Optional.of(itemStack);
            }
        }
    }

    private static boolean dropAllBundledItems(ItemStack stack, PlayerEntity player) {
        NbtCompound nbtCompound = stack.getOrCreateNbt();
        if (!nbtCompound.contains("Items")) {
            return false;
        } else {
            if (player instanceof ServerPlayerEntity) {
                NbtList nbtList = nbtCompound.getList("Items", 10);

                for(int i = 0; i < nbtList.size(); ++i) {
                    NbtCompound nbtCompound2 = nbtList.getCompound(i);
                    ItemStack itemStack = ItemStack.fromNbt(nbtCompound2);
                    player.dropItem(itemStack, true);
                }
            }

            stack.removeSubNbt("Items");
            return true;
        }
    }

    private static Stream<ItemStack> getBundledStacks(ItemStack stack) {
        NbtCompound nbtCompound = stack.getNbt();
        if (nbtCompound == null) {
            return Stream.empty();
        } else {
            NbtList nbtList = nbtCompound.getList("Items", 10);
            Stream var10000 = nbtList.stream();
            Objects.requireNonNull(NbtCompound.class);
            return var10000.map(NbtCompound.class::cast).map(ItemStack::fromNbt);
        }
    }

    public Optional<TooltipData> getTooltipData(ItemStack stack) {
        DefaultedList<ItemStack> defaultedList = DefaultedList.of();
        Stream var10000 = getBundledStacks(stack);
        Objects.requireNonNull(defaultedList);
        var10000.forEach(defaultedList::add);
        return Optional.of(new BundleTooltipData(defaultedList, getBundleOccupancy(stack)));
    }

    /*private static double getContentAmount(ItemStack p_150779_) {
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

        //MowLibTooltipUtils.addTooltipMessage(p_150751_,p_150749_,Component.translatable("item.minecraft.bundle.fullness", getContentWeight(p_150749_), MAX_STORAGE).withStyle(ChatFormatting.GRAY));
    }*/

    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("item.minecraft.bundle.fullness", new Object[]{getBundleOccupancy(stack), 64}).formatted(Formatting.GRAY));
    }

    public void onItemEntityDestroyed(ItemEntity entity) {
        ItemUsage.spawnItemContents(entity, getBundledStacks(entity.getStack()));
    }

    private void playRemoveOneSound(Entity entity) {
        entity.playSound(SoundEvents.BLOCK_CHAIN_HIT, 0.8F, 0.8F + entity.getWorld().getRandom().nextFloat() * 0.4F);
    }

    private void playInsertSound(Entity entity) {
        entity.playSound(SoundEvents.BLOCK_CHAIN_PLACE, 0.8F, 0.8F + entity.getWorld().getRandom().nextFloat() * 0.4F);
    }

    private void playDropContentsSound(Entity entity) {
        entity.playSound(SoundEvents.BLOCK_CHAIN_BREAK, 0.8F, 0.8F + entity.getWorld().getRandom().nextFloat() * 0.4F);
    }

    /*

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
        return Math.min(1 + 12 * getContentWeight(p_150771_) / MAX_STORAGE, 13);
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
                int k = Math.min(p_150765_.getCount(), (MAX_STORAGE - i));
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

    */
}
