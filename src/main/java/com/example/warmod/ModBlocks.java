package com.example.warmod;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class ModBlocks {
    public static final Block BIG_TNT = reg("big_tnt", 8.0F, false);
    public static final Block MEGA_TNT = reg("mega_tnt", 20.0F, false);
    public static final Block FIRE_TNT = reg("fire_tnt", 5.0F, true);

    private static Block reg(String name, float power, boolean fire) {
        Block block = new CustomTntBlock(
            BlockBehaviour.Properties.of().instabreak().sound(SoundType.GRASS), power, fire);
        ResourceLocation id = new ResourceLocation(WarMod.MODID, name);
        Registry.register(BuiltInRegistries.BLOCK, id, block);
        Registry.register(BuiltInRegistries.ITEM, id, new BlockItem(block, new Item.Properties()));
        return block;
    }

    public static void register() {}
}
