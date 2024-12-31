package net.kmn64.ine.client.utils;

import net.minecraft.client.GameSettings;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * Central place for Minecraft instance related stuff.
 *
 * @author KMN64
 */
@OnlyIn(Dist.CLIENT)
public class MCUtil{
	
private static final Minecraft MC = Minecraft.getInstance();
	
	public static void bindTexture(ResourceLocation texture){
		getTextureManager().bind(texture);
	}
	
	public static float getPartialTicks(){
		return MC.getFrameTime();
	}
	
	public static Entity getRenderViewEntity(){
		return MC.getCameraEntity();
	}
	
	public static BlockColors getBlockColors(){
		return MC.getBlockColors();
	}
	
	public static Screen getScreen(){
		return MC.screen;
	}
	
	public static ParticleManager getParticleEngine(){
		return MC.particleEngine;
	}
	
	public static TextureManager getTextureManager(){
		return MC.textureManager;
	}
	
	public static BlockRendererDispatcher getBlockRenderer(){
		return MC.getBlockRenderer();
	}
	
	public static GameRenderer getGameRenderer(){
		return MC.gameRenderer;
	}
	
	public static ClientWorld getWorld(){
		return MC.level;
	}
	
	public static FontRenderer getFont(){
		return MC.font;
	}
	
	public static ClientPlayerEntity getPlayer(){
		return MC.player;
	}
	
	public static RayTraceResult getHitResult(){
		return MC.hitResult;
	}
	
	public static GameSettings getOptions(){
		return MC.options;
	}
	
	public static MainWindow getWindow(){
		return MC.getWindow();
	}

	public static ItemRenderer getItemRenderer(){
		return MC.getItemRenderer();
	}
}