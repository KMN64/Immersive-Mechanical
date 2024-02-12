package net.kmn64.ine.api.crafting.xml;

import java.util.HashMap;

import net.kmn64.ine.common.utils.INEFluidTagInput;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.ITag;
import net.minecraftforge.fluids.FluidStack;

public class ReaderRecipeData {
	public ReaderRecipeData(ItemStack[] item, FluidStack[] fluid, HashMap<ITag.INamedTag<Item>, Integer> tagitem,
			INEFluidTagInput[] tagfluid, int time, int energy) {
		this.item = item;
		this.fluid = fluid;
		this.tagitem = tagitem;
		this.tagfluid = tagfluid;
		this.time = time;
		this.energy = energy;
	}
	
	public ItemStack[] item;
	public FluidStack[] fluid;
	
	public HashMap<ITag.INamedTag<Item>,Integer> tagitem;
	public INEFluidTagInput[] tagfluid;
	
	public int time;
	public int energy;
}
