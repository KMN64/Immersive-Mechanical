package net.kmn64.ine.client.render;

import java.util.OptionalDouble;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.systems.RenderSystem;

import net.kmn64.ine.ImmersiveNuclearEngineering;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderState;
import net.minecraft.client.renderer.RenderState.LineState;
import net.minecraft.client.renderer.RenderState.TextureState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;

/** Copy from {@link https://github.com/TwistedGate/ImmersivePetroleum/blob/1.16.5/src/main/java/flaxbeard/immersivepetroleum/client/render/IPRenderTypes.java}**/
public class INERenderTypes {
	static final ResourceLocation oilTankTexture = new ResourceLocation(ImmersiveNuclearEngineering.MODID,"textures/multiblocks/oil_tank.png");
	
	public static final RenderType OIL_TANK;
	public static final RenderType TRANSLUCENT_LINES;
	public static final RenderType TRANSLUCENT_POSITION_COLOR;
	
	static final RenderState.ShadeModelState SHADE_ENABLED = new RenderState.ShadeModelState(true);
	static final RenderState.TextureState TEXTURE_OIL_TANK = new RenderState.TextureState(oilTankTexture, false, false);
	static final RenderState.LightmapState LIGHTMAP_ENABLED = new RenderState.LightmapState(true);
	static final RenderState.OverlayState OVERLAY_ENABLED = new RenderState.OverlayState(true);
	static final RenderState.OverlayState OVERLAY_DISABLED = new RenderState.OverlayState(false);
	static final RenderState.DepthTestState DEPTH_ALWAYS = new RenderState.DepthTestState("always", GL11.GL_ALWAYS);
	static final RenderState.TransparencyState TRANSLUCENT_TRANSPARENCY = new RenderState.TransparencyState("translucent_transparency", () -> {
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
	}, RenderSystem::disableBlend);
	static final RenderState.TransparencyState NO_TRANSPARENCY = new RenderState.TransparencyState("no_transparency", () -> {
		RenderSystem.disableBlend();
	}, () -> {
	});
	static final RenderState.DiffuseLightingState DIFFUSE_LIGHTING_ENABLED = new RenderState.DiffuseLightingState(true);
	static final RenderType.State TRANSLUCENT_NOTEXTURE_STATE = RenderType.State.builder().setTransparencyState(TRANSLUCENT_TRANSPARENCY).setTextureState(new RenderState.TextureState()).createCompositeState(false);
	
	static{
		TRANSLUCENT_LINES = RenderType.create(
				ImmersiveNuclearEngineering.MODID+":translucent_lines",
				DefaultVertexFormats.POSITION_COLOR,
				GL11.GL_LINES,
				256,
				RenderType.State.builder().setTransparencyState(TRANSLUCENT_TRANSPARENCY)
					.setLineState(new LineState(OptionalDouble.of(3.5)))
					.setTextureState(new TextureState())
					.setDepthTestState(DEPTH_ALWAYS)
					.createCompositeState(false)
		);
		TRANSLUCENT_POSITION_COLOR = RenderType.create(ImmersiveNuclearEngineering.MODID+":translucent_pos_color", DefaultVertexFormats.POSITION_COLOR, 7, 256, TRANSLUCENT_NOTEXTURE_STATE);
		OIL_TANK = RenderType.create(
				ImmersiveNuclearEngineering.MODID+":oil_tank",
				DefaultVertexFormats.BLOCK,
				GL11.GL_QUADS,
				256,
				true,
				false,
				RenderType.State.builder()
					.setTextureState(TEXTURE_OIL_TANK)
					.setTransparencyState(TRANSLUCENT_TRANSPARENCY)
					.setLightmapState(LIGHTMAP_ENABLED)
					.setOverlayState(OVERLAY_DISABLED)
					.createCompositeState(false)
		);
	}
	
	/** Same as vanilla, just without an overlay */
	public static RenderType getEntitySolid(ResourceLocation locationIn){
		RenderType.State renderState = RenderType.State.builder()
				.setTextureState(new RenderState.TextureState(locationIn, false, false))
				.setTransparencyState(NO_TRANSPARENCY)
				.setDiffuseLightingState(DIFFUSE_LIGHTING_ENABLED)
				.setLightmapState(LIGHTMAP_ENABLED)
				.setOverlayState(OVERLAY_DISABLED)
				.createCompositeState(true);
		return RenderType.create("entity_solid", DefaultVertexFormats.NEW_ENTITY, 7, 256, true, false, renderState);
	}
	
	public static IRenderTypeBuffer disableLighting(IRenderTypeBuffer in){
		return type -> {
			@SuppressWarnings("deprecation")
			RenderType rt = new RenderType(
					ImmersiveNuclearEngineering.MODID + ":" + type + "_no_lighting",
					type.format(),
					type.mode(),
					type.bufferSize(),
					type.affectsCrumbling(),
					false,
					() -> {
						type.setupRenderState();
						
						RenderSystem.disableLighting();
					}, () -> {
						type.clearRenderState();
					}){};
			return in.getBuffer(rt);
		};
	}
}
