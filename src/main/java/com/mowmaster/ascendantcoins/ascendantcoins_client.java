package com.mowmaster.ascendantcoins;


import com.mowmaster.ascendantcoins.items.wallets.WalletBase;
import com.mowmaster.ascendantcoins.registry.ItemRegistry;
import com.mowmaster.ascendantcoins.util.References;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.util.Identifier;

public class ascendantcoins_client implements ClientModInitializer
{
    @Override
    public void onInitializeClient() {
        References.LOGGER.info("Registering Predicates for Custom Modeled Items for " + References.MODNAME);

        ModelPredicateProviderRegistry.register(
                ItemRegistry.WALLET, new Identifier("filled"),
                (stack, world, entity, seed) -> WalletBase.getAmountFilled(stack)
        );

    }
}
