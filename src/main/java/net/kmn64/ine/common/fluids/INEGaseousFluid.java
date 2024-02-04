package net.kmn64.ine.common.fluids;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import blusunrize.immersiveengineering.ImmersiveEngineering;
import blusunrize.immersiveengineering.common.IEContent;
import blusunrize.immersiveengineering.common.util.GenericDeferredWork;
import blusunrize.immersiveengineering.common.util.fluids.IEFluid;
import blusunrize.immersiveengineering.common.util.fluids.IEFluidBlock;
import net.kmn64.ine.ImmersiveNuclearEngineering;
import net.kmn64.ine.common.INEContent;
import net.minecraft.block.BlockState;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IDispenseItemBehavior;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.IDataSerializer;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.wrappers.FluidBucketWrapper;

public class INEGaseousFluid extends FlowingFluid {
	  public static final Collection<INEGaseousFluid> INEGASEOUS_FLUIDS = new ArrayList<>();
	  
	  protected final String fluidName;
	  
	  protected final ResourceLocation stillTex;
	  protected final ResourceLocation flowingTex;
	  protected INEGaseousFluid flowing;
	  protected INEGaseousFluid source;
	  @Nullable
	  protected final Consumer<FluidAttributes.Builder> buildAttributes;
	  public INEFluidBlock block;
	  protected Item bucket;
	
	public INEGaseousFluid(String name,int color) {
		this(name, new ResourceLocation(ImmersiveNuclearEngineering.MODID,"fluids/fluid"), 
				new ResourceLocation(ImmersiveNuclearEngineering.MODID,"fluids/fluid_flow"), INEFluid.createBuilder(color, 300, -1000, 1000, true));
		// TODO Auto-generated constructor stub
	}
	
	protected INEGaseousFluid(String fluidName, ResourceLocation stillTex, ResourceLocation flowingTex, @Nullable Consumer<FluidAttributes.Builder> buildAttributes) 
	{ 
		this(fluidName, stillTex, flowingTex, buildAttributes, true); 
	}
	
	protected INEGaseousFluid(String fluidName, ResourceLocation stillTex, ResourceLocation flowingTex, @Nullable Consumer<FluidAttributes.Builder> buildAttributes, boolean isSource) {
	    this.fluidName = fluidName;
	    this.stillTex = stillTex;
	    this.flowingTex = flowingTex;
	    this.buildAttributes = buildAttributes;
	    INEContent.registeredINEFluids.add(this);
	    if (!isSource) {
	      
	      this.flowing = this;
	      setRegistryName(ImmersiveNuclearEngineering.MODID, fluidName + "_flowing");
	    }
	    else {
	      
	      this.source = this;
	      this.block = new INEFluidBlock(this);
	      this.block.setRegistryName(ImmersiveNuclearEngineering.MODID, fluidName + "_fluid_block");
	      INEContent.registeredINEBlocks.add(this.block);
	      this.bucket = new BucketItem(() -> this.source, (new Item.Properties()).stacksTo(1).tab(ImmersiveEngineering.ITEM_GROUP).craftRemainder(Items.BUCKET))
	        {
	          
	          public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt)
	          {
	            return new FluidBucketWrapper(stack);
	          }
	        };
	      this.bucket.setRegistryName(ImmersiveNuclearEngineering.MODID, fluidName + "_bucket");
	      INEContent.registeredINEItems.add(this.bucket);
	      GenericDeferredWork.registerDispenseBehavior(this.bucket, BUCKET_DISPENSE_BEHAVIOR);
	      this.flowing = createFlowingVariant();
	      setRegistryName(ImmersiveNuclearEngineering.MODID, fluidName);
	      INEGASEOUS_FLUIDS.add(this);
	    } 
	  }



	  
	  @OnlyIn(Dist.CLIENT)
	  public void addTooltipInfo(FluidStack fluidStack, @Nullable PlayerEntity player, List<ITextComponent> tooltip) {}


	  
	  @Nonnull
	  public Item getBucket() { return this.bucket; }




	  
	  protected boolean canBeReplacedWith(FluidState fluidState, IBlockReader blockReader, BlockPos pos, Fluid fluidIn, Direction direction) { return (direction == Direction.DOWN && !isSame(fluidIn)); }




	  
	  public boolean isSame(Fluid fluidIn) { return (fluidIn == this.source || fluidIn == this.flowing); }





	  
	  public int getTickDelay(IWorldReader p_205569_1_) { return 5; }




	  
	  protected float getExplosionResistance() { return 100.0F; }




	  
	  protected BlockState createLegacyBlock(FluidState state) { return (BlockState)this.block.defaultBlockState().setValue(FlowingFluidBlock.LEVEL, Integer.valueOf(getLegacyLevel(state))); }




	  
	  public boolean isSource(FluidState state) { return (state.getType() == this.source); }



	  
	  public int getAmount(FluidState state) {
	    if (isSource(state)) {
	      return 8;
	    }
	    return ((Integer)state.getValue(LEVEL)).intValue();
	  }


	  
	  @Nonnull
	  protected FluidAttributes createAttributes() {
	    FluidAttributes.Builder builder = FluidAttributes.builder(this.stillTex, this.flowingTex);
	    if (this.buildAttributes != null)
	      this.buildAttributes.accept(builder); 
	    return builder.build(this);
	  }



	  
	  @Nonnull
	  public Fluid getFlowing() { return this.flowing; }




	  
	  @Nonnull
	  public Fluid getSource() { return this.source; }




	  
	  protected boolean canConvertToSource() { return false; }




	  
	  protected void beforeDestroyingBlock(IWorld iWorld, BlockPos blockPos, BlockState blockState) {}




	  
	  protected int getSlopeFindDistance(IWorldReader iWorldReader) { return 4; }




	  
	  protected int getDropOff(IWorldReader iWorldReader) { return 1; }


	  
	  protected INEGaseousFluid createFlowingVariant() {
	    INEGaseousFluid ret = new INEGaseousFluid(this.fluidName, this.stillTex, this.flowingTex, this.buildAttributes, false)
	      {
	        
	        protected void createFluidStateDefinition(StateContainer.Builder<Fluid, FluidState> builder)
	        {
	          super.createFluidStateDefinition(builder);
	          builder.add(new Property[] { LEVEL });
	        }
	      };
	    ret.source = this;
	    ret.bucket = this.bucket;
	    ret.block = this.block;
	    ret.registerDefaultState((FluidState)((FluidState)ret.getStateDefinition().any()).setValue(LEVEL, Integer.valueOf(7)));
	    return ret;
	  }

	  
	  public static Consumer<FluidAttributes.Builder> createBuilder(int density, int viscosity) {
	    return builder -> 
	      builder.viscosity(viscosity)
	      .density(density);
	  }

	  
	  public static final IDataSerializer<Optional<FluidStack>> OPTIONAL_FLUID_STACK = new IDataSerializer<Optional<FluidStack>>()
	    {
	      
	      public void write(PacketBuffer buf, Optional<FluidStack> value)
	      {
	        buf.writeBoolean(value.isPresent());
	        value.ifPresent(fs -> buf.writeNbt(fs.writeToNBT(new CompoundNBT())));
	      }


	      
	      @Nonnull
	      public Optional<FluidStack> read(PacketBuffer buf) {
	        FluidStack fs = !buf.readBoolean() ? null : FluidStack.loadFluidStackFromNBT(buf.readNbt());
	        return Optional.ofNullable(fs);
	      }



	      
	      public DataParameter<Optional<FluidStack>> createAccessor(int id) { return new DataParameter(id, this); }




	      
	      public Optional<FluidStack> copy(Optional<FluidStack> value) { return value.map(FluidStack::copy); }
	    };

	  
	  public static final IDispenseItemBehavior BUCKET_DISPENSE_BEHAVIOR = new DefaultDispenseItemBehavior()
	    {
	      private final DefaultDispenseItemBehavior defaultBehavior = new DefaultDispenseItemBehavior();

	      
	      public ItemStack execute(IBlockSource source, ItemStack stack) {
	        BucketItem bucketitem = (BucketItem)stack.getItem();
	        BlockPos blockpos = source.getPos().relative((Direction)source.getBlockState().getValue(DispenserBlock.FACING));
	        ServerWorld serverWorld = source.getLevel();
	        if (bucketitem.emptyBucket(null, serverWorld, blockpos, null)) {
	          
	          bucketitem.checkExtraContent(serverWorld, stack, blockpos);
	          return new ItemStack(Items.BUCKET);
	        } 
	        
	        return this.defaultBehavior.dispense(source, stack);
	      }
	    };
}
