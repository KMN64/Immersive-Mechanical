package net.kmn64.ine.common;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import blusunrize.immersiveengineering.api.multiblocks.MultiblockHandler;
import net.kmn64.ine.ImmersiveNuclearEngineering;
import net.kmn64.ine.common.fluids.*;
import net.kmn64.ine.common.items.INEItemBase;
import net.kmn64.ine.common.items.INEItemMaterialBase;
import net.kmn64.ine.common.multiblocks.blocks.SteelSheetmetalTankBlock;
import net.kmn64.ine.common.multiblocks.multiblocks.SteelSheetmetalTankMultiblock;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.potion.Effect;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = ImmersiveNuclearEngineering.MODID, bus = Bus.MOD)
public class INEContent {
	public static final Logger LOGGER = LogManager.getLogger(ImmersiveNuclearEngineering.MODID + "/Content");
	
	public static final List<Block> registeredINEBlocks = new ArrayList<>();
	public static final List<Item> registeredINEItems = new ArrayList<>();
	public static final List<Fluid> registeredINEFluids = new ArrayList<>();
	
	public static class Multiblocks{
		public static Block steeltank;
	}
	
	public static class Fluids{
		public static INEGaseousFluid fluorine;
		public static INEGaseousFluid deuterium;
		public static INEGaseousFluid tritium;
		public static INEGaseousFluid helium;
		public static INEGaseousFluid helium3;
		public static INEFluid sulfuricacid;
		public static INEFluid hydrochloricacid;
		public static INEFluid hydrofluoricacid;
	}
	
	public static class Blocks{

	}
	
	public static class Items{
		public static INEItemBase salt;
		public static INEItemMaterialBase depleteduraniumdioxide;
		public static INEItemMaterialBase lightlyenricheduraniumdioxide;
		public static INEItemMaterialBase highlyenricheduraniumdioxide;
	}
	
	/** block/item/fluid population */
	public static void populate(){
		Fluids.fluorine = new INEGaseousFluid("fluorine",0xCEBD89);
		Fluids.deuterium = new INEGaseousFluid("deuterium",0xFF3232);
		Fluids.tritium = new INEGaseousFluid("tritium",0x64FF70);
		Fluids.helium = new INEGaseousFluid("helium",0xE3FFFE);
		Fluids.helium3 = new INEGaseousFluid("helium3",0xE3FFFE);
		Fluids.sulfuricacid=new INEFluid("sulfuricacid",0x82802B, 300, 1000, 1000);
		Fluids.hydrochloricacid=new INEFluid("hydrochloricacid",0xA8F1E9, 300, 1000, 1000);
		Fluids.hydrofluoricacid=new INEFluid("hydrofluoricacid",0x689B9A, 300, 1300, 1000);
		
		// Regular salt (NaCl)
		Items.salt = new INEItemBase("salt");
		Items.depleteduraniumdioxide=new INEItemMaterialBase("depleteduraniumdioxide",new String[] {"ingot", "plate", "nugget", "dust"});
		Items.lightlyenricheduraniumdioxide=new INEItemMaterialBase("lightlyenricheduraniumdioxide",new String[] {"ingot", "plate", "nugget", "dust", "rod", "fuelpellet"});
		Items.highlyenricheduraniumdioxide=new INEItemMaterialBase("highlyenricheduraniumdioxide",new String[] {"ingot", "plate", "nugget", "dust", "rod", "fuelpellet"});
		
		Multiblocks.steeltank = new SteelSheetmetalTankBlock();
		
		//Prepare to register multiblock
		//MultiblockHandler.registerMultiblock(AMultiblock.instance);
		MultiblockHandler.registerMultiblock(SteelSheetmetalTankMultiblock.instance);
	}
	
	public static void preInit(){
	}
	
	public static void init(){
	}
	
	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event){
		for(Block block:registeredINEBlocks){
			try{
				event.getRegistry().register(block);
			}catch(Throwable e){
				LOGGER.error("Failed to register a block. ({})", block);
				throw e;
			}
		}
	}
	
	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event){
		for(Item item:registeredINEItems){
			try{
				event.getRegistry().register(item);
			}catch(Throwable e){
				LOGGER.error("Failed to register an item. ({}, {})", item, item.getRegistryName());
				throw e;
			}
		}
	}
	
	@SubscribeEvent
	public static void registerFluids(RegistryEvent.Register<Fluid> event){
		for(Fluid fluid:registeredINEFluids){
			try{
				event.getRegistry().register(fluid);
			}catch(Throwable e){
				LOGGER.error("Failed to register a fluid. ({}, {})", fluid, fluid.getRegistryName());
				throw e;
			}
		}
	}
	
	@SubscribeEvent
	public static void registerEntityTypes(RegistryEvent.Register<EntityType<?>> event){

	}
	
	@SubscribeEvent
	public static void registerEffects(RegistryEvent.Register<Effect> event){
	}
}
