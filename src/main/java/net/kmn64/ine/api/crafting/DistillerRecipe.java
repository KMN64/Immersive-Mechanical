package net.kmn64.ine.api.crafting;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Nonnull;

import blusunrize.immersiveengineering.api.crafting.FluidTagInput;
import blusunrize.immersiveengineering.api.crafting.IERecipeSerializer;
import net.kmn64.ine.ImmersiveNuclearEngineering;
import net.kmn64.ine.common.crafting.Serializers;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

public class DistillerRecipe extends INEMultiblockRecipe {

public static final IRecipeType<DistillerRecipe> TYPE = IRecipeType.register(ImmersiveNuclearEngineering.MODID + ":distiller");
	
	public static Map<ResourceLocation, DistillerRecipe> recipes = new HashMap<>();
	
	public static DistillerRecipe findRecipe(@Nonnull FluidStack input){
		Objects.requireNonNull(input);
		
		for(DistillerRecipe recipe:recipes.values()){
			if(recipe.inputFluid != null && recipe.inputFluid.test(input)){
				return recipe;
			}
		}
		return null;
	}
	
	public static boolean hasRecipeWithInput(@Nonnull FluidStack fluid, boolean ignoreAmount){
		Objects.requireNonNull(fluid);
		
		if(!fluid.isEmpty()){
			for(DistillerRecipe recipe:recipes.values()){
				if(recipe.inputFluid != null){
					if((!ignoreAmount && recipe.inputFluid.test(fluid)) || (ignoreAmount && recipe.inputFluid.testIgnoringAmount(fluid))){
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public static float energyModifier = 1;
	public static float timeModifier = 1;
	public final ItemStack outputItem;
	public final float chance;
	
	public final FluidStack outputFluid;
	
	public final FluidTagInput inputFluid;
	
	public DistillerRecipe(ResourceLocation id,FluidTagInput inputFluid,FluidStack outputFluid, ItemStack outputItem, int time, int energy, float chance) {
		super(ItemStack.EMPTY, TYPE, id);
		this.outputFluid = outputFluid;
		this.outputItem = outputItem;
		this.inputFluid = inputFluid;
		this.chance = chance;
		
		this.fluidOutputList = List.of(outputFluid);
		this.fluidInputList = List.of(inputFluid);
		
		timeAndEnergy(time,energy);
		modifyTimeAndEnergy(()->time,()->energy);
	}

	@Override
	public int getMultipleProcessTicks() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public FluidTagInput getInputFluid(){
		return this.inputFluid;
	}

	@Override
	protected IERecipeSerializer<DistillerRecipe> getIESerializer() {
		// TODO Auto-generated method stub
		return Serializers.DISTILLER_SERIALIZER.get();
	}

}
