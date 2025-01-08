package net.kmn64.im.main.multiblocks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;

import blusunrize.immersiveengineering.common.blocks.generic.MultiblockPartTileEntity;
import blusunrize.immersiveengineering.common.blocks.multiblocks.IETemplateMultiblock;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public abstract class IMMultiblockTileEntity<T extends MultiblockPartTileEntity<T>> extends MultiblockPartTileEntity<T> {
    public abstract FluidTank[] getFluidTank();
    public abstract int[] getInputTankIndex();
    public abstract int[] getOutputTankIndex();
    public abstract Map<BlockPos,Direction> getInputTankPos();
    public abstract Map<BlockPos,Direction> getOutputTankPos();

    public IMMultiblockTileEntity(IETemplateMultiblock multiblockInstance, TileEntityType<? extends T> type, boolean hasRSControl) {
        super(multiblockInstance,type,hasRSControl);
    }

    @Override
    protected boolean canDrainTankFrom(int arg0, Direction arg1) {
        return getFluidTank() != null;
    }

    @Override
    protected boolean canFillTankFrom(int arg0, Direction arg1, FluidStack arg2) {
        return getFluidTank() != null;
    }

    @Override
    @Nonnull
    protected IFluidTank[] getAccessibleFluidTanks(Direction arg0) {
        if (getFluidTank() == null) return new FluidTank[0];
        return getFluidTank();
    }
}
