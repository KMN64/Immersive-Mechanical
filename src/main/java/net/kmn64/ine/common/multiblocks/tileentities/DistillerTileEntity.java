package net.kmn64.ine.common.multiblocks.tileentities;

import java.util.Set;

import blusunrize.immersiveengineering.common.blocks.generic.PoweredMultiblockTileEntity;
import blusunrize.immersiveengineering.common.blocks.multiblocks.IETemplateMultiblock;
import net.kmn64.ine.api.crafting.DistillerRecipe;
import net.kmn64.ine.common.INETileTypes;
import net.kmn64.ine.common.multiblocks.multiblocks.DistillerMultiblock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;

public class DistillerTileEntity extends PoweredMultiblockTileEntity<DistillerTileEntity,DistillerRecipe> {

	public DistillerTileEntity() {
		super(DistillerMultiblock.instance, 16000, true, INETileTypes.DISTILLER.get());
		// TODO Auto-generated constructor stub
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
		return false;
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
		return null;
	}

	@Override
	public IFluidTank[] getInternalTanks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getMaxProcessPerTick() {
		// TODO Auto-generated method stub
		return 0;
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
		return null;
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
	protected IFluidTank[] getAccessibleFluidTanks(Direction arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
