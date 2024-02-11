package net.kmn64.ine.api.crafting.xml;

import blusunrize.immersiveengineering.api.crafting.FluidTagInput;
import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.tags.ITag;

public class RecipeData {
	public static Item item;
	public static Block block;
	public static Fluid fluid;
	
	public static ITag.INamedTag<Item> itemtag;
	public static ITag.INamedTag<Block> blocktag;
	public static FluidTagInput fluidtag;
}
