package net.kmn64.ine.common.crafting;

import blusunrize.immersiveengineering.api.crafting.IERecipeSerializer;
import net.kmn64.ine.ImmersiveNuclearEngineering;
import net.kmn64.ine.api.crafting.DistillerRecipe;
import net.kmn64.ine.common.crafting.serializer.DistillerRecipeSerializer;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class Serializers {
	public static final DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, ImmersiveNuclearEngineering.MODID);
	
	public static final RegistryObject<IERecipeSerializer<DistillerRecipe>> DISTILLER_SERIALIZER = RECIPE_SERIALIZERS.register(
			"distillation", DistillerRecipeSerializer::new
	);
}
