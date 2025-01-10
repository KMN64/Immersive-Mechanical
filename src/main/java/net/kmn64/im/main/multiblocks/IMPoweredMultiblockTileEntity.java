package net.kmn64.im.main.multiblocks;

import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import blusunrize.immersiveengineering.api.crafting.MultiblockRecipe;
import blusunrize.immersiveengineering.common.blocks.IEBlocks;
import blusunrize.immersiveengineering.common.blocks.generic.PoweredMultiblockTileEntity;
import blusunrize.immersiveengineering.common.blocks.multiblocks.IETemplateMultiblock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;

// TODO: Removing all the throw new UnsupportedOperationException("Unimplemented method 'method'") lines
// TODO: Implementing the methods
public class IMPoweredMultiblockTileEntity<T extends IMPoweredMultiblockTileEntity<T, R>, R extends MultiblockRecipe> extends PoweredMultiblockTileEntity<T,R> {

    public IMPoweredMultiblockTileEntity(IETemplateMultiblock multiblockInstance, int energyCapacity, TileEntityType<? extends T> type) {
        super(multiblockInstance, energyCapacity, multiblockInstance.getStructure(null).stream().anyMatch((blockinfo)->blockinfo.state.equals(IEBlocks.MetalDecoration.engineeringRS.defaultBlockState())), type);
    }

    @Override
    public void doGraphicalUpdates() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'doGraphicalUpdates'");
    }

    @Override
    @Nullable
    public NonNullList<ItemStack> getInventory() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getInventory'");
    }

    @Override
    public int getSlotLimit(int arg0) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getSlotLimit'");
    }

    @Override
    public boolean isStackValid(int arg0, ItemStack arg1) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isStackValid'");
    }

    @Override
    public boolean additionalCanProcessCheck(MultiblockProcess<R> arg0) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'additionalCanProcessCheck'");
    }

    @Override
    public void doProcessFluidOutput(FluidStack arg0) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'doProcessFluidOutput'");
    }

    @Override
    public void doProcessOutput(ItemStack arg0) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'doProcessOutput'");
    }

    @Override
    @Nullable
    public R findRecipeForInsertion(ItemStack arg0) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findRecipeForInsertion'");
    }

    @Override
    public Set<BlockPos> getEnergyPos() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getEnergyPos'");
    }

    @Override
    @Nullable
    public IFluidTank[] getInternalTanks() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getInternalTanks'");
    }

    @Override
    public int getMaxProcessPerTick() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getMaxProcessPerTick'");
    }

    @Override
    public float getMinProcessDistance(MultiblockProcess<R> arg0) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getMinProcessDistance'");
    }

    @Override
    @Nullable
    public int[] getOutputSlots() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getOutputSlots'");
    }

    @Override
    @Nullable
    public int[] getOutputTanks() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getOutputTanks'");
    }

    @Override
    public int getProcessQueueMaxLength() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getProcessQueueMaxLength'");
    }

    @Override
    @Nullable
    protected R getRecipeForId(ResourceLocation arg0) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getRecipeForId'");
    }

    @Override
    public boolean isInWorldProcessingMachine() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isInWorldProcessingMachine'");
    }

    @Override
    public void onProcessFinish(MultiblockProcess<R> arg0) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'onProcessFinish'");
    }

    @Override
    protected boolean canDrainTankFrom(int arg0, Direction arg1) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'canDrainTankFrom'");
    }

    @Override
    protected boolean canFillTankFrom(int arg0, Direction arg1, FluidStack arg2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'canFillTankFrom'");
    }

    @Override
    @Nonnull
    protected IFluidTank[] getAccessibleFluidTanks(Direction arg0) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAccessibleFluidTanks'");
    }

}
