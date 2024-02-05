package net.kmn64.ine.common.items;

import java.util.List;

import javax.annotation.Nullable;

import net.kmn64.ine.ImmersiveNuclearEngineering;
import net.kmn64.ine.common.INEContent;
import net.minecraft.item.Item;

public class INEItemMaterialBase extends Item {

	public INEItemMaterialBase(String name, String... part){
		super(new Item.Properties().tab(ImmersiveNuclearEngineering.CREATIVE_TAB));
		
		List.of(part).forEach((a)->{
			INEItemBase itembase = new INEItemBase(name+"_"+a);
			INEContent.registeredINEItems.add(itembase);
		});
	}

}
