package com.example.warmod;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.level.Level;

public class CustomTntEntity extends PrimedTnt {
    private float power = 4.0F;
    private boolean fire = false;

    public CustomTntEntity(EntityType<? extends PrimedTnt> type, Level level) {
        super(type, level);
    }

    public CustomTntEntity(Level level, double x, double y, double z, float power, boolean fire) {
        this(ModEntities.CUSTOM_TNT, level);
        this.setPos(x, y, z);
        this.power = power;
        this.fire = fire;
        this.setFuse(80);
    }

    @Override
    public void tick() {
        if (this.getFuse() <= 1) {
            if (!level().isClientSide) {
                level().explode(this, getX(), getY(0.0625D), getZ(), power,
                    fire, Level.ExplosionInteraction.TNT);
            }
            this.discard();
            return;
        }
        super.tick();
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putFloat("WarPower", power);
        tag.putBoolean("WarFire", fire);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("WarPower")) power = tag.getFloat("WarPower");
        fire = tag.getBoolean("WarFire");
    }
}
