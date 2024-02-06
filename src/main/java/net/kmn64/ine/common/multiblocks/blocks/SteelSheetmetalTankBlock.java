package net.kmn64.ine.common.multiblocks.blocks;

import java.util.function.Supplier;

import blusunrize.immersiveengineering.common.blocks.metal.MetalMultiblockBlock;
import net.kmn64.ine.common.INETileTypes;
import net.kmn64.ine.common.multiblocks.tileentities.SteelSheetmetalTankTileEntity;
import net.minecraft.tileentity.TileEntityType;

public class SteelSheetmetalTankBlock extends INEMetalMultiblock<SteelSheetmetalTankTileEntity> {

	public SteelSheetmetalTankBlock() {
		super("steel_tank",()->INETileTypes.STEEL_TANK.get());
		// TODO Auto-generated constructor stub
	}

}
