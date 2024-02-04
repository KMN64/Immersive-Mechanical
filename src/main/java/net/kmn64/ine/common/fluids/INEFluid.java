package net.kmn64.ine.common.fluids;

import java.util.function.Consumer;

import javax.annotation.Nullable;

import blusunrize.immersiveengineering.common.IEContent;
import blusunrize.immersiveengineering.common.util.fluids.IEFluid;
import net.kmn64.ine.ImmersiveNuclearEngineering;
import net.kmn64.ine.common.INEContent;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidAttributes;

public class INEFluid extends IEFluid {
	public INEFluid(String name,int color,int temperature,int density,int viscosity) {
		this(name, new ResourceLocation(ImmersiveNuclearEngineering.MODID,"fluids/fluid"), 
				new ResourceLocation(ImmersiveNuclearEngineering.MODID,"fluids/fluid_flow"), INEFluid.createBuilder(color, temperature, density, viscosity, false));
		// TODO Auto-generated constructor stub
	}
	
	public INEFluid(String fluidName, ResourceLocation stillTex, ResourceLocation flowingTex, @Nullable Consumer<FluidAttributes.Builder> buildAttributes) 
	{ 
		super(fluidName, stillTex, flowingTex, buildAttributes, true); 
		IEContent.registeredIEFluids.remove(this);
		IEContent.registeredIEBlocks.remove(this.block);
		IEContent.registeredIEItems.remove(this.bucket);
		
		INEContent.registeredINEFluids.add(this);
		INEContent.registeredINEBlocks.add(this.block);
		INEContent.registeredINEItems.add(this.bucket);
	}
	
	public static Consumer<FluidAttributes.Builder> createBuilder(int color,int temp,int density, int viscosity,boolean isGaseous){
		return builder -> {if (isGaseous)
			builder.color(color).temperature(temp).density(density).viscosity(viscosity).gaseous();
		else
			builder.color(color).temperature(temp).density(density).viscosity(viscosity);
		};
	}
}
