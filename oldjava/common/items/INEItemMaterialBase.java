package net.kmn64.ine.common.items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Nullable;

import net.kmn64.ine.ImmersiveNuclearEngineering;
import net.kmn64.ine.common.INEContent;
import net.minecraft.item.Item;

public class INEItemMaterialBase extends Item {
	public static ArrayList<INEItemBase> arrlist = new ArrayList<>();
	public static HashMap<INEItemBase,Integer> coloritem = new HashMap<>();
	public INEItemMaterialBase(String name, String[] part,int color){
		super(new Item.Properties().tab(ImmersiveNuclearEngineering.CREATIVE_TAB));
		
		List.of(part).forEach((a)->{
			INEItemBase itembase = new INEItemBase(name+"_"+a);
			arrlist.add(itembase);
			coloritem.put(itembase, color);
			INEContent.registeredINEItems.add(itembase);
		});
	}

}
