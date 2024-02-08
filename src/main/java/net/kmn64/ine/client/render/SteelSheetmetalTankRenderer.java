package net.kmn64.ine.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import blusunrize.immersiveengineering.client.utils.GuiHelper;
import blusunrize.immersiveengineering.client.utils.IERenderTypes;
import blusunrize.immersiveengineering.common.blocks.metal.SheetmetalTankTileEntity;
import net.kmn64.ine.ImmersiveNuclearEngineering;
import net.kmn64.ine.common.multiblocks.tileentities.SteelSheetmetalTankTileEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fluids.FluidStack;

public class SteelSheetmetalTankRenderer extends TileEntityRenderer<SteelSheetmetalTankTileEntity>
{
	  public SteelSheetmetalTankRenderer(TileEntityRendererDispatcher rendererDispatcherIn) { super(rendererDispatcherIn); }



	  
	  public void render(SteelSheetmetalTankTileEntity tile, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
	    if (!tile.formed || tile.isDummy() || !tile.getWorldNonnull().hasChunkAt(tile.getBlockPos()))
	      return; 
	    matrixStack.pushPose();
	    
	    matrixStack.translate(0.5D, 0.0D, 0.5D);
	    
	    FluidStack fs = tile.tank.getFluid();
	    matrixStack.translate(0.0D, 3.5D, 0.0D);
	    float baseScale = 0.0625F;
	    matrixStack.scale(baseScale, -baseScale, baseScale);
	    
	    float xx = -0.5F;
	    float zz = 1.496F;
	    xx /= baseScale;
	    zz /= baseScale;
	    for (int i = 0; i < 4; i++) {
	      
	      matrixStack.pushPose();
	      matrixStack.translate(xx, 0.0D, zz);
	      
	      Matrix4f mat = matrixStack.last().pose();
	      IVertexBuilder builder = bufferIn.getBuffer(INERenderTypes.TRANSLUCENT_POSITION_COLOR);
	      builder.vertex(mat, -4.0F, -4.0F, 0.0F).color(34, 34, 34, 255).endVertex();
	      builder.vertex(mat, -4.0F, 20.0F, 0.0F).color(34, 34, 34, 255).endVertex();
	      builder.vertex(mat, 20.0F, 20.0F, 0.0F).color(34, 34, 34, 255).endVertex();
	      builder.vertex(mat, 20.0F, -4.0F, 0.0F).color(34, 34, 34, 255).endVertex();
	      
	      if (!fs.isEmpty()) {
	        
	        float h = fs.getAmount() / tile.tank.getCapacity();
	        matrixStack.translate(0.0D, 0.0D, 0.004000000189989805D);
	        GuiHelper.drawRepeatedFluidSprite(bufferIn.getBuffer(RenderType.solid()), matrixStack, fs, 0.0F, 0.0F + (1.0F - h) * 16.0F, 16.0F, h * 16.0F);
	      } 
	      
	      matrixStack.popPose();
	      matrixStack.mulPose(new Quaternion(new Vector3f(0.0F, 1.0F, 0.0F), 90.0F, true));
	    } 
	    matrixStack.popPose();
	  }
}
