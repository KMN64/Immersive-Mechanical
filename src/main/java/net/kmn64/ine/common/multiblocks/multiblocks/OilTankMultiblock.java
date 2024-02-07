package net.kmn64.ine.common.multiblocks.multiblocks;

import java.util.function.Supplier;

import com.mojang.blaze3d.matrix.MatrixStack;

import blusunrize.immersiveengineering.client.ClientUtils;
import blusunrize.immersiveengineering.common.blocks.multiblocks.IETemplateMultiblock;
import net.kmn64.ine.ImmersiveNuclearEngineering;
import net.kmn64.ine.common.INEContent;
import net.kmn64.ine.common.INEContent.Multiblocks;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class OilTankMultiblock extends IETemplateMultiblock {
	public static final OilTankMultiblock instance = new OilTankMultiblock();
	public OilTankMultiblock() {
		super(new ResourceLocation(ImmersiveNuclearEngineering.MODID, "multiblocks/oiltank"),
				new BlockPos(2, 0, 3), new BlockPos(2, 1, 5), new BlockPos(5, 4, 6),()-> INEContent.Multiblocks.oiltank.defaultBlockState());
		// TODO Auto-generated constructor stub
	}

	@Override
	public float getManualScale() {
		// TODO Auto-generated method stub
		return 12;
	}

	@Override
	public boolean canRenderFormedStructure() {
		// TODO Auto-generated method stub
		return true;
	}
	
	@OnlyIn(Dist.CLIENT)
	private static ItemStack renderStack;

	@Override
	public void renderFormedStructure(MatrixStack transform, IRenderTypeBuffer buffer) {
		// TODO Auto-generated method stub
		if(renderStack==null)
			renderStack = new ItemStack(Multiblocks.oiltank);
		transform.translate(1.875, 1.75, 1.125);
		transform.mulPose(new Quaternion(0, 45, 0, true));
		transform.mulPose(new Quaternion(-20, 0, 0, true));
		transform.scale(5.5F, 5.5F, 5.5F);

		ClientUtils.mc().getItemRenderer().renderStatic(
				renderStack,
				TransformType.GUI,
				0xf000f0,
				OverlayTexture.NO_OVERLAY,
				transform, buffer
		);
	}

}
