package net.kmn64.ine.api.crafting.xml;

import java.util.HashMap;

import net.kmn64.ine.common.utils.INEFluidTagInput;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.ITag;
import net.minecraftforge.fluids.FluidStack;

public class ReaderRecipeData {
	public ReaderRecipeData(RecipeData inputData, RecipeData outputData, int time, int energy) {
		this.inputData = inputData;
		this.outputData = outputData;
		this.time = time;
		this.energy = energy;
	}
	
	public RecipeData inputData;
	public RecipeData outputData;
	
	public int time;
	public int energy;
}
