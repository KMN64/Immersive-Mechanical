package net.kmn64.ine.common.fluids;

import java.util.function.Consumer;

import javax.annotation.Nullable;

import blusunrize.immersiveengineering.common.IEContent;
import blusunrize.immersiveengineering.common.util.fluids.IEFluid;
import net.kmn64.ine.ImmersiveNuclearEngineering;
import net.kmn64.ine.common.INEContent;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidAttributes;

public class INESolidFluid extends IEFluid {
	public INESolidFluid(String name,int color,int temperature,int density,int viscosity) {
		this(name, new ResourceLocation(ImmersiveNuclearEngineering.MODID,"fluids/solid_fluid_still"), 
				new ResourceLocation(ImmersiveNuclearEngineering.MODID,"fluids/solid_fluid_flow"), INEFluid.createBuilder(color, temperature, density, viscosity, false));
		// TODO Auto-generated constructor stub
	}
	
	public INESolidFluid(String fluidName, ResourceLocation stillTex, ResourceLocation flowingTex, @Nullable Consumer<FluidAttributes.Builder> buildAttributes) 
	{ 
		super(fluidName, stillTex, flowingTex, buildAttributes, true); 
		IEContent.registeredIEFluids.remove(this);
		IEContent.registeredIEBlocks.remove(this.block);
		IEContent.registeredIEItems.remove(this.bucket);
		
		INEContent.registeredINEFluids.add(this);
		INEContent.registeredINEBlocks.add(this.block);
		INEContent.registeredINEItems.add(this.bucket);
	}

}
