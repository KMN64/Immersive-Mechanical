package net.kmn64.im;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;

import net.kmn64.im.main.IMContent;
import net.kmn64.im.main.IMRegister;
import net.kmn64.im.main.item.IMMaterials;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(IMMain.MODID)
public class IMMain
{
	// Directly reference a mod ID
	public static final String MODID = "im";
    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger(MODID);
	
	//public static CommonProxy proxy = DistExecutor.safeRunForDist(() -> ClientProxy::new, () -> CommonProxy::new);

	// public static final ItemGroup CREATIVE_TAB = new ItemGroup(MODID)
	// 		{

	// 			@Override
	// 			public ItemStack makeIcon() {
	// 				return new ItemStack(null);
	// 			}
		
	// 		};

    public IMMain() {
    	//ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, INEServerConfig.all,ImmersiveNuclearEngineering.MODID+"-server.toml");
    	
    	IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
    	eventBus.addListener(this::setup);
    	eventBus.addListener(this::loadComplete);

		IMContent.populate();
		
		MinecraftForge.EVENT_BUS.addListener(this::serverStarting);
		MinecraftForge.EVENT_BUS.addListener(this::serverAboutToStart);
		MinecraftForge.EVENT_BUS.addListener(this::serverStarted);
		MinecraftForge.EVENT_BUS.addListener(this::registerCommand);
		MinecraftForge.EVENT_BUS.addListener(this::addReloadListeners);
    }

	private void setup(final FMLCommonSetupEvent event)
    {

    }
    
    public void serverAboutToStart(FMLServerAboutToStartEvent event){

	}
	
	public void serverStarting(FMLServerStartingEvent event){

	}
	
	public void registerCommand(RegisterCommandsEvent event){

	}
	
	public void addReloadListeners(AddReloadListenerEvent event){
		
	}
	
	public void serverStarted(FMLServerStartedEvent event){
		
	}
    
    private void loadComplete(final FMLLoadCompleteEvent event)
    {
    	
    }
}
