package net.kmn64.im.main.fluid;

import java.util.function.Consumer;

import javax.annotation.Nullable;

import blusunrize.immersiveengineering.ImmersiveEngineering;
import blusunrize.immersiveengineering.common.IEContent;
import blusunrize.immersiveengineering.common.util.GenericDeferredWork;
import blusunrize.immersiveengineering.common.util.fluids.IEFluid;
import net.kmn64.im.IMMain;
import net.kmn64.im.main.IMContent;
import net.kmn64.im.main.block.IMFluidBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.capability.wrappers.FluidBucketWrapper;

public class IMBaseFluid extends FlowingFluid {

    private final String fluidName;
	protected final ResourceLocation stillTex;
	protected final ResourceLocation flowingTex;
	protected IMBaseFluid flowing;
	protected IMBaseFluid source;
    @Nullable
	protected final Consumer<FluidAttributes.Builder> buildAttributes;
	public IMFluidBlock block;
	protected Item bucket;

	public IMBaseFluid(String fluidName, ResourceLocation stillTex, ResourceLocation flowingTex, @Nullable Consumer<FluidAttributes.Builder> buildAttributes)
	{
		this(fluidName, stillTex, flowingTex, buildAttributes, true);
	}

	public IMBaseFluid(String fluidName, ResourceLocation stillTex, ResourceLocation flowingTex, @Nullable Consumer<FluidAttributes.Builder> buildAttributes, boolean isSource)
	{
		this.fluidName = fluidName;
		this.stillTex = stillTex;
		this.flowingTex = flowingTex;
		this.buildAttributes = buildAttributes;
		IMContent.IMFluids.put(fluidName, this);
		if(!isSource)
		{
			flowing = this;
			setRegistryName(ImmersiveEngineering.MODID, fluidName+"_flowing");
		}
		else
		{
			source = this;
			this.block = new IMFluidBlock(this);
			this.block.setRegistryName(IMMain.MODID, String.format("%s_block", fluidName));
			//IEContent.registeredIEBlocks.add(this.block);
			this.bucket = new BucketItem(() -> this.source, new Item.Properties().stacksTo(1).craftRemainder(Items.BUCKET))
			    {
				    @Override
				    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt)
				    {
					    return new FluidBucketWrapper(stack);
				    }
                };
			this.bucket.setRegistryName(ImmersiveEngineering.MODID, String.format("%s_bucket", fluidName));
			//IEContent.registeredIEItems.add(this.bucket);
			GenericDeferredWork.registerDispenseBehavior(this.bucket, IEFluid.BUCKET_DISPENSE_BEHAVIOR);
			flowing = createFlowingVariant();
			setRegistryName(ImmersiveEngineering.MODID, fluidName);
		}
	}

    private IMBaseFluid createFlowingVariant()
	{
		IMBaseFluid flowingVariant = new IMBaseFluid(fluidName, stillTex, flowingTex, buildAttributes, false)
		{
			@Override
			protected void createFluidStateDefinition(StateContainer.Builder<Fluid, FluidState> builder)
			{
				super.createFluidStateDefinition(builder);
				builder.add(LEVEL);
			}
		};
		flowingVariant.source = this;
		flowingVariant.bucket = bucket;
		flowingVariant.block = block;
		flowingVariant.registerDefaultState(flowingVariant.getStateDefinition().any().setValue(LEVEL, 7));
		return flowingVariant;
	}

    @Override
    public Fluid getFlowing() {
        
        return flowing;
    }

    @Override
    public Fluid getSource() {
        
        return source;
    }

    @Override
    protected boolean canConvertToSource() {
        
        return false;
    }

    @Override
    protected void beforeDestroyingBlock(IWorld pLevel, BlockPos pPos, BlockState pState) {

    }

    @Override
    protected int getSlopeFindDistance(IWorldReader pLevel) {
        
        return 4;
    }

    @Override
    protected int getDropOff(IWorldReader pLevel) {
        
        return 1;
    }

    @Override
    public Item getBucket() {
        
        return bucket;
    }

    @Override
    protected boolean canBeReplacedWith(FluidState Fluidstate, IBlockReader Blockreader, BlockPos blockPos, Fluid fluid, Direction direction) {
        
        return direction==Direction.DOWN&&!isSame(fluid);
    }

    @Override
    public int getTickDelay(IWorldReader p_205569_1_) {
        
        return 5;
    }

    @Override
    protected float getExplosionResistance() {
        
        return -1;
    }

    @Override
    protected BlockState createLegacyBlock(FluidState state) {
        
        return block.defaultBlockState().setValue(FlowingFluidBlock.LEVEL, getLegacyLevel(state));
    }

    @Override
    public boolean isSource(FluidState state) {
        return state.getType()==source;
    }

    @Override
    public int getAmount(FluidState state) {
        if(isSource(state))
			return 8;
		else
			return state.getValue(LEVEL);
    }

}