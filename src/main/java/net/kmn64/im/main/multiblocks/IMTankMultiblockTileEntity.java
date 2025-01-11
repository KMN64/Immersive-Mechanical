package net.kmn64.im.main.multiblocks;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import blusunrize.immersiveengineering.common.blocks.IEBlocks;
import blusunrize.immersiveengineering.common.blocks.generic.MultiblockPartTileEntity;
import blusunrize.immersiveengineering.common.blocks.metal.SheetmetalTankTileEntity;
import blusunrize.immersiveengineering.common.blocks.multiblocks.IETemplateMultiblock;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public abstract class IMTankMultiblockTileEntity<T extends MultiblockPartTileEntity<T>> extends MultiblockPartTileEntity<T> {
    
    @Nullable
    public abstract FluidTank[] getFluidTanks();

    @Nullable
    public abstract int[] getInputTanksIndex();

    @Nullable
    public abstract int[] getOutputTanksIndex();

    @Nullable
    public abstract Map<BlockPos,Direction> getInputTanksPos();

    @Nullable
    public abstract Map<BlockPos,Direction> getOutputTanksPos();

    private FluidTank[] fluidTanks;
    public IMTankMultiblockTileEntity(IETemplateMultiblock multiblockInstance, TileEntityType<? extends T> type) {
        super(multiblockInstance,type,multiblockInstance.getStructure(null).stream().anyMatch((blockinfo)->blockinfo.state.equals(IEBlocks.MetalDecoration.engineeringRS.defaultBlockState())));
        fluidTanks = getFluidTanks();
    }

    /**
     * Retrieves all redstone positions in the multiblock structure.
     * This method overrides the parent class's getRedstonePos method to include
     * additional redstone positions specific to the multiblock structure.
     *
     * @return A set of BlockPos objects representing the positions of redstone blocks
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
     * This method is called every tick to update the state of the tile entity.
     * Override this method to implement custom behavior that should occur
     * every game tick.
     */
    // This method is called every tick to update the state of the tile entity.
    // Override this method to implement custom behavior that should occur every game tick.
    @Override
    public abstract void tick();

    /**
     * Determines if the tank can be drained from a specific side.
     *
     * @param arg0 Unused parameter.
     * @param side The direction from which the tank is being drained.
     * @return true if the tank can be drained from the specified side, false otherwise.
     */
    @Override
    protected boolean canDrainTankFrom(int arg0, Direction side) {
        if (fluidTanks == null) return false;
        return getOutputTanksPos().keySet().stream().anyMatch((blockPosOut)->{
            Direction direction = getOutputTanksPos().get(blockPosOut);
            return (side == null || side.equals(direction)) && this.posInMultiblock.equals(blockPosOut);
        });
        
    }

    /**
     * Determines if the tank can be filled from a specific side with a given fluid.
     *
     * @param arg0 Unused parameter.
     * @param side The direction from which the fluid is being inserted.
     * @param arg2 The fluid stack to be inserted.
     * @return true if the tank can be filled from the specified side with the given fluid, false otherwise.
     */
    @Override
    protected boolean canFillTankFrom(int arg0, Direction side, FluidStack arg2) {
        if (fluidTanks == null) return false;
        return getInputTanksPos().keySet().stream().anyMatch((blockPosIn)->{
            Direction direction = getInputTanksPos().get(blockPosIn);
            return (side == null || side.equals(direction)) && this.posInMultiblock.equals(blockPosIn);
        });
    }


    /**
     * Reads custom NBT data from the given CompoundNBT and updates the fluid tanks.
     *
     * @param nbt The CompoundNBT containing the data to read.
     * @param descPacket A boolean indicating whether the data is from a description packet.
     */
    @Override
    public void readCustomNBT(CompoundNBT nbt, boolean descPacket) {
        super.readCustomNBT(nbt, descPacket);
        List<FluidTank> tanks = new LinkedList<>();
        for (FluidTank fluidTank : fluidTanks) {
            int index = Arrays.asList(fluidTanks).indexOf(fluidTank);
            CompoundNBT tankTag = nbt.getCompound(String.format("tank%s",index));
            tanks.add(fluidTank.readFromNBT(tankTag));
        }

        fluidTanks = tanks.toArray(new FluidTank[0]);
    }


    /**
     * Writes custom NBT data to the provided CompoundNBT.
     * This method is used to save the state of the fluid tanks in the multiblock tile entity.
     *
     * @param nbt The CompoundNBT to write the data to.
     * @param descPacket A boolean flag indicating whether this is for a description packet.
     */
    @Override
    public void writeCustomNBT(CompoundNBT nbt, boolean descPacket) {
        super.writeCustomNBT(nbt, descPacket);
        for (FluidTank fluidTank : fluidTanks) {
            int index = Arrays.asList(fluidTanks).indexOf(fluidTank);
            CompoundNBT tankTag = fluidTank.writeToNBT(new CompoundNBT());
            nbt.put(String.format("tank%s",index), tankTag);
        }
    }


    @Override
    @Nonnull
    protected IFluidTank[] getAccessibleFluidTanks(Direction side) {
        if (fluidTanks == null) return new FluidTank[0];
        List<FluidTank> tanks = new LinkedList<>();

        getInputTanksPos().keySet().forEach((blockPosIn)->{
            if (this.posInMultiblock.equals(blockPosIn)) {
                int index = Arrays.asList(getInputTanksPos().keySet().toArray()).indexOf(blockPosIn);
                int index2 = getInputTanksIndex()[index];

                Direction direction = getInputTanksPos().get(blockPosIn);
                if ((side == null || side.equals(direction)) && this.posInMultiblock.equals(blockPosIn)) tanks.add(fluidTanks[index2]);
            }
        });

        getOutputTanksPos().keySet().forEach((blockPosOut)->{
            if (this.posInMultiblock.equals(blockPosOut)) {
                int index = Arrays.asList(getInputTanksPos().keySet().toArray()).indexOf(blockPosOut);
                int index2 = getOutputTanksIndex()[index];

                Direction direction = getInputTanksPos().get(blockPosOut);
                if ((side == null || side.equals(direction)) && this.posInMultiblock.equals(blockPosOut)) tanks.add(fluidTanks[index2]);
            }
        });

        return tanks.toArray(new FluidTank[0]);
    }
}
