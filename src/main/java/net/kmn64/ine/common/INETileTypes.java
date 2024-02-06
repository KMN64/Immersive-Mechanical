package net.kmn64.ine.common;

import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableSet;

import net.kmn64.ine.ImmersiveNuclearEngineering;
import net.kmn64.ine.common.multiblocks.tileentities.SteelSheetmetalTankTileEntity;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class INETileTypes {
public static final DeferredRegister<TileEntityType<?>> REGISTER = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, ImmersiveNuclearEngineering.MODID);
	
	// Multiblocks
	public static final RegistryObject<TileEntityType<SteelSheetmetalTankTileEntity>> STEEL_TANK = register("steel_tank", SteelSheetmetalTankTileEntity::new,new Block[] {INEContent.Multiblocks.steeltank});

	private static <T extends TileEntity> RegistryObject<TileEntityType<T>> register(String name, Supplier<T> factory, Block... valid){
		return REGISTER.register(name, () -> new TileEntityType<>(factory, ImmutableSet.copyOf(valid), null));
	}
}
