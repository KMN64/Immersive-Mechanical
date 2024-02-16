package net.kmn64.ine.common.multiblocks.tileentities;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.ImmutableSet;

import blusunrize.immersiveengineering.api.IEEnums.IOSideConfig;
import blusunrize.immersiveengineering.api.utils.shapes.CachedShapesWithTransform;
import blusunrize.immersiveengineering.common.blocks.IEBlockInterfaces.IBlockBounds;
import blusunrize.immersiveengineering.common.blocks.IEBlockInterfaces.IInteractionObjectIE;
import blusunrize.immersiveengineering.common.blocks.generic.PoweredMultiblockTileEntity;
import net.kmn64.ine.api.crafting.DistillerRecipe;
import net.kmn64.ine.common.INETileTypes;
import net.kmn64.ine.common.multiblocks.multiblocks.DistillerMultiblock;
import net.kmn64.ine.common.utils.AABBUtils;
import net.kmn64.ine.common.utils.FluidHelper;
import net.kmn64.ine.config.INEServerConfig;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

public class DistillerTileEntity extends PoweredMultiblockTileEntity<DistillerTileEntity,DistillerRecipe> implements IInteractionObjectIE, IBlockBounds {

	/** Template-Location of the Fluid Input Port. (1 0 0)<br> */
	public static final BlockPos Fluid_IN = new BlockPos(1, 0, 0);
	
	/** Template-Location of the Fluid Output Port. (1 0 2)<br> */
	public static final BlockPos Fluid_OUT = new BlockPos(1, 0, 2);
	
	/** Template-Location of the Item Output Port. (0 0 2)<br> */
	public static final BlockPos Item_OUT = new BlockPos(0, 0, 2);
	
	/** Template-Location of the Energy Input Ports. (0 1 0)<br> */
	public static final Set<BlockPos> Energy_IN = ImmutableSet.of(new BlockPos(0, 1, 0));
	
	/** Template-Location of the Redstone Input Port. (0 1 2)<br> */
	public static final Set<BlockPos> Redstone_IN = ImmutableSet.of(new BlockPos(0, 1, 2));
	
	
	public final FluidTank[] tanks = new FluidTank[]{new FluidTank(INEServerConfig.machines.distiller.distiller_input_tankSize.get()), new FluidTank(INEServerConfig.machines.distiller.distiller_output_tankSize.get())};
	public DistillerTileEntity() {
		super(DistillerMultiblock.instance, 16000, true, INETileTypes.DISTILLER.get());
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void readCustomNBT(CompoundNBT nbt, boolean descPacket){
		super.readCustomNBT(nbt, descPacket);

		this.tanks[0].readFromNBT(nbt.getCompound("tank0"));
		this.tanks[1].readFromNBT(nbt.getCompound("tank1"));
	}
	
	@Override
	public void writeCustomNBT(CompoundNBT nbt, boolean descPacket){
		super.writeCustomNBT(nbt, descPacket);

		nbt.put("tank0", this.tanks[0].writeToNBT(new CompoundNBT()));
		nbt.put("tank1", this.tanks[1].writeToNBT(new CompoundNBT()));
	}
	
	@Override
	public void tick(){
		super.tick();
		
		if(this.level.isClientSide || isDummy() || isRSDisabled()){
			return;
		}
		
		boolean update = false;
		
		if(this.energyStorage.getEnergyStored() > 0 && this.processQueue.size() < getProcessQueueMaxLength()){
			if(this.tanks[0].getFluidAmount() > 0){
				DistillerRecipe recipe = DistillerRecipe.findRecipe(this.tanks[0].getFluid());
				
				if(recipe != null && this.energyStorage.getEnergyStored() >= recipe.getTotalProcessEnergy()){
					if(this.tanks[0].getFluidAmount() >= recipe.getInputFluid().getAmount()){
						int[] inputs, inputAmounts;
						
						inputs = new int[]{0};
						inputAmounts = new int[]{recipe.getInputFluid().getAmount()};
						
						MultiblockProcessInMachine<DistillerRecipe> process = new MultiblockProcessInMachine<DistillerRecipe>(recipe)
								.setInputTanks(inputs)
								.setInputAmounts(inputAmounts);
						if(addProcessToQueue(process, true)){
							addProcessToQueue(process, false);
							update = true;
						}
					}
				}
			}
		}
		
		if(!this.processQueue.isEmpty()){
			update = true;
		}
		
		
		if(this.tanks[1].getFluidAmount() > 0){
			BlockPos outPos = getBlockPosForPos(Fluid_OUT).above();
			update |= FluidUtil.getFluidHandler(this.level, outPos, Direction.DOWN).map(output -> {
				boolean ret = false;
				FluidStack target = this.tanks[1].getFluid();
				target = FluidHelper.copyFluid(target, Math.min(target.getAmount(), 1000));
				
				int accepted = output.fill(target, FluidAction.SIMULATE);
				if(accepted > 0){
					int drained = output.fill(FluidHelper.copyFluid(target, Math.min(target.getAmount(), accepted)), FluidAction.EXECUTE);
					
					this.tanks[1].drain(new FluidStack(target.getFluid(), drained), FluidAction.EXECUTE);
					ret |= true;
				}
				
				return ret;
			}).orElse(false);
		}
		
		if(update){
			updateMasterBlock(null, true);
		}
	}

	@Override
	public void doGraphicalUpdates() {
		// TODO Auto-generated method stub
		this.setChanged();
		this.markContainingBlockForUpdate(null);
	}

	@Override
	public NonNullList<ItemStack> getInventory() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getSlotLimit(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isStackValid(int arg0, ItemStack arg1) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean additionalCanProcessCheck(MultiblockProcess<DistillerRecipe> process) {
		// TODO Auto-generated method stub
		int outputAmount = 0;
		for(FluidStack outputFluid:process.recipe.getFluidOutputs()){
			outputAmount += outputFluid.getAmount();
		}
		
		return this.tanks[1].getCapacity() >= (this.tanks[1].getFluidAmount() + outputAmount);
	}

	@Override
	public void doProcessFluidOutput(FluidStack arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doProcessOutput(ItemStack output) {
		// TODO Auto-generated method stub
		Direction outputdir = (getIsMirrored() ? getFacing().getClockWise() : getFacing().getCounterClockWise());
		BlockPos outputpos = getBlockPosForPos(Item_OUT).offset(outputdir.getStepX(),outputdir.getStepY(),outputdir.getStepZ());
		
		TileEntity te = level.getBlockEntity(outputpos);
		if(te != null){
			IItemHandler handler = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, outputdir.getOpposite()).orElse(null);
			if(handler != null){
				output = ItemHandlerHelper.insertItem(handler, output, false);
			}
		}
		
		if(!output.isEmpty()){
			double x = outputpos.getX() + 0.5;
			double y = outputpos.getY() + 0.25;
			double z = outputpos.getZ() + 0.5;
			
			Direction facing = getIsMirrored() ? getFacing().getOpposite() : getFacing();
			if(facing != Direction.EAST && facing != Direction.WEST){
				x = outputpos.getX() + (facing == Direction.SOUTH ? 0.15 : 0.85);
			}
			if(facing != Direction.NORTH && facing != Direction.SOUTH){
				z = outputpos.getZ() + (facing == Direction.WEST ? 0.15 : 0.85);
			}
			
			ItemEntity ei = new ItemEntity(level, x, y, z, output.copy());
			ei.lerpMotion(0.075 * outputdir.getStepX(), 0.025, 0.075 * outputdir.getStepZ());
			level.addFreshEntity(ei);
		}
	}

	@Override
	public DistillerRecipe findRecipeForInsertion(ItemStack arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<BlockPos> getEnergyPos() {
		// TODO Auto-generated method stub
		return Energy_IN;
	}
	
	@Override
	public Set<BlockPos> getRedstonePos() { 
		return Redstone_IN;
	}

	@Override
	public IFluidTank[] getInternalTanks() {
		// TODO Auto-generated method stub
		return this.tanks;
	}
	
	@Override
	public IOSideConfig getEnergySideConfig(Direction facing){
		if(this.formed && this.isEnergyPos() && (facing == null || facing == Direction.UP)){
			return IOSideConfig.INPUT;
		}
		
		return IOSideConfig.NONE;
	}

	@Override
	public int getMaxProcessPerTick() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public float getMinProcessDistance(MultiblockProcess<DistillerRecipe> arg0) {
		// TODO Auto-generated method stub
		return 1.0f;
	}

	@Override
	public int[] getOutputSlots() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getOutputTanks() {
		// TODO Auto-generated method stub
		return new int[] {1};
	}

	@Override
	public int getProcessQueueMaxLength() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected DistillerRecipe getRecipeForId(ResourceLocation arg0) {
		// TODO Auto-generated method stub
		return DistillerRecipe.recipes.get(arg0);
	}

	@Override
	public boolean isInWorldProcessingMachine() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onProcessFinish(MultiblockProcess<DistillerRecipe> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected boolean canDrainTankFrom(int arg0, Direction side) {
		// TODO Auto-generated method stub
		if(this.posInMultiblock.equals(Fluid_OUT) && (side == null || side == getFacing().getOpposite())){
			DistillerTileEntity master = master();
			
			if(master != null && master.tanks[1].getFluidAmount() > 0){
				if(!master.tanks[1].isEmpty())
					return DistillerRecipe.hasRecipeWithInput(master.tanks[0].getFluid(), true);
			}
		}
		return false;
	}

	@Override
	protected boolean canFillTankFrom(int arg0, Direction side, FluidStack resource) {
		// TODO Auto-generated method stub
		if(this.posInMultiblock.equals(Fluid_IN) && (side == null || side == getFacing().getOpposite())){
			DistillerTileEntity master = master();
			
			if(master != null && master.tanks[0].getFluidAmount() < master.tanks[0].getCapacity()){
				if(master.tanks[0].isEmpty()){
					return DistillerRecipe.hasRecipeWithInput(resource, true);
				}else{
					return resource.isFluidEqual(master.tanks[0].getFluid());
				}
			}
		}
		return false;
	}

	@Override
	protected IFluidTank[] getAccessibleFluidTanks(Direction side) {
		// TODO Auto-generated method stub
		DistillerTileEntity master = master();
		if(master != null){
			if(this.posInMultiblock.equals(Fluid_IN) && side != Direction.DOWN){
				return new IFluidTank[]{master.tanks[0]};
			}
			if(this.posInMultiblock.equals(Fluid_OUT) && side != Direction.DOWN){
				return new IFluidTank[]{master.tanks[1]};
			}
		}
		return new IFluidTank[0];
	}
	
	private static CachedShapesWithTransform<BlockPos, Pair<Direction, Boolean>> SHAPES = CachedShapesWithTransform.createForMultiblock(DistillerTileEntity::getShape);

	@Override
	public VoxelShape getBlockBounds(ISelectionContext param1iSelectionContext) {
		// TODO Auto-generated method stub
		return SHAPES.get(this.posInMultiblock, Pair.of(getFacing(), getIsMirrored()));
	}
	
	private static List<AxisAlignedBB> getShape(BlockPos posInMultiblock){
		final BlockPos bp = posInMultiblock.offset(1, 1, 1).offset(Direction.EAST.getStepX(),Direction.EAST.getStepY(),Direction.EAST.getStepZ());
		final int bX = bp.getX();
		final int bY = bp.getY();
		final int bZ = bp.getZ();
		final int pos = bX+bY+bZ;
		
		List<AxisAlignedBB> main = new ArrayList<>();
		
		if(pos > 0 && pos < 9 && pos != 5 && pos != 3 && pos != 7) AABBUtils.box16(main,0, 0, 0, 1, 0.5, 1);
		if(pos == 11) AABBUtils.box16(main,0, 0,0, 0.5, 1, 1);
		if(pos == 21 || pos == 24) AABBUtils.box16(main,0, 0, 0, 1, 0.5, 1);
		
		// Use default cube shape if nessesary
		if(main.isEmpty()){
			main.add(AABBUtils.FULL);
		}
		return main;
		
	}

	@Override
	public IInteractionObjectIE getGuiMaster() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean canUseGui(PlayerEntity param1PlayerEntity) {
		// TODO Auto-generated method stub
		return false;
	}

}
