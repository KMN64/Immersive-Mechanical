package net.kmn64.ine.common.fluids;

import java.util.function.Supplier;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import blusunrize.immersiveengineering.common.util.fluids.IEFluid;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer;
import net.minecraft.state.StateHolder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class INEFluidBlock
extends FlowingFluidBlock
{
private final FlowingFluid ineFluid;
@Nullable
private Effect effect;
private int duration;
private int level;
private static FlowingFluid tempFluid;

private static Supplier<FlowingFluid> supply(FlowingFluid fluid) {
  tempFluid = fluid;
  return () -> fluid;
}


public INEFluidBlock(FlowingFluid ineFluid) {
  super(supply(ineFluid), AbstractBlock.Properties.of(Material.WATER));
  this.ineFluid = ineFluid;
}


protected void createBlockStateDefinition(@Nonnull StateContainer.Builder<Block, BlockState> builder) {
  FlowingFluid f;
  super.createBlockStateDefinition(builder);
  
  if (this.ineFluid != null) {
    f = this.ineFluid;
  } else {
    f = tempFluid;
  }  builder.add((Property[])f.getStateDefinition().getProperties().toArray(new Property[0]));
}



@Nonnull
public FluidState getFluidState(@Nonnull BlockState state) {
  FluidState baseState = super.getFluidState(state);
  for (Property<?> prop : this.ineFluid.getStateDefinition().getProperties()) {
    if (prop != FlowingFluidBlock.LEVEL)
      baseState = (FluidState)withCopiedValue(prop, baseState, state); 
  }  return baseState;
}




private <T extends StateHolder<?, T>, S extends Comparable<S>> T withCopiedValue(Property<S> prop, T oldState, StateHolder<?, ?> copyFrom) { return (T)(StateHolder)oldState.setValue(prop, copyFrom.getValue(prop)); }



public void setEffect(@Nonnull Effect effect, int duration, int level) {
  this.effect = effect;
  this.duration = duration;
  this.level = level;
}



@SuppressWarnings("deprecation")
public void entityInside(@Nonnull BlockState state, @Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull Entity entityIn) {
  super.entityInside(state, worldIn, pos, entityIn);
  if (this.effect != null && entityIn instanceof LivingEntity)
    ((LivingEntity)entityIn).addEffect(new EffectInstance(this.effect, this.duration, this.level)); 
}
}
