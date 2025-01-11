package net.kmn64.im.main.multiblocks;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import blusunrize.immersiveengineering.api.crafting.MultiblockRecipe;
import blusunrize.immersiveengineering.common.blocks.IEBlocks;
import blusunrize.immersiveengineering.common.blocks.generic.PoweredMultiblockTileEntity;
import blusunrize.immersiveengineering.common.blocks.multiblocks.IETemplateMultiblock;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.templates.FluidTank;


/**
 * The IMPoweredMultiblockTileEntity class represents a tile entity for a powered multiblock structure.
 * It extends the PoweredMultiblockTileEntity class and provides additional functionality specific to
 * powered multiblock structures.
 *
 * @param <T> The type of the tile entity.
 * @param <R> The type of the multiblock recipe.
 */
public abstract class IMPoweredMultiblockTileEntity<T extends IMPoweredMultiblockTileEntity<T, R>, R extends MultiblockRecipe> extends PoweredMultiblockTileEntity<T,R> {

    private final NonNullList<ItemStack> inventory = NonNullList.withSize(getinvSize(), ItemStack.EMPTY);
    private IFluidTank[] fluidTanks;
    public IMPoweredMultiblockTileEntity(IETemplateMultiblock multiblockInstance, int energyCapacity, TileEntityType<? extends T> type) {
        super(multiblockInstance, energyCapacity, multiblockInstance.getStructure(null).stream().anyMatch((blockinfo)->blockinfo.state.equals(IEBlocks.MetalDecoration.engineeringRS.defaultBlockState())), type);
        fluidTanks = getInternalTanks();
    }

    /**
     * Retrieves the size of the inventory for the tile entity.
     *
     * @return the size of the inventory
     */
    public abstract int getinvSize();

    /**
     * Retrieves the positions and directions of the input tanks for this multiblock tile entity.
     *
     * @return A map where the keys are the positions of the input tanks and the values are the directions they face,
     *         or null if there are no input tanks.
     */
    @Nullable
    public abstract Map<BlockPos,Direction> getInputTanksPos();

    /**
     * Retrieves the positions and directions of the output tanks.
     *
     * @return A map where the keys are the positions of the output tanks and the values are the directions they face,
     *         or null if there are no output tanks.
     */
    @Nullable
    public abstract Map<BlockPos,Direction> getOutputTanksPos();

    /**
     * Performs graphical updates for the tile entity.
     * This method is called to update the visual representation
     * of the tile entity in the game world.
     */
    @Override
    public void doGraphicalUpdates() {
        this.markChunkDirty();
        this.markContainingBlockForUpdate(null);
    }

    /**
     * Retrieves the inventory of the tile entity.
     *
     * @return a NonNullList of ItemStacks representing the inventory, or null if the inventory is not available.
     */
    @Override
    @Nullable
    public NonNullList<ItemStack> getInventory() {
        // As of now, the inventory is not implemented.
        // This method should be overridden to return the appropriate value.
        // For example, the Arc Furnace has an inventory.
        // In that case, this method should be overridden to return the appropriate value.
        // For example, return this.inventory;
        // where inventory is a NonNullList<ItemStack> field in the class.
        // This method should return null if the inventory is not available.
        // For example, return null;
        // if the inventory is not available.
        return this.inventory;
    }
    
    /**
     * Returns the maximum number of items that can be stored in the specified slot.
     *
     * @param arg0 the index of the slot
     * @return the maximum number of items that can be stored in the specified slot
     */
    @Override
    public int getSlotLimit(int arg0) {
        return 64;
    }
    
    /**
     * Checks if the given ItemStack is valid for the specified slot.
     *
     * @param arg0 the slot index to check
     * @param arg1 the ItemStack to validate
     * @return true if the ItemStack is valid for the slot, false otherwise
     */
    @Override
    public boolean isStackValid(int arg0, ItemStack arg1) {
        // As of now, the inventory is implemented but can be changed.
        // This method should be overridden to return the appropriate value.
        // For example, the Arc Furnace has specific items that can be inserted.
        // In that case, this method should be overridden to return the appropriate value.
        // For example, return true;
        // if the ItemStack is valid for the slot.
        // This method should return false if the ItemStack is not valid for the slot.
        // For example, return false;
        return true;
    }

    /**
     * This method performs an additional check to determine if the multiblock process can proceed.
     * 
     * @param arg0 the multiblock process to check
     * @return always returns false, indicating that the process cannot proceed
     */
    @Override
    public boolean additionalCanProcessCheck(MultiblockProcess<R> arg0) {
        return false;
    }

    /**
     * Processes the output of a fluid.
     *
     * @param arg0 the FluidStack to be processed
     */
    @Override
    public void doProcessFluidOutput(FluidStack arg0) {
    }

    /**
     * Processes the output of the multiblock tile entity.
     *
     * @param arg0 the ItemStack to be processed as output
     */
    @Override
    public void doProcessOutput(ItemStack arg0) { 
    }


    /**
     * Finds a recipe for the given item stack to be inserted.
     *
     * @param arg0 the item stack to find a recipe for
     * @return the recipe that matches the item stack, or null if no matching recipe is found
     */
    @Override
    @Nullable
    public R findRecipeForInsertion(ItemStack arg0) {
        return null;
    }

    /**
     * Retrieves a set of BlockPos objects that represent the positions
     * where energy is stored or transferred within the multiblock structure.
     *
     * @return a set of BlockPos objects indicating the energy positions.
     */
    // As of now,the energy position is not implemented.
    // This method should be overridden to return the appropriate value.
    // For example, the Arc Furnace has energy positions.
    // In that case, this method should be overridden to return the appropriate value.
    @Override
    public abstract Set<BlockPos> getEnergyPos();

    /**
     * Retrieves all redstone positions in the multiblock structure.
     * This method overrides the parent class method to include additional redstone positions
     * specific to the multiblock structure defined in this class.
     *
     * @return A set of BlockPos objects representing the positions of redstone components
     *         within the multiblock structure.
     */
    @Override
    public Set<BlockPos> getRedstonePos() {
        // Get all redstone positions in the multiblock structure
        Set<BlockPos> redstonePos = super.getRedstonePos();
        multiblockInstance.getStructure(null).stream().filter((blockInfo)->blockInfo.state.equals(IEBlocks.MetalDecoration.engineeringRS.defaultBlockState())).forEach((blockInfo)->{
            redstonePos.add(blockInfo.pos);
        });
        
        return redstonePos;
    }

    /**
     * Retrieves the internal fluid tanks of this tile entity.
     *
     * @return An array of {@link IFluidTank} representing the internal tanks, or null if there are no internal tanks.
     */
    // As of now, the internal tanks are not implemented.
    // This method should be overridden to return the appropriate value.
    // For example, the Mixer has internal tanks.
    // In that case, this method should be overridden to return the appropriate value.
    // For example, return this.tanks;
    // where tanks is an array of IFluidTank fields in the class.
    // This method should return null if there are no internal tanks.
    // For example, return null;
    // if there are no internal tanks.
    @Override
    @Nullable
    public abstract IFluidTank[] getInternalTanks();

    @Override
    public int getMaxProcessPerTick() {
        // As of now, only one process can be done per tick.
        // Some multiblock machines can do more than one process per tick.
        // In that case, this method should be overridden to return the appropriate value.
        // For example, the Arc Furnace can do 2 processes per tick.
        // In that case, this method should be overridden to return 2.
        return 1; 
    }

    /**
     * Returns the minimum distance required for the process to start.
     *
     * @param arg0 the multiblock process
     * @return the minimum distance for the process to start, which is 1
     */
    @Override
    public float getMinProcessDistance(MultiblockProcess<R> arg0) {
        // The minimum distance for the process to start is 1.
        return 1;
    }

    /**
     * Returns an array of output slot indices for this tile entity.
     * 
     * @return an array of integers representing the output slot indices, or null if there are no output slots.
     */
    @Override
    @Nullable
    public abstract int[] getOutputSlots();

    /**
     * Retrieves the input tanks for this tile entity.
     *
     * @return an array of integers representing the input tanks, or null if there are no input tanks.
     */
    @Nullable
    public abstract int[] getInputTanks();

    /**
     * Retrieves the indices of the output tanks for this tile entity.
     *
     * @return An array of integers representing the indices of the output tanks,
     *         or null if there are no output tanks.
     */
    @Override
    @Nullable
    public abstract int[] getOutputTanks();

    /**
     * Returns the maximum length of the process queue.
     *
     * @return the maximum length of the process queue, which is 1.
     */
    @Override
    public int getProcessQueueMaxLength() {
        return 1;
    }
    
    /**
     * Retrieves the recipe associated with the given resource location.
     *
     * @param arg0 the resource location of the recipe
     * @return the recipe associated with the given resource location, or null if no recipe is found
     */
    @Override
    @Nullable
    protected R getRecipeForId(ResourceLocation arg0) {
        return null;
    }

    /**
     * Determines if the machine is an in-world processing machine.
     * 
     * @return false, indicating that this machine is not an in-world processing machine.
     */
    @Override
    public boolean isInWorldProcessingMachine() {
        return false;
    }

    /**
     * This method is called when a multiblock process finishes.
     *
     * @param arg0 the multiblock process that has finished
     */
    @Override
    public void onProcessFinish(MultiblockProcess<R> arg0) {
    }

    /**
     * Checks if the tank can be drained from the specified side.
     *
     * @param arg0 the index of the tank to check
     * @param side the direction from which the tank is being drained
     * @return true if the tank can be drained from the specified side, false otherwise
     */
    @Override
    protected abstract boolean canDrainTankFrom(int arg0, Direction side);

    /**
     * Determines if the tank can be filled from a specific side with a given fluid.
     *
     * @param arg0 the tank index or identifier
     * @param side the direction from which the fluid is being inserted
     * @param arg2 the fluid stack to be inserted
     * @return true if the tank can be filled from the specified side with the given fluid, false otherwise
     */
    @Override
    protected abstract boolean canFillTankFrom(int arg0, Direction side, FluidStack arg2);

    @Override
    @Nonnull
    protected IFluidTank[] getAccessibleFluidTanks(Direction side) {
        if (fluidTanks == null) return new FluidTank[0];
        List<IFluidTank> tanks = new LinkedList<>();

        getInputTanksPos().keySet().forEach((blockPosIn)->{
            if (this.posInMultiblock.equals(blockPosIn)) {
                int index = Arrays.asList(getInputTanksPos().keySet().toArray()).indexOf(blockPosIn);
                int index2 = getInputTanks()[index];

                Direction direction = getInputTanksPos().get(blockPosIn);
                if ((side == null || side.equals(direction)) && this.posInMultiblock.equals(blockPosIn)) tanks.add(fluidTanks[index2]);
            }
        });

        getOutputTanksPos().keySet().forEach((blockPosOut)->{
            if (this.posInMultiblock.equals(blockPosOut)) {
                int index = Arrays.asList(getInputTanksPos().keySet().toArray()).indexOf(blockPosOut);
                int index2 = getOutputTanks()[index];

                Direction direction = getInputTanksPos().get(blockPosOut);
                if ((side == null || side.equals(direction)) && this.posInMultiblock.equals(blockPosOut)) tanks.add(fluidTanks[index2]);
            }
        });

        return tanks.toArray(new FluidTank[0]);
    }

}
