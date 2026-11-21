package com.example.warmod;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.world.item.CreativeModeTabs;

public class WarMod implements ModInitializer {
    public static final String MODID = "warmod";

    @Override
    public void onInitialize() {
        ModBlocks.register();
        ModEntities.register();
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.REDSTONE_BLOCKS).register(entries -> {
            entries.accept(ModBlocks.BIG_TNT);
            entries.accept(ModBlocks.MEGA_TNT);
            entries.accept(ModBlocks.FIRE_TNT);
        });
    }
}
