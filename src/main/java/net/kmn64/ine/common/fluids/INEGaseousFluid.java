package net.kmn64.ine.common.fluids;

import java.util.function.Consumer;

import javax.annotation.Nullable;

import blusunrize.immersiveengineering.common.IEContent;
import blusunrize.immersiveengineering.common.util.fluids.IEFluid;
import net.kmn64.ine.ImmersiveNuclearEngineering;
import net.kmn64.ine.common.INEContent;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidAttributes;

public class INEGaseousFluid extends IEFluid {
	public INEGaseousFluid(String name,int color) {
		this(name, new ResourceLocation(ImmersiveNuclearEngineering.MODID,"fluids/fluid"), 
				new ResourceLocation(ImmersiveNuclearEngineering.MODID,"fluids/fluid_flow"), INEFluid.createBuilder(color, 300, -1000, 1000, true));
		// TODO Auto-generated constructor stub
	}
	
	public INEGaseousFluid(String fluidName, ResourceLocation stillTex, ResourceLocation flowingTex, @Nullable Consumer<FluidAttributes.Builder> buildAttributes) 
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
