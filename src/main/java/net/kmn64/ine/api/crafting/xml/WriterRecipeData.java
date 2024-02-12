package net.kmn64.ine.api.crafting.xml;

import java.util.HashMap;
import java.util.Map;

import net.kmn64.ine.common.utils.INEFluidTagInput;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ITag.INamedTag;
import net.minecraftforge.fluids.FluidStack;

public class WriterRecipeData {
	public WriterRecipeData(String recipename, ItemStack[] items, FluidStack[] fluids,
			Map<ITag.INamedTag<Item>, Integer> tagitems, INEFluidTagInput[] tagfluids, int time, int energy) {
		this.recipename = recipename;
		this.items = items;
		this.fluids = fluids;
		this.tagitems = tagitems;
		this.tagfluids = tagfluids;
		this.time = time;
		this.energy = energy;
	}
	
	public String recipename="";
	
	public ItemStack[] items = null;
	public FluidStack[] fluids = null;
	
	public Map<ITag.INamedTag<Item>,Integer> tagitems = null;
	public INEFluidTagInput[] tagfluids = null;
	
	public int time;
	public int energy;
}
