package net.kmn64.ine.api.crafting;

import blusunrize.immersiveengineering.api.crafting.IERecipeSerializer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;

public class DistillerRecipe extends INEMultiblockRecipe {

	protected DistillerRecipe(ItemStack outputDummy, IRecipeType<?> type, ResourceLocation id) {
		super(outputDummy, type, id);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getMultipleProcessTicks() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected IERecipeSerializer getIESerializer() {
		// TODO Auto-generated method stub
		return null;
	}

}
