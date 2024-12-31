package net.kmn64.ine.common.multiblocks.blocks;

import net.kmn64.ine.common.INETileTypes;
import net.kmn64.ine.common.multiblocks.tileentities.OilTankTileEntity;

public class OilTankBlock extends INEMetalMultiblock<OilTankTileEntity> {

	public OilTankBlock() {
		super("oil_tank",()-> INETileTypes.OIL_TANK.get());
		// TODO Auto-generated constructor stub
	}

}
