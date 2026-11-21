package com.example.warmod;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.TntBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class CustomTntBlock extends TntBlock {
    private final float power;
    private final boolean fire;

    public CustomTntBlock(Properties props, float power, boolean fire) {
        super(props);
        this.power = power;
        this.fire = fire;
    }

    private void ignite(Level level, BlockPos pos, int fuse) {
        if (level.isClientSide) return;
        CustomTntEntity tnt = new CustomTntEntity(level,
            pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, power, fire);
        if (fuse > 0) tnt.setFuse(fuse);
        level.addFreshEntity(tnt);
        level.playSound(null, tnt.getX(), tnt.getY(), tnt.getZ(),
            SoundEvents.TNT_PRIMED, SoundSource.BLOCKS, 1.0F, 1.0F);
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState old, boolean moving) {
        if (!old.is(state.getBlock()) && level.hasNeighborSignal(pos)) {
            ignite(level, pos, 0);
            level.removeBlock(pos, false);
        }
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block,
                                BlockPos from, boolean moving) {
        if (level.hasNeighborSignal(pos)) {
            ignite(level, pos, 0);
            level.removeBlock(pos, false);
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player,
                                 InteractionHand hand, BlockHitResult hit) {
        ItemStack stack = player.getItemInHand(hand);
        boolean flint = stack.is(Items.FLINT_AND_STEEL);
        if (!flint && !stack.is(Items.FIRE_CHARGE)) {
            return InteractionResult.PASS;
        }
        ignite(level, pos, 0);
        level.setBlock(pos, Blocks.AIR.defaultBlockState(), 11);
        if (!player.isCreative()) {
            if (flint) {
                stack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(hand));
            } else {
                stack.shrink(1);
            }
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    public void onProjectileHit(Level level, BlockState state, BlockHitResult hit, Projectile projectile) {
        if (!level.isClientSide && projectile.isOnFire()) {
            BlockPos pos = hit.getBlockPos();
            ignite(level, pos, 0);
            level.removeBlock(pos, false);
        }
    }

    @Override
    public void wasExploded(Level level, BlockPos pos, Explosion explosion) {
        ignite(level, pos, level.random.nextInt(20) + 10);
    }
}
