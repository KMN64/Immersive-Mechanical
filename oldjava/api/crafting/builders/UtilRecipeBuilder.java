package net.kmn64.ine.api.crafting.builders;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import blusunrize.immersiveengineering.api.ApiUtils;
import blusunrize.immersiveengineering.api.crafting.IERecipeSerializer;
import blusunrize.immersiveengineering.api.crafting.builders.IEFinishedRecipe;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.tags.ITag;
import net.minecraft.util.Tuple;
import net.minecraftforge.fluids.FluidStack;

public class UtilRecipeBuilder extends IEFinishedRecipe<UtilRecipeBuilder> {

	/** Temporary storage for byproducts */
	private List<Tuple<ItemStack, Double>> byproducts = new ArrayList<>();
	public UtilRecipeBuilder(IERecipeSerializer<?> serializer) {
		super(serializer);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Can be called multiple times to add more byproducts to the recipe
	 * 
	 * @param byproduct
	 * @param chance 0 to 100 (clamped)
	 * @return self for chaining
	 */
	public UtilRecipeBuilder addByproduct(ItemStack byproduct, int chance){
		return addByproduct(byproduct, chance / 100D);
	}
	
	/**
	 * Can be called multiple times to add more byproducts to the recipe.
	 * 
	 * @param byproduct
	 * @param chance 0.0 to 1.0 (clamped)
	 * @return self for chaining
	 */
	public UtilRecipeBuilder addByproduct(ItemStack byproduct, double chance){
		this.byproducts.add(new Tuple<ItemStack, Double>(byproduct, Math.max(Math.min(chance, 1.0), 0.0)));
		return this;
	}
	
	public UtilRecipeBuilder setTimeAndEnergy(int time, int energy){
		return setTime(time).setEnergy(energy);
	}
	
	public UtilRecipeBuilder addInput(ITag.INamedTag<Fluid> fluidTag, int amount){
		return addFluidTag("input", fluidTag, amount);
	}
	
	public UtilRecipeBuilder addInput(Fluid fluid, int amount){
		return addInput(new FluidStack(fluid, amount));
	}
	
	public UtilRecipeBuilder addInput(FluidStack fluidStack){
		return addFluid("input", fluidStack);
	}
	
	public UtilRecipeBuilder addFluids(String key, FluidStack... fluidStacks){
		return addWriter(jsonObject -> {
			JsonArray array = new JsonArray();
			for(FluidStack stack:fluidStacks)
				array.add(ApiUtils.jsonSerializeFluidStack(stack));
			jsonObject.add(key, array);
		});
	}
	
	public UtilRecipeBuilder addItems(String key, ItemStack... itemStacks){
		return addWriter(jsonObject -> {
			JsonArray array = new JsonArray();
			for(ItemStack stack:itemStacks){
				array.add(serializeItemStack(stack));
			}
			jsonObject.add(key, array);
		});
	}
	
	public static Tuple<ItemStack, Double> deserializeItemStackWithChance(JsonObject jsonObject){
		if(jsonObject.has("chance") && jsonObject.has("item")){
			double chance = jsonObject.get("chance").getAsDouble();
			jsonObject.remove("chance");
			ItemStack stack = ShapedRecipe.itemFromJson(jsonObject);
			return new Tuple<ItemStack, Double>(stack, chance);
		}
		
		throw new IllegalArgumentException("Unexpected json object.");
	}
}
