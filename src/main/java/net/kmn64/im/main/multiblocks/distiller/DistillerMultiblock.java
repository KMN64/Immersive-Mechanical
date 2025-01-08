package net.kmn64.im.main.multiblocks.distiller;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.kmn64.im.main.multiblocks.IMMultiblock;
import net.minecraft.util.math.BlockPos;

public class DistillerMultiblock extends IMMultiblock {

    public DistillerMultiblock() {
        super("distiller",new BlockPos(1,1,1), new BlockPos(1,1,1), new BlockPos(3,3,3));
    }

    @Override
    public float getManualScale() {
        return 12;
    }

    @Override
    public void renderTransformFormedStructure(MatrixStack transform) {
        // "Undo" the GUI Perspective Transform
		transform.translate(.5, .5, .5);
    }
}
