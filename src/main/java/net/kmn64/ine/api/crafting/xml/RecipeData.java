package net.kmn64.ine.api.crafting.xml;

import java.util.Map;

import net.kmn64.ine.common.utils.INEFluidTagInput;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.ITag;
import net.minecraftforge.fluids.FluidStack;

public class RecipeData {
	public RecipeData(ItemStack[] items, FluidStack[] fluids,
			Map<ITag.INamedTag<Item>, Integer> tagitems, INEFluidTagInput[] tagfluids) {
		this.items = items;
		this.fluids = fluids;
		this.tagitems = tagitems;
		this.tagfluids = tagfluids;
	}
	
	public ItemStack[] items = null;
	public FluidStack[] fluids = null;
	
	public Map<ITag.INamedTag<Item>,Integer> tagitems = null;
	public INEFluidTagInput[] tagfluids = null;
}
