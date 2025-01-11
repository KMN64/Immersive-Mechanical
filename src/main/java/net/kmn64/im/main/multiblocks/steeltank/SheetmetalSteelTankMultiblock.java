package net.kmn64.im.main.multiblocks.steeltank;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.kmn64.im.main.multiblocks.IMMultiblock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Quaternion;

public class SheetmetalSteelTankMultiblock extends IMMultiblock {
    public SheetmetalSteelTankMultiblock() {
        super("steeltank", new BlockPos(1, 1, 1), new BlockPos(1, 1, 1), new BlockPos(3, 3, 3));
    }

    @Override
    public float getManualScale() {
        return 12;
    }

    @Override
    public void renderTransformFormedStructure(MatrixStack transform) {
        transform.translate(1.875, 1.75, 1.125);
        transform.mulPose(new Quaternion(0.0F, 45.0F, 0.0F, true));
        transform.mulPose(new Quaternion(-20.0F, 0.0F, 0.0F, true));
        transform.scale(5.5F, 5.5F, 5.5F);
    }
    
}
