package net.kmn64.ine.common.utils;

import net.minecraftforge.fluids.FluidStack;

public class FluidHelper {
	/** Convenience Method */
	public static FluidStack copyFluid(FluidStack fluid, int amount){
		FluidStack fs = new FluidStack(fluid.getFluid(), amount);
		return fs;
	}
}
