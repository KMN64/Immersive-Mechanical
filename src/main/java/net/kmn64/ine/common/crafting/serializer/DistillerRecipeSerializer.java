package net.kmn64.ine.common.crafting.serializer;

import com.google.gson.JsonObject;

import blusunrize.immersiveengineering.api.crafting.FluidTagInput;
import blusunrize.immersiveengineering.api.crafting.IERecipeSerializer;
import net.kmn64.ine.api.crafting.DistillerRecipe;
import net.kmn64.ine.common.INEContent.Multiblocks;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

public class DistillerRecipeSerializer extends IERecipeSerializer<DistillerRecipe> {

	@Override
	public DistillerRecipe fromNetwork(ResourceLocation recipeId, PacketBuffer buffer) {
		// TODO Auto-generated method stub
		ItemStack outputItem = buffer.readItem();
		
		FluidTagInput inputFluid = FluidTagInput.read(buffer);
		FluidStack outputFluid = buffer.readFluidStack();
		
		int totalProcessTime = buffer.readInt();
		int totalProcessEnergy = buffer.readInt();
		
		float chance = buffer.readFloat();
		
		return new DistillerRecipe(recipeId,inputFluid,outputFluid,outputItem,totalProcessTime,totalProcessEnergy,chance);
	}

	@Override
	public void toNetwork(PacketBuffer buffer, DistillerRecipe recipe) {
		// TODO Auto-generated method stub
		buffer.writeItem(recipe.outputItem);
		
		recipe.inputFluid.write(buffer);
		buffer.writeFluidStack(recipe.outputFluid);
		
		buffer.writeInt(recipe.getTotalProcessTime());
		buffer.writeInt(recipe.getTotalProcessEnergy());
		
		buffer.writeFloat(recipe.chance);
	}

	@Override
	public ItemStack getIcon() {
		// TODO Auto-generated method stub
		return new ItemStack(Multiblocks.distiller);
	}

	@Override
	public DistillerRecipe readFromJson(ResourceLocation paramResourceLocation, JsonObject paramJsonObject) {
		// TODO Auto-generated method stub
		return null;
	}

}
