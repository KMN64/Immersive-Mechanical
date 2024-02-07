package net.kmn64.ine.config;

import java.lang.reflect.Field;

import com.electronwill.nightconfig.core.Config;
import com.google.common.base.Preconditions;

import net.kmn64.ine.ImmersiveNuclearEngineering;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
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
		
		Machines(ForgeConfigSpec.Builder builder)
		{
			builder.push("machines");
			builder.pop();
		}
		
		public static class Multiblock {
			Multiblock(ForgeConfigSpec.Builder builder)
			{
				builder.push("multiblock");
				builder.pop();
			}
		}
	}
}