package com.example.warmod;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class ModEntities {
    public static final EntityType<CustomTntEntity> CUSTOM_TNT = Registry.register(
        BuiltInRegistries.ENTITY_TYPE,
        new ResourceLocation(WarMod.MODID, "custom_tnt"),
        EntityType.Builder.<CustomTntEntity>of(CustomTntEntity::new, MobCategory.MISC)
            .fireImmune().sized(0.98F, 0.98F)
            .clientTrackingRange(10).updateInterval(10)
            .build("custom_tnt"));

    public static void register() {}
}
