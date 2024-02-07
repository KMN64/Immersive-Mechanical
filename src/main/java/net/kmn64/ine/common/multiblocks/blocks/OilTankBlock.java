package net.kmn64.ine.common.multiblocks.blocks;

import com.google.common.base.Supplier;

import net.kmn64.ine.common.INETileTypes;
import net.kmn64.ine.common.multiblocks.tileentities.OilTankTileEntity;
import net.minecraft.tileentity.TileEntityType;

public class OilTankBlock extends INEMetalMultiblock<OilTankTileEntity> {

	public OilTankBlock() {
		super("oil_tank",()-> INETileTypes.OIL_TANK.get());
		// TODO Auto-generated constructor stub
	}

}
