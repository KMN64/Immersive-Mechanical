package net.kmn64.im.main;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

@Mod.EventBusSubscriber(modid = IMMain.MODID, bus = Bus.MOD)
public class IMContent {
    public static final Logger LOGGER = LogManager.getLogger(IMMain.MODID + "/Content");

	/** Block/Item/Fluid population **/
    public static void populate(){
		IMMaterials.register();
		
    }

	/** Pre-Initialization **/
    public static void preInit(){
	}
	
	/** Initialization **/
	public static void init(){
		// For example
		final TemplateMultiblock[] multiblocks = { /* AMultiblock.INSTANCE */};

	}
	
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
