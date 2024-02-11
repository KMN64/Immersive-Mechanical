package net.kmn64.ine.config;

import java.lang.reflect.Field;

import com.electronwill.nightconfig.core.Config;
import com.google.common.base.Preconditions;

import net.kmn64.ine.ImmersiveNuclearEngineering;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.config.ModConfig.ModConfigEvent;

@EventBusSubscriber(modid = ImmersiveNuclearEngineering.MODID, bus = Bus.MOD)
public class INEServerConfig
{
	public static final ForgeConfigSpec all;
	public static Machines machines;
	//public static MechanicalEnergy mechanicalenergy;
	//public static Barrels barrels;
	
	static{
		ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
		
		machines = new Machines(builder);
		machines.multiblock = new Machines.Multiblock(builder);
		machines.distiller = new Machines.Distiller(builder);
		machines.steeltank = new Machines.SteelTank(builder);
		
		all = builder.build();
	}
	
	@SubscribeEvent
	public static void onConfigReload(ModConfigEvent ev){
		
	}
	
	private static Config rawConfig;
	public static Config getRawConfig(){
		if(rawConfig == null){
			try{
				Field childConfig = ForgeConfigSpec.class.getDeclaredField("childConfig");
				childConfig.setAccessible(true);
				rawConfig = (Config) childConfig.get(all);
				Preconditions.checkNotNull(rawConfig);
			}catch(Exception x){
				throw new RuntimeException(x);
			}
		}
		return rawConfig;
	}
	
	public static class Machines {
		public static Multiblock multiblock;
		public static Distiller distiller;
		public static SteelTank steeltank;
		
		Machines(ForgeConfigSpec.Builder builder)
		{
			builder.push("machines");
			builder.pop();
		}
		
		public static class Multiblock {
			public final BooleanValue enable_distiller;
			Multiblock(ForgeConfigSpec.Builder builder)
			{
				builder.push("multiblock");
				enable_distiller = builder.comment("**WARNING** disable this before you load a new world or break the multiblocks before you do this!!! Can the Distiller Multiblock structure be built ? [Default=true]").define("enable_distiller", true);
				builder.pop();
			}
		}
		
		public static class Distiller
		{
			public final ConfigValue<Integer> distiller_input_tankSize;
			public final ConfigValue<Integer> distiller_output_tankSize;
			Distiller(ForgeConfigSpec.Builder builder)
			{
				builder.push("distiller");
				distiller_input_tankSize = builder.comment("The capacity of the input tank for the Distiller [Default=24000]").define("distiller_input_tankSize", 24000);
				distiller_output_tankSize = builder.comment("The capacity of the output tank for the Distiller [Default=24000]").define("distiller_output_tankSize", 24000);
				builder.pop();
			}
		}
		
		public static class SteelTank {
			public final ConfigValue<Integer> steelTank_tankSize;
			public final ConfigValue<Integer> steelTank_transferSpeed;
			SteelTank(ForgeConfigSpec.Builder builder)
			{
				builder.push("steeltank");
				steelTank_tankSize = builder.comment("Steel Tank Size in mB [Default=2048000]").define("steelTank_tankSize", 2048000);
				steelTank_transferSpeed = builder.comment("How fast can the Steel Tank push fluids out, in mB, when powered by Redstone [Default=1000]").define("steelTank_transferSpeed", 1000);
				builder.pop();
			}
		}
	}
}