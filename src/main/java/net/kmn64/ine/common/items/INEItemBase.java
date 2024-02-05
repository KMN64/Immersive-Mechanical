package net.kmn64.ine.common.items;

import blusunrize.immersiveengineering.common.items.IEItemInterfaces.IColouredItem;
import net.kmn64.ine.ImmersiveNuclearEngineering;
import net.kmn64.ine.common.INEContent;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class INEItemBase extends Item implements IColouredItem{

	/** For basic items */
	public INEItemBase(String name){
		this(name, new Item.Properties());
	}
	
	/** For items that require special attention */
	public INEItemBase(String name, Item.Properties properties){
		super(properties.tab(ImmersiveNuclearEngineering.CREATIVE_TAB));
		setRegistryName(new ResourceLocation(ImmersiveNuclearEngineering.MODID,name));
		
		INEContent.registeredINEItems.add(this);
	}

}
