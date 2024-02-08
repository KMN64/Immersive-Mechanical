package net.kmn64.ine.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import blusunrize.immersiveengineering.client.utils.GuiHelper;
import net.kmn64.ine.ImmersiveNuclearEngineering;
import net.kmn64.ine.common.multiblocks.tileentities.OilTankTileEntity;
import net.kmn64.ine.common.multiblocks.tileentities.OilTankTileEntity.Port;
import net.kmn64.ine.common.multiblocks.tileentities.OilTankTileEntity.PortState;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

public class OilTankRenderer extends TileEntityRenderer<OilTankTileEntity>{

	public OilTankRenderer(TileEntityRendererDispatcher p_i226006_1_) {
		super(p_i226006_1_);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void render(OilTankTileEntity te, float partialTicks, MatrixStack matrix,
			IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) {
		// TODO Auto-generated method stub
		if(!te.formed || te.isDummy() || !te.getLevel().hasChunkAt(te.getBlockPos()))
			return;
		
		combinedOverlay = OverlayTexture.NO_OVERLAY;
		
		matrix.pushPose();
		{
			switch(te.getFacing()){
				case EAST -> {
					matrix.mulPose(new Quaternion(0, 270F, 0, true));
					matrix.translate(0, 0, -1);
				}
				case SOUTH -> {
					matrix.mulPose(new Quaternion(0F, 180F, 0F, true));
					matrix.translate(-1, 0, -1);
				}
				case WEST -> {
					matrix.mulPose(new Quaternion(0, 90F, 0, true));
					matrix.translate(-1, 0, 0);
				}
				default -> {
				}
			}
			
			// Tank Display
			matrix.pushPose();
			{
				matrix.translate(1, 2, 2.995F);
				
				// Background
				Matrix4f mat = matrix.last().pose();//SheetmetalTankRenderer
				IVertexBuilder builder = buffer.getBuffer(INERenderTypes.TRANSLUCENT_POSITION_COLOR);
				builder.vertex(mat, 1.5F, -0.5F, 0.0F).color(34, 34, 34, 255).endVertex();
				builder.vertex(mat, 1.5F, 1F, 0.0F).color(34, 34, 34, 255).endVertex();
				builder.vertex(mat, 0F, 1F, 0.0F).color(34, 34, 34, 255).endVertex();
				builder.vertex(mat, 0F, -0.5F, 0.0F).color(34, 34, 34, 255).endVertex();
				
				OilTankTileEntity master = te.master();
				if(master != null){
					FluidStack fs = master.tank.getFluid();
					if(!fs.isEmpty()){
						matrix.pushPose();
						{
							matrix.translate(0.25, 0.875, 0.0025F);
							matrix.scale(0.0625F, -0.0625F, 0.0625F);
							
							float h = fs.getAmount() / (float) master.tank.getCapacity();
							GuiHelper.drawRepeatedFluidSprite(buffer.getBuffer(RenderType.solid()), matrix, fs, 0, 0 + (1 - h) * 16, 16, h * 16);
						}
						matrix.popPose();
					}
				}
			}
			matrix.popPose();
			
			matrix.pushPose();
			{
				// Dynamic Fluid IO Ports
				if(te.getIsMirrored()){
					OilTankTileEntity master = te.master();
					if(master != null){
						for(Port port:Port.DYNAMIC_PORTS){
							matrix.pushPose();
							{
								BlockPos p = port.posInMultiblock.subtract(te.posInMultiblock);
								matrix.mulPose(new Quaternion(0, 180F, 0, true));
								matrix.translate(p.getX() - 1, p.getY(), -p.getZ() - 1);
								quad(matrix, buffer, master.getPortStateFor(port), port.posInMultiblock.getX() == 4, combinedLight, combinedOverlay);
							}
							matrix.popPose();
						}
					}
				}else{
					OilTankTileEntity master = te.master();
					if(master != null){
						for(Port port:Port.DYNAMIC_PORTS){
							matrix.pushPose();
							{
								BlockPos p = port.posInMultiblock.subtract(te.posInMultiblock);
								matrix.translate(p.getX(), p.getY(), p.getZ());
								quad(matrix, buffer, master.getPortStateFor(port), port.posInMultiblock.getX() == 4, combinedLight, combinedOverlay);
							}
							matrix.popPose();
						}
					}
				}
			}
			matrix.popPose();
		}
		matrix.popPose();
	}
	
	public void quad(MatrixStack matrix, IRenderTypeBuffer buffer, PortState portState, boolean flip, int combinedLight, int combinedOverlay){
		Matrix4f mat = matrix.last().pose();
		IVertexBuilder builder = buffer.getBuffer(INERenderTypes.OIL_TANK);
		
		boolean input = portState == PortState.INPUT;
		float u0 = input ? 0.0F : 0.1F, v0 = 0.5F;
		float u1 = u0 + 0.1F, v1 = v0 + 0.1F;
		if(flip){
			builder.vertex(mat, 1.001F, 0F, 0F).color(1F, 1F, 1F, 1F).uv(u1, v1).overlayCoords(combinedOverlay).uv2(combinedLight).normal(1, 1, 1).endVertex();
			builder.vertex(mat, 1.001F, 1F, 0F).color(1F, 1F, 1F, 1F).uv(u1, v0).overlayCoords(combinedOverlay).uv2(combinedLight).normal(1, 1, 1).endVertex();
			builder.vertex(mat, 1.001F, 1F, 1F).color(1F, 1F, 1F, 1F).uv(u0, v0).overlayCoords(combinedOverlay).uv2(combinedLight).normal(1, 1, 1).endVertex();
			builder.vertex(mat, 1.001F, 0F, 1F).color(1F, 1F, 1F, 1F).uv(u0, v1).overlayCoords(combinedOverlay).uv2(combinedLight).normal(1, 1, 1).endVertex();
		}else{
			builder.vertex(mat, -0.001F, 0F, 0F).color(1F, 1F, 1F, 1F).uv(u0, v1).overlayCoords(combinedOverlay).uv2(combinedLight).normal(1, 1, 1).endVertex();
			builder.vertex(mat, -0.001F, 0F, 1F).color(1F, 1F, 1F, 1F).uv(u1, v1).overlayCoords(combinedOverlay).uv2(combinedLight).normal(1, 1, 1).endVertex();
			builder.vertex(mat, -0.001F, 1F, 1F).color(1F, 1F, 1F, 1F).uv(u1, v0).overlayCoords(combinedOverlay).uv2(combinedLight).normal(1, 1, 1).endVertex();
			builder.vertex(mat, -0.001F, 1F, 0F).color(1F, 1F, 1F, 1F).uv(u0, v0).overlayCoords(combinedOverlay).uv2(combinedLight).normal(1, 1, 1).endVertex();
		}
	}

}
