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
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

public class SteelSheetmetalTankMultiblock extends IETemplateMultiblock {
	public static SteelSheetmetalTankMultiblock instance = new SteelSheetmetalTankMultiblock();
	public SteelSheetmetalTankMultiblock() {
		super(new ResourceLocation(ImmersiveNuclearEngineering.MODID, "multiblocks/steel_sheetmetal_tank"),
				new BlockPos(1, 0, 1), new BlockPos(1, 1, 2), new BlockPos(3, 5, 3), ()->INEContent.Multiblocks.steeltank.defaultBlockState());
		// TODO Auto-generated constructor stub
	}

	@Override
	public float getManualScale() {
		// TODO Auto-generated method stub
		return 10;
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
			renderStack = new ItemStack(Multiblocks.steeltank);
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
