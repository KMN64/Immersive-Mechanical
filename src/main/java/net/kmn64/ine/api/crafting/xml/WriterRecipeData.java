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
	public WriterRecipeData(String recipename, RecipeData inputData, RecipeData outputData, int time, int energy) {
		this.recipename = recipename;
		this.inputData = inputData;
		this.outputData = outputData;
		this.time = time;
		this.energy = energy;
	}
	
	public String recipename="";
	
	public RecipeData inputData;
	public RecipeData outputData;
	
	public int time;
	public int energy;
}
