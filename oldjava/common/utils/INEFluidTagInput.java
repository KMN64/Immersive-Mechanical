package net.kmn64.ine.common.utils;

import blusunrize.immersiveengineering.api.crafting.FluidTagInput;
import net.minecraft.fluid.Fluid;
import net.minecraft.tags.ITag;

public class INEFluidTagInput extends FluidTagInput {

	private final ITag.INamedTag<Fluid> namedtag;
	public INEFluidTagInput(ITag.INamedTag<Fluid> tag, int amount) {
		super(tag, amount);
		this.namedtag = tag;
		// TODO Auto-generated constructor stub
	}
	
	public ITag<Fluid> getNamedFluidTag()
	{
		return namedtag;
	}

}
