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
import net.minecraft.text.MutableText;
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
                if(!isValidItem(itemStack)) return false;
                ItemStack currentStoredStackMatch = getContentStack(stack,itemStack);
                int i = (currentStoredStackMatch.getMaxCount() - currentStoredStackMatch.getCount());
                int j = addToBundle(stack, slot.takeStackRange(itemStack.getCount(), i, player));
                if (j > 0) {
                    this.playInsertSound(player);
                }
            }

            return true;
        }
    }

    public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
        if (stack.getCount() != 1) return false;
        if (clickType == ClickType.RIGHT && slot.canTakePartial(player)) {
            if(!isValidItem(otherStack)) return false;
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
        return Math.min(1 + 12 * getBundleOccupancy(stack) / MAX_STORAGE, 13);
    }

    public int getItemBarColor(ItemStack stack) {
        return ITEM_BAR_COLOR;
    }

    private static int addToBundle(ItemStack bundle, ItemStack stack) {
        if(isValidItem(stack))
        {
            if (!stack.isEmpty() && stack.getItem().canBeNested()) {
                NbtCompound nbtCompound = bundle.getOrCreateNbt();
                if (!nbtCompound.contains(ITEMS_KEY)) {
                    nbtCompound.put(ITEMS_KEY, new NbtList());
                }

                int i = getBundleOccupancy(bundle);
                int j = getItemOccupancy(stack);
                int k = Math.min(stack.getCount(), (MAX_STORAGE - i));
                if (k == 0) {
                    return 0;
                } else {
                    NbtList nbtList = nbtCompound.getList(ITEMS_KEY, 10);
                    Optional<NbtCompound> optional = canMergeStack(stack, nbtList);
                    if (optional.isPresent()) {
                        NbtCompound nbtCompound2 = (NbtCompound)optional.get();
                        ItemStack itemStack = ItemStack.fromNbt(nbtCompound2);
                        k = Math.min(stack.getCount(), (itemStack.getMaxCount() - itemStack.getCount()));
                        if (k == 0) { return 0; }
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
        }else {
            return 0;
        }
    }

    private static Optional<NbtCompound> canMergeStack(ItemStack stack, NbtList items) {
//        if (stack.isOf(Items.BUNDLE)) {
//            return Optional.empty();
//        } else {
//            Stream var10000 = items.stream();
//            Objects.requireNonNull(NbtCompound.class);
//            var10000 = var10000.filter(NbtCompound.class::isInstance);
//            Objects.requireNonNull(NbtCompound.class);
//            return var10000.map(NbtCompound.class::cast).filter((item) -> {
//                return ItemStack.canCombine(ItemStack.fromNbt(item), stack);
//            }).findFirst();
//        }
        return stack.isOf(Items.BUNDLE) ? Optional.empty() : items.stream().filter(NbtCompound.class::isInstance).map(NbtCompound.class::cast).filter((p_186350_) -> {
            return ItemStack.canCombine(ItemStack.fromNbt(p_186350_), stack);
        }).findFirst();
    }

    private static int getItemOccupancy(ItemStack stack) {
        return 1;
    }

    private static int getBundleOccupancy(ItemStack stack) {
        return getBundledStacks(stack).mapToInt((itemStack) -> {
            return getItemOccupancy(itemStack) * itemStack.getCount();
        }).sum();
    }

    private static ItemStack getContentStack(ItemStack bagStack,ItemStack matchStack) {
        ItemStack itemFromInv = ItemStack.EMPTY;
        itemFromInv = getBundledStacks(bagStack)
                .filter(itemStack -> itemStack.getItem().equals(matchStack.getItem()))
                .findFirst().orElse(ItemStack.EMPTY);

        return itemFromInv;
    }

    private static Optional<ItemStack> removeFirstStack(ItemStack stack) {
        NbtCompound nbtCompound = stack.getOrCreateNbt();
        if (!nbtCompound.contains(ITEMS_KEY)) {
            return Optional.empty();
        } else {
            NbtList nbtList = nbtCompound.getList(ITEMS_KEY, 10);
            if (nbtList.isEmpty()) {
                return Optional.empty();
            } else {
                int i = 0;
                NbtCompound nbtCompound2 = nbtList.getCompound(0);
                ItemStack itemStack = ItemStack.fromNbt(nbtCompound2);
                nbtList.remove(0);
                if (nbtList.isEmpty()) {
                    stack.removeSubNbt(ITEMS_KEY);
                }

                return Optional.of(itemStack);
            }
        }
    }

    private static boolean dropAllBundledItems(ItemStack stack, PlayerEntity player) {
        NbtCompound nbtCompound = stack.getOrCreateNbt();
        if (!nbtCompound.contains(ITEMS_KEY)) {
            return false;
        } else {
            if (player instanceof ServerPlayerEntity) {
                NbtList nbtList = nbtCompound.getList(ITEMS_KEY, 10);

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
            NbtList nbtList = nbtCompound.getList(ITEMS_KEY, 10);
            return nbtList.stream().map(NbtCompound.class::cast).map(ItemStack::fromNbt);
//            Stream var10000 = nbtList.stream();
//            Objects.requireNonNull(NbtCompound.class);
//            return var10000.map(NbtCompound.class::cast).map(ItemStack::fromNbt);
        }
    }

    public Optional<TooltipData> getTooltipData(ItemStack stack) {
        DefaultedList<ItemStack> defaultedList = DefaultedList.of();
        getBundledStacks(stack).forEach(defaultedList::add);
        return Optional.of(new BundleTooltipData(defaultedList, getBundleOccupancy(stack)));
//        Stream var10000 = getBundledStacks(stack);
//        Objects.requireNonNull(defaultedList);
//        var10000.forEach(defaultedList::add);
//        return Optional.of(new BundleTooltipData(defaultedList, getBundleOccupancy(stack)));
    }

    private static double getContentAmount(ItemStack stack) {
        return getBundledStacks(stack).mapToDouble((itemStack) -> {
            if(itemStack.getItem() instanceof BaseCoinItem coin)
            {
                return coin.coinValue() * itemStack.getCount();
            }
            else return 0.0D;
        }).sum();
    }

    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {

        MutableText componentAmountText = Text.translatable("toooltip.wallet_amount");
        componentAmountText.formatted(Formatting.GOLD);
        MutableText componentAmount = Text.literal(""+ BigDecimal.valueOf(getContentAmount(stack)).toPlainString());
        componentAmount.formatted(Formatting.GREEN);
        componentAmountText.append(componentAmount);
        tooltip.add(componentAmountText);


        //tooltip.add(Text.translatable("item.minecraft.bundle.fullness", new Object[]{getBundleOccupancy(stack), 64}).formatted(Formatting.GRAY));
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
}
