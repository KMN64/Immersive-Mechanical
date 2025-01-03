package net.kmn64.im.main.fluid;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.base.Supplier;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer;
import net.minecraft.state.StateHolder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class IMBaseFluidBlock extends FlowingFluidBlock {
    private final IMBaseFluid fluid;
	@Nullable
	private Effect effect;
	private int duration;
	private int level;
	private static IMBaseFluid tempFluid;

	private static Supplier<IMBaseFluid> supply(IMBaseFluid fluid)
	{
		tempFluid = fluid;
		return () -> fluid;
	}

	public IMBaseFluidBlock(IMBaseFluid fluid)
	{
		super(supply(fluid), Properties.of(Material.WATER));
		this.fluid = fluid;
	}

	@Override
	protected void createBlockStateDefinition(@Nonnull StateContainer.Builder<Block, BlockState> builder)
	{
		super.createBlockStateDefinition(builder);
		IMBaseFluid f;
		if(fluid!=null)
			f = fluid;
		else
			f = tempFluid;
		builder.add(f.getStateDefinition().getProperties().toArray(new Property[0]));
	}

	@Nonnull
	@Override
	public FluidState getFluidState(@Nonnull BlockState state)
	{
		FluidState baseState = super.getFluidState(state);
		for(Property<?> prop : fluid.getStateDefinition().getProperties())
			if(prop!=FlowingFluidBlock.LEVEL)
				baseState = withCopiedValue(prop, baseState, state);
		return baseState;
	}

	private <T extends StateHolder<?, T>, S extends Comparable<S>>
	T withCopiedValue(Property<S> prop, T oldState, StateHolder<?, ?> copyFrom)
	{
		return oldState.setValue(prop, copyFrom.getValue(prop));
	}

	public void setEffect(@Nonnull Effect effect, int duration, int level)
	{
		this.effect = effect;
		this.duration = duration;
		this.level = level;
	}

	@SuppressWarnings("deprecation")
    @Override
	public void entityInside(@Nonnull BlockState state, @Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull Entity entityIn)
	{
		super.entityInside(state, worldIn, pos, entityIn);
		if(effect!=null&&entityIn instanceof LivingEntity)
			((LivingEntity)entityIn).addEffect(new EffectInstance(effect, duration, level));
	}

    // TODO just copy some IE code -- KMN64
}
