package net.kmn64.ine.common.multiblocks.blocks;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.stream.Stream;

import com.google.common.base.Supplier;

import blusunrize.immersiveengineering.common.IEContent;
import blusunrize.immersiveengineering.common.blocks.BlockItemIE;
import blusunrize.immersiveengineering.common.blocks.generic.MultiblockPartTileEntity;
import blusunrize.immersiveengineering.common.blocks.metal.MetalMultiblockBlock;
import net.kmn64.ine.ImmersiveNuclearEngineering;
import net.kmn64.ine.common.INEContent;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraft.item.BlockItem;

public class INEMetalMultiblock<T extends MultiblockPartTileEntity<T>> extends MetalMultiblockBlock<T>{
	public INEMetalMultiblock(String name, Supplier<TileEntityType<T>> te){
		super(name, te);
		
		// Necessary hacks
		if(!FMLLoader.isProduction()){
			IEContent.registeredIEBlocks.remove(this);
			ArrayList<Item> listitem = new ArrayList<Item>();
			listitem.addAll(IEContent.registeredIEItems);
			Iterator<Item> it = listitem.iterator();
			while(it.hasNext()){
				Item item = it.next();
				if(item instanceof BlockItemIE && ((BlockItemIE) item).getBlock() == this){
					it.remove();
					IEContent.registeredIEItems.remove(item);
					break;
				}
			}
		}
		
		INEContent.registeredINEBlocks.add(this);
		
		BlockItem bItem = new BlockItemIE(this, new Item.Properties().tab(ImmersiveNuclearEngineering.CREATIVE_TAB));
		INEContent.registeredINEItems.add(bItem.setRegistryName(getRegistryName()));
	}
	
	@Override
	public ResourceLocation createRegistryName(){
		return new ResourceLocation(ImmersiveNuclearEngineering.MODID,this.name);
	}
}
