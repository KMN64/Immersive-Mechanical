package net.kmn64.ine.common.utils;

import java.util.List;

import net.minecraft.util.math.AxisAlignedBB;

public class AABBUtils {
public static final AxisAlignedBB FULL = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 1.0, 1.0);
	
	/** Creates a 16p-Texture aligned {@link AxisAlignedBB} and adds it to the provided List */
	public static void box16(List<AxisAlignedBB> list, double x0, double y0, double z0, double x1, double y1, double z1){
		list.add(box16(x0, y0, z0, x1, y1, z1));
	}
	
	/** Creates a 16p-Texture aligned {@link AxisAlignedBB} */
	public static AxisAlignedBB box16(double x0, double y0, double z0, double x1, double y1, double z1){
		return new AxisAlignedBB(x0 / 16D, y0 / 16D, z0 / 16D, x1 / 16D, y1 / 16D, z1 / 16D);
	}
	
	/** Creates a {@link AxisAlignedBB} and adds it to the provided List */
	public static void box(List<AxisAlignedBB> list, double x0, double y0, double z0, double x1, double y1, double z1){
		list.add(box(x0, y0, z0, x1, y1, z1));
	}
	
	/** Creates a {@link AxisAlignedBB} */
	public static AxisAlignedBB box(double x0, double y0, double z0, double x1, double y1, double z1){
		return new AxisAlignedBB(x0, y0, z0, x1, y1, z1);
	}
}
