package net.kmn64.im.main.proxy;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.event.sound.SoundEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.event.server.FMLServerStoppedEvent;

// TODO: Add javadoc
public interface Proxy {	
	/** Fired at {@link FMLCommonSetupEvent} */
	void setup(FMLCommonSetupEvent event);
    default void finishSetup(FMLLoadCompleteEvent event){
    }

    default void clientSetup(FMLClientSetupEvent event){
    }
	
	default void registerContainersAndScreens(){}
	
	default void preInit(){
	}
	
	default void preInitEnd(){
	}
	
	default void init(){
	}
	
	default void postInit(){
	}
	
	/** Fired at {@link FMLLoadCompleteEvent} */
	default void completed(FMLLoadCompleteEvent event){
	}
	
	default void serverAboutToStart(FMLServerAboutToStartEvent event){
	}
	
	default void serverStarting(FMLServerStartingEvent event){
	}
	
	default void serverStarted(FMLServerStartedEvent event){
	}

    default void serverStopped(FMLServerStoppedEvent event){
    }

    default void enqueueModComs(InterModEnqueueEvent event){
    }

    default void processModComs(InterModProcessEvent event){
    }
	
	default void renderTile(TileEntity te, IVertexBuilder iVertexBuilder, MatrixStack transform, IRenderTypeBuffer buffer){
	}
	
	default void handleEntitySound(SoundEvent soundEvent, Entity entity, boolean active, float volume, float pitch){
	}
	
	default void handleTileSound(SoundEvent soundEvent, TileEntity te, boolean active, float volume, float pitch){
	}
}
