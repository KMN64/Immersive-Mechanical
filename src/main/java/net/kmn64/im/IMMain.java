package net.kmn64.im;


import javax.annotation.Nonnull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;

import blusunrize.immersiveengineering.ImmersiveEngineering;
import net.kmn64.im.main.IMContent;
import net.kmn64.im.main.IMRegister;
import net.kmn64.im.main.proxy.ClientProxy;
import net.kmn64.im.main.proxy.CommonProxy;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.event.server.FMLServerStoppedEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(IMMain.MODID)
public class IMMain
{
	// Directly reference a modid value from Immersive Mechanical
	/**
	 * The unique identifier for the mod.
	 * This ID is used to reference the mod in various contexts, such as configuration files and resource locations.
	 */
	public static final String MODID = "im";

	// Directly reference a log4j logger
	/**
	 * Logger instance for the main class of the Immersive Mechanical mod.
	 * This logger is configured with the mod's ID and is used to log messages
	 * related to the main functionality of the mod.
	 */
    public static final Logger LOGGER = LogManager.getLogger(String.format("%s/Main",MODID));
	
	/**
	 * A proxy instance that is initialized based on the distribution environment.
	 * If the environment is client-side, an instance of {@link ClientProxy} is created.
	 * Otherwise, an instance of {@link CommonProxy} is created.
	 */
	public static CommonProxy proxy = DistExecutor.safeRunForDist(() -> ClientProxy::new, () -> CommonProxy::new);

    public IMMain() {
    	//ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, INEServerConfig.all,ImmersiveNuclearEngineering.MODID+"-server.toml");
    	
    	IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
    	eventBus.addListener(this::setup);
    	eventBus.addListener(this::loadComplete);
		eventBus.addListener(this::finishSetup);
		eventBus.addListener(this::clientSetup);
		eventBus.addListener(this::enqueueIMC);
		eventBus.addListener(this::processIMC);

		IMContent.populate();
		
		MinecraftForge.EVENT_BUS.addListener(this::serverStarting);
		MinecraftForge.EVENT_BUS.addListener(this::serverAboutToStart);
		MinecraftForge.EVENT_BUS.addListener(this::serverStarted);
		MinecraftForge.EVENT_BUS.addListener(this::serverStopped);
		MinecraftForge.EVENT_BUS.addListener(this::registerCommand);
		MinecraftForge.EVENT_BUS.addListener(this::addReloadListeners);
    }

	private void finishSetup(final FMLLoadCompleteEvent event){
		proxy.finishSetup(event);
	}

	private void clientSetup(final FMLClientSetupEvent event){
		proxy.clientSetup(event);
	}

	private void enqueueIMC(InterModEnqueueEvent event)
	{
		proxy.enqueueModComs(event);
	}

	private void processIMC(InterModProcessEvent event)
	{
		proxy.processModComs(event);
	}

	private void setup(FMLCommonSetupEvent event)
    {
		proxy.setup(event);
		
		// ---------------------------------------------------------------------------------------------------------------------------------------------
		
		proxy.preInit();

		
		proxy.preInitEnd();

		
		proxy.init();
		
		// ---------------------------------------------------------------------------------------------------------------------------------------------
		
		proxy.postInit();
    }
    
    public void serverAboutToStart(FMLServerAboutToStartEvent event){
		proxy.serverAboutToStart(event);
	}
	
	public void serverStarting(FMLServerStartingEvent event){
		proxy.serverStarting(event);
	}

	public void serverStopped(FMLServerStoppedEvent event){
		proxy.serverStopped(event);
	}
	
	public void registerCommand(RegisterCommandsEvent event){

	}
	
	public void addReloadListeners(AddReloadListenerEvent event){
		
	}
	
	public void serverStarted(FMLServerStartedEvent event){
		proxy.serverStarted(event);
	}
    
    private void loadComplete(FMLLoadCompleteEvent event)
    {
    	proxy.completed(event);
    }
}
