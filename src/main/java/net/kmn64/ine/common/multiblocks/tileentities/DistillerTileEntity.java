package net.kmn64.ine.common.multiblocks.tileentities;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.ImmutableSet;

import blusunrize.immersiveengineering.api.utils.shapes.CachedShapesWithTransform;
import blusunrize.immersiveengineering.common.blocks.IEBlockInterfaces.IBlockBounds;
import blusunrize.immersiveengineering.common.blocks.generic.PoweredMultiblockTileEntity;
import net.kmn64.ine.api.crafting.DistillerRecipe;
import net.kmn64.ine.common.INETileTypes;
import net.kmn64.ine.common.multiblocks.multiblocks.DistillerMultiblock;
import net.kmn64.ine.common.utils.AABBUtils;
import net.kmn64.ine.config.INEServerConfig;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public class DistillerTileEntity extends PoweredMultiblockTileEntity<DistillerTileEntity,DistillerRecipe> implements IBlockBounds {

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
	public boolean additionalCanProcessCheck(MultiblockProcess<DistillerRecipe> arg0) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void doProcessFluidOutput(FluidStack arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doProcessOutput(ItemStack arg0) {
		// TODO Auto-generated method stub
		
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
	public int getMaxProcessPerTick() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public float getMinProcessDistance(MultiblockProcess<DistillerRecipe> arg0) {
		// TODO Auto-generated method stub
		return 0;
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
		return null;
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
	protected boolean canDrainTankFrom(int arg0, Direction arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean canFillTankFrom(int arg0, Direction arg1, FluidStack arg2) {
		// TODO Auto-generated method stub
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
		final int bX = posInMultiblock.getX();
		final int bY = posInMultiblock.getY();
		final int bZ = posInMultiblock.getZ();
		
		int pos = bX+bY+bZ;
		List<AxisAlignedBB> main = new ArrayList<>();
		
		if(pos > 0 && pos < 9 && pos != 5 && pos != 3 && pos != 7) AABBUtils.box(main,0, 0, 0, 1, .5f, 1);
		if(pos == 11) AABBUtils.box(main, 0, 0, 0, 1,1,.5f);
		
		// Use default cube shape if nessesary
		if(main.isEmpty()){
			main.add(AABBUtils.FULL);
		}
		return main;
		
	}

}
