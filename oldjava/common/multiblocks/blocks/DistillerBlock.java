package net.kmn64.ine.common.multiblocks.blocks;

import net.kmn64.ine.common.INETileTypes;
import net.kmn64.ine.common.multiblocks.tileentities.DistillerTileEntity;

public class DistillerBlock extends INEMetalMultiblock<DistillerTileEntity> {

	public DistillerBlock() {
		super("distiller", ()->INETileTypes.DISTILLER.get());
		// TODO Auto-generated constructor stub
	}

}
