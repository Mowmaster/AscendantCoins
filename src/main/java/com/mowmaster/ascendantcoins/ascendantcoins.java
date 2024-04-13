package com.mowmaster.ascendantcoins;

import com.mowmaster.ascendantcoins.registry.ConfigRegistry;
import com.mowmaster.ascendantcoins.registry.CreativeTabGroup;
import com.mowmaster.ascendantcoins.registry.ItemRegistry;
import com.mowmaster.ascendantcoins.util.References;
import net.fabricmc.api.ModInitializer;

public class ascendantcoins implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.

	@Override
	public void onInitialize() {
		ConfigRegistry.init();
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		CreativeTabGroup.registerItemGroups();
		ItemRegistry.registerModItems();

		References.LOGGER.info("Hello Fabric world!");
	}
}