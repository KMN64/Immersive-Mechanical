package net.kmn64.im.main.multiblocks;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;

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
    public abstract FluidTank[] getFluidTank();
    public abstract int[] getInputTankIndex();
    public abstract int[] getOutputTankIndex();
    public abstract Map<BlockPos,Direction> getInputTankPos();
    public abstract Map<BlockPos,Direction> getOutputTankPos();

    private FluidTank[] fluidTanks;
    public IMTankMultiblockTileEntity(IETemplateMultiblock multiblockInstance, TileEntityType<? extends T> type, boolean hasRSControl) {
        super(multiblockInstance,type,hasRSControl);
        fluidTanks = getFluidTank();
    }

    @Override
    protected boolean canDrainTankFrom(int arg0, Direction side) {
        if (getFluidTank() == null) return false;
        return getOutputTankPos().keySet().stream().anyMatch((blockPosOut)->{
            Direction direction = getOutputTankPos().get(blockPosOut);
            return (side == null || side.equals(direction)) && this.posInMultiblock.equals(blockPosOut);
        });
        
    }

    @Override
    protected boolean canFillTankFrom(int arg0, Direction side, FluidStack arg2) {
        if (getFluidTank() == null) return false;
        return getInputTankPos().keySet().stream().anyMatch((blockPosIn)->{
            Direction direction = getInputTankPos().get(blockPosIn);
            return (side == null || side.equals(direction)) && this.posInMultiblock.equals(blockPosIn);
        });
    }

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
        if (getFluidTank() == null) return new FluidTank[0];
        List<FluidTank> tanks = new LinkedList<>();

        getInputTankPos().keySet().forEach((blockPosIn)->{
            if (this.posInMultiblock.equals(blockPosIn)) {
                int index = Arrays.asList(getInputTankPos().keySet().toArray()).indexOf(blockPosIn);
                int index2 = getInputTankIndex()[index];

                Direction direction = getInputTankPos().get(blockPosIn);
                if ((side == null || side.equals(direction)) && this.posInMultiblock.equals(blockPosIn)) tanks.add(fluidTanks[index2]);
            }
        });

        getOutputTankPos().keySet().forEach((blockPosOut)->{
            if (this.posInMultiblock.equals(blockPosOut)) {
                int index = Arrays.asList(getInputTankPos().keySet().toArray()).indexOf(blockPosOut);
                int index2 = getInputTankIndex()[index];
                tanks.add(fluidTanks[index2]);

                Direction direction = getInputTankPos().get(blockPosOut);
                if ((side == null || side.equals(direction)) && this.posInMultiblock.equals(blockPosOut)) tanks.add(fluidTanks[index2]);
            }
        });

        return tanks.toArray(new FluidTank[0]);
    }
}
