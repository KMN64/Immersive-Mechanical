package net.kmn64.ine.common.blocks;

import net.kmn64.ine.ImmersiveNuclearEngineering;
import net.kmn64.ine.common.INEContent;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class INEBlockBase extends Block{
	public INEBlockBase(String name, Block.Properties props){
		super(props);
		setRegistryName(new ResourceLocation(ImmersiveNuclearEngineering.MODID,name));
		
		INEContent.registeredINEBlocks.add(this);
		
		BlockItem bItem = createBlockItem();
		if(bItem != null)
			INEContent.registeredINEItems.add(bItem.setRegistryName(getRegistryName()));
	}
	
	protected BlockItem createBlockItem(){
		return new BlockItem(this, new Item.Properties().tab(ImmersiveNuclearEngineering.CREATIVE_TAB));
	}
}
