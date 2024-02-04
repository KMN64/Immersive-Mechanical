package net.kmn64.ine.common.fluids;

import java.util.function.Consumer;

import javax.annotation.Nullable;

import blusunrize.immersiveengineering.common.IEContent;
import blusunrize.immersiveengineering.common.util.fluids.IEFluid;
import blusunrize.immersiveengineering.common.util.fluids.IEFluidBlock;
import net.kmn64.ine.ImmersiveNuclearEngineering;
import net.kmn64.ine.common.INEContent;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidAttributes;

public class INEMoltenFluid extends IEFluid {

	public INEMoltenFluid(String name,int color,int temperature,int density,int viscosity) {
		this(name, new ResourceLocation(ImmersiveNuclearEngineering.MODID,"fluids/molten_still"), 
				new ResourceLocation(ImmersiveNuclearEngineering.MODID,"fluids/molten_flow"), INEMoltenFluid.createBuilder(color, temperature, density, viscosity));
		// TODO Auto-generated constructor stub
	}
	
	public INEMoltenFluid(String fluidName, ResourceLocation stillTex, ResourceLocation flowingTex, @Nullable Consumer<FluidAttributes.Builder> buildAttributes) 
	{ 
		super(fluidName, stillTex, flowingTex, buildAttributes, true); 
		IEContent.registeredIEFluids.remove(this);
		IEContent.registeredIEBlocks.remove(this.block);
		IEContent.registeredIEItems.remove(this.bucket);
		
		INEContent.registeredINEFluids.add(this);
		INEContent.registeredINEBlocks.add(this.block);
		INEContent.registeredINEItems.add(this.bucket);
	}
	
	public static Consumer<FluidAttributes.Builder> createBuilder(int color,int temp,int density, int viscosity){
		return builder -> builder.color(color).temperature(temp).viscosity(viscosity).density(density).luminosity(15);
	}
}
