package net.kmn64.ine.api.crafting.xml;

import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.fluids.FluidStack;

public class RecipesData {
	private static final RecipeData TEST_INPUTDATA = new RecipeData(new ItemStack[] {new ItemStack(Items.STICK)}, new FluidStack[] {new FluidStack(Fluids.WATER,1000)}, null, null);
	private static final RecipeData TEST_OUTPUTDATA = new RecipeData(new ItemStack[] {new ItemStack(Items.SUGAR)}, new FluidStack[] {new FluidStack(Fluids.LAVA,1000)}, null, null);
	public static final WriterRecipeData TEST = new WriterRecipeData("test", TEST_INPUTDATA, TEST_OUTPUTDATA, 0, 0);
}
