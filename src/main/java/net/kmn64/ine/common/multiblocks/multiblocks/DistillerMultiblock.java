package net.kmn64.ine.common.multiblocks.multiblocks;

import java.util.function.Supplier;

import com.mojang.blaze3d.matrix.MatrixStack;

import blusunrize.immersiveengineering.client.ClientUtils;
import blusunrize.immersiveengineering.common.blocks.multiblocks.IETemplateMultiblock;
import net.kmn64.ine.ImmersiveNuclearEngineering;
import net.kmn64.ine.common.INEContent;
import net.minecraft.block.Block;
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

public class DistillerMultiblock extends IETemplateMultiblock  {
	public static final DistillerMultiblock instance = new DistillerMultiblock();
	public DistillerMultiblock() {
		super(new ResourceLocation(ImmersiveNuclearEngineering.MODID, "multiblocks/distiller"), new BlockPos(1,1,1), new BlockPos(1,1,1), new BlockPos(3,3,3), ()->INEContent.Multiblocks.distiller.defaultBlockState());
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean canRenderFormedStructure() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public float getManualScale() {
		// TODO Auto-generated method stub
		return 13;
	}
	
	@OnlyIn(Dist.CLIENT)
	private static ItemStack renderStack;

	@Override
	public void renderFormedStructure(MatrixStack transform, IRenderTypeBuffer buffer) {
		// TODO Auto-generated method stub
		if(renderStack == null) renderStack = new ItemStack(INEContent.Multiblocks.distiller);
		transform.translate(1.5, 1.5, 1.5);
		transform.mulPose(new Quaternion(-45, 0, 1, 0));
		transform.mulPose(new Quaternion(-20, 1, 0, 0));
		transform.scale(4, 4, 4);
		ClientUtils.mc().getItemRenderer().renderStatic(renderStack, TransformType.GUI,0xf000f0,OverlayTexture.NO_OVERLAY,transform, buffer);
	}

}
