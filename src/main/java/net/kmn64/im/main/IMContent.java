package net.kmn64.im.main;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import blusunrize.immersiveengineering.api.multiblocks.MultiblockHandler;
import blusunrize.immersiveengineering.api.multiblocks.TemplateMultiblock;
import net.kmn64.im.IMMain;
import net.kmn64.im.main.fluid.IMBaseFluid;
import net.kmn64.im.main.item.IMMaterials;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.potion.Effect;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

/**
 * This class handles the registration of various game content such as blocks, items, fluids, and entities
 * for the Immersive Mechanical mod. It uses the Forge event bus to listen for registry events and 
 * registers the corresponding content.
 * 
 * <p>Methods:</p>
 * <ul>
 *   <li>{@link #populate()}: Populates the mod with initial materials.</li>
 *   <li>{@link #preInit()}: Pre-initialization logic (currently empty).</li>
 *   <li>{@link #init()}: Initialization logic, including registering multiblocks.</li>
 *   <li>{@link #registerBlock(RegistryEvent.Register<Block>)}: Registers all blocks.</li>
 *   <li>{@link #registerItem(RegistryEvent.Register<Item>)}: Registers all items.</li>
 *   <li>{@link #registerFluid(RegistryEvent.Register<Fluid>)}: Registers all fluids.</li>
 *   <li>{@link #registerEntityType(RegistryEvent.Register<EntityType<?>>)}: Registers all entity types (currently empty).</li>
 *   <li>{@link #registerEffect(RegistryEvent.Register<Effect>)}: Registers all effects (currently empty).</li>
 * </ul>
 * 
 * <p>Fields:</p>
 * <ul>
 *   <li>{@link #LOGGER}: Logger instance for logging registration errors.</li>
 * </ul>
 * 
 * <p>Annotations:</p>
 * <ul>
 *   <li>{@link Mod.EventBusSubscriber}: Indicates that this class subscribes to the Forge event bus for the MODID.</li>
 *   <li>{@link SubscribeEvent}: Indicates that the annotated methods are event handlers.</li>
 * </ul>
 */
@Mod.EventBusSubscriber(modid = IMMain.MODID, bus = Bus.MOD)
public class IMContent {
	// Logger for the IMContent class
	// This logger is used to log errors that occur during the registration of content.
	// The logger is initialized with the logger name "IM/Content".
	// The logger name is formatted using the mod ID "IMMain.MODID".
	// The logger is used to log errors that occur during the registration of content.
	
    public static final Logger LOGGER = LogManager.getLogger(String.format("%s/Content",IMMain.MODID));

	/**
	 * Populates the content by registering the necessary materials.
	 * This method calls the register method of the IMMaterials class
	 * to ensure all materials are properly registered.
	 */
    public static void populate(){
		// Register materials
		IMMaterials.register();
    }


	/**
	 * This method is called during the pre-initialization phase of the mod lifecycle.
	 * It is used to set up and register content before the mod is fully initialized.
	 * Typically, this includes registering blocks, items, and other game elements.
	 */
    public static void preInit(){
	}
	

	/**
	 * Initializes the content by registering all defined TemplateMultiblocks
	 * with the MultiblockHandler. This method is called to set up the necessary
	 * multiblock structures for the application.
	 */
	public static void init(){
		final TemplateMultiblock[] multiblocks = { };
		for (TemplateMultiblock multiblock : multiblocks) {
			MultiblockHandler.registerMultiblock(multiblock);
		}
	}
	
	// ==================== Registry Events ====================
	// These methods are called when the corresponding registry event is fired.
	// They register all blocks, items, fluids, entity types, and effects.
	// =========================================================


	/**
	 * Registers blocks during the RegistryEvent.Register<Block> event.
	 * 
	 * @param event The registry event for registering blocks.
	 * 
	 * This method iterates through all blocks registered in IMRegister and attempts to register them.
	 * If an error occurs during the registration of a block, it logs an error message and rethrows the exception.
	 */
	@SubscribeEvent
	public static void registerBlock(RegistryEvent.Register<Block> event){
		for(Block block:IMRegister.getRegistedBlock()){
			try{
				event.getRegistry().register(block);
			}catch(Throwable e){
				LOGGER.error("Failed to register a block. ({},{})", block, block.getRegistryName());
				throw e;
			}
		}
	}
	

	/**
	 * Handles the registration of items during the RegistryEvent.Register<Item> event.
	 * This method iterates through all registered items from IMRegister and attempts to register them.
	 * If an item fails to register, an error is logged and the exception is rethrown.
	 *
	 * @param event The registry event for registering items.
	 */
	@SubscribeEvent
	public static void registerItem(RegistryEvent.Register<Item> event){
		for(Item item:IMRegister.getRegistedItem()){
			try{
				event.getRegistry().register(item);
			}catch(Throwable e){
				LOGGER.error("Failed to register an item. ({}, {})", item, item.getRegistryName());
				throw e;
			}
		}
	}
	
	/**
	 * Registers fluids during the RegistryEvent.Register<Fluid> event.
	 * 
	 * @param event The registry event for registering fluids.
	 */
	@SubscribeEvent
	public static void registerFluid(RegistryEvent.Register<Fluid> event){
		for(Fluid fluid:IMRegister.getRegistedFluid()){
			try{
				event.getRegistry().register(fluid);
			}catch(Throwable e){
				LOGGER.error("Failed to register a fluid. ({}, {})", fluid, fluid.getRegistryName());
				throw e;
			}
		}
	}
	
	@SubscribeEvent
	public static void registerEntityType(RegistryEvent.Register<EntityType<?>> event){

	}
	
	@SubscribeEvent
	public static void registerEffect(RegistryEvent.Register<Effect> event){
	}
}
