package net.kmn64.im.main.multiblocks;

import com.mojang.blaze3d.matrix.MatrixStack;

import blusunrize.immersiveengineering.client.ClientUtils;
import blusunrize.immersiveengineering.common.blocks.multiblocks.IETemplateMultiblock;
import net.kmn64.im.IMMain;
import net.kmn64.im.main.IMRegister;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public abstract class IMMultiblock extends IETemplateMultiblock {
    private final IMMultiblockBlock block;
    
    @SuppressWarnings("rawtypes")
    public IMMultiblock(String name, BlockPos masterFromOrigin, BlockPos triggerFromOrigin, BlockPos size) {
      super(new ResourceLocation(IMMain.MODID,String.format("multiblocks/%s", name)), masterFromOrigin, triggerFromOrigin, size, ()->IMRegister.findBlock(name).defaultBlockState());
      this.block = (IMMultiblockBlock)IMRegister.findBlock(name);
   }

    @Override
	@OnlyIn(Dist.CLIENT)
	public boolean canRenderFormedStructure(){
		return true;
	}

	@OnlyIn(Dist.CLIENT)
	public abstract void renderTransformFormedStructure(MatrixStack transform);
	
	@OnlyIn(Dist.CLIENT)
	private static ItemStack renderStack;
	
	@Override
	public void renderFormedStructure(MatrixStack transform, IRenderTypeBuffer buffer){
		if(renderStack == null)
			renderStack = new ItemStack(this.block);
		
		renderTransformFormedStructure(transform);
		
		ClientUtils.mc().getItemRenderer().renderStatic(
				renderStack,
				ItemCameraTransforms.TransformType.NONE,
				0xf000f0,
				OverlayTexture.NO_OVERLAY,
				transform, buffer);
	}
}
