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

/**
 * Proxy interface for handling various setup and lifecycle events in a Minecraft mod.
 * 
 * <p>This interface provides default methods for handling different stages of the mod lifecycle,
 * including setup, initialization, and server events. Implementations can override these methods
 * to provide custom behavior.</p>
 * 
 * <p>Methods:</p>
 * <ul>
 *   <li>{@link #setup(FMLCommonSetupEvent)} - Fired at {@link FMLCommonSetupEvent}.</li>
 *   <li>{@link #finishSetup(FMLLoadCompleteEvent)} - Default method for handling load complete event.</li>
 *   <li>{@link #clientSetup(FMLClientSetupEvent)} - Default method for handling client setup event.</li>
 *   <li>{@link #registerContainersAndScreens()} - Default method for registering containers and screens.</li>
 *   <li>{@link #preInit()} - Default method for pre-initialization.</li>
 *   <li>{@link #preInitEnd()} - Default method for end of pre-initialization.</li>
 *   <li>{@link #init()} - Default method for initialization.</li>
 *   <li>{@link #postInit()} - Default method for post-initialization.</li>
 *   <li>{@link #completed(FMLLoadCompleteEvent)} - Fired at {@link FMLLoadCompleteEvent}.</li>
 *   <li>{@link #serverAboutToStart(FMLServerAboutToStartEvent)} - Default method for handling server about to start event.</li>
 *   <li>{@link #serverStarting(FMLServerStartingEvent)} - Default method for handling server starting event.</li>
 *   <li>{@link #serverStarted(FMLServerStartedEvent)} - Default method for handling server started event.</li>
 *   <li>{@link #serverStopped(FMLServerStoppedEvent)} - Default method for handling server stopped event.</li>
 *   <li>{@link #enqueueModComs(InterModEnqueueEvent)} - Default method for enqueuing mod communications.</li>
 *   <li>{@link #processModComs(InterModProcessEvent)} - Default method for processing mod communications.</li>
 *   <li>{@link #renderTile(TileEntity, IVertexBuilder, MatrixStack, IRenderTypeBuffer)} - Default method for rendering a tile entity.</li>
 *   <li>{@link #handleEntitySound(SoundEvent, Entity, boolean, float, float)} - Default method for handling entity sound.</li>
 *   <li>{@link #handleTileSound(SoundEvent, TileEntity, boolean, float, float)} - Default method for handling tile entity sound.</li>
 * </ul>
 */
public interface Proxy {	
	
	/**
	 * Sets up the necessary configurations and initializations during the common setup phase.
	 *
	 * @param event The event containing information about the common setup phase.
	 */
	void setup(FMLCommonSetupEvent event);

	/**
	 * This method is called to finish the setup process when the FML load is complete.
	 * It is a default method that can be overridden by implementing classes to provide
	 * specific setup completion logic.
	 *
	 * @param event the FMLLoadCompleteEvent that indicates the load process is complete
	 */
    default void finishSetup(FMLLoadCompleteEvent event){
    }

	/**
	 * This method is a default implementation for client-side setup.
	 * It is called during the FMLClientSetupEvent phase.
	 * 
	 * @param event the FMLClientSetupEvent instance containing event data
	 */
    default void clientSetup(FMLClientSetupEvent event){
    }
	
	/**
	 * Registers the containers and screens for the application.
	 * This method is intended to be overridden by implementing classes
	 * to provide specific container and screen registration logic.
	 */
	default void registerContainersAndScreens(){}
	
	/**
	 * This method is called during the pre-initialization phase of the application.
	 * Override this method to add custom behavior during the pre-initialization phase.
	 */
	default void preInit(){
	}
	
	/**
	 * This method is called at the end of the pre-initialization phase.
	 * It is intended to be overridden by subclasses to perform any necessary
	 * actions that should occur after the pre-initialization process is complete.
	 */
	default void preInitEnd(){
	}
	
	/**
	 * Initializes the proxy. This method is intended to be overridden by subclasses
	 * to provide specific initialization logic. By default, this method does nothing.
	 */
	default void init(){
	}
	
	/**
	 * This method is called during the post-initialization phase.
	 * Override this method to add custom behavior during the post-initialization phase.
	 */
	default void postInit(){
	}
	
	/**
	 * This method is called when the FML load is complete.
	 * 
	 * @param event the event that indicates the completion of the FML load
	 */
	default void completed(FMLLoadCompleteEvent event){
	}
	
	/**
	 * This method is called when the server is about to start.
	 * It is a default implementation and can be overridden by subclasses.
	 *
	 * @param event the event that is triggered when the server is about to start
	 */
	default void serverAboutToStart(FMLServerAboutToStartEvent event){
	}
	
	/**
	 * This method is called during the server starting phase.
	 * It is triggered by the FMLServerStartingEvent.
	 * Override this method to add custom behavior during the server startup.
	 *
	 * @param event the FMLServerStartingEvent that contains information about the server starting event
	 */
	default void serverStarting(FMLServerStartingEvent event){
	}
	
	/**
	 * This method is called when the server has started.
	 * 
	 * @param event the event that indicates the server has started
	 */
	default void serverStarted(FMLServerStartedEvent event){
	}

	/**
	 * This method is called when the server has stopped.
	 * It is a default implementation that does nothing.
	 *
	 * @param event the event that indicates the server has stopped
	 */
    default void serverStopped(FMLServerStoppedEvent event){
    }

	/**
	 * This method is called to enqueue inter-mod communication events.
	 * It is a default method that can be overridden by implementing classes.
	 *
	 * @param event the inter-mod enqueue event
	 */
    default void enqueueModComs(InterModEnqueueEvent event){
    }

	/**
	 * Processes inter-mod communication events.
	 *
	 * @param event the inter-mod process event to be handled
	 */
    default void processModComs(InterModProcessEvent event){
    }
	
	/**
	 * Renders a tile entity.
	 *
	 * @param te the tile entity to render
	 * @param iVertexBuilder the vertex builder used for rendering
	 * @param transform the matrix stack for transformations
	 * @param buffer the render type buffer
	 */
	default void renderTile(TileEntity te, IVertexBuilder iVertexBuilder, MatrixStack transform, IRenderTypeBuffer buffer){
	}
	
	/**
	 * Handles the sound event for a given entity.
	 *
	 * @param soundEvent the sound event to be handled
	 * @param entity the entity associated with the sound event
	 * @param active a boolean indicating whether the sound is active
	 * @param volume the volume of the sound
	 * @param pitch the pitch of the sound
	 */
	default void handleTileSound(SoundEvent soundEvent, TileEntity te, boolean active, float volume, float pitch){
	}
}
