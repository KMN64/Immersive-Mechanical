package net.kmn64.ine;

import net.kmn64.ine.client.ClientProxy;
import net.kmn64.ine.common.CommonProxy;
import net.kmn64.ine.common.INEContent;
import net.kmn64.ine.common.INETileTypes;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ImmersiveNuclearEngineering.MODID)
public class ImmersiveNuclearEngineering
{
	// Directly reference a mod ID
	public static final String MODID = "ine";
    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger(MODID);
	public static final ItemGroup CREATIVE_TAB = new ItemGroup(MODID)
			{

				@Override
				public ItemStack makeIcon() {
					// TODO Auto-generated method stub
					return new ItemStack(INEContent.Fluids.fluorine.getBucket());
				}
		
			};
			
			
	@SuppressWarnings("deprecation")
	public static final ItemGroup CREATIVE_TAB_MOLTEN = new ItemGroup(MODID+"_molten")
	{

		@Override
		public ItemStack makeIcon() {
			// TODO Auto-generated method stub
			return new ItemStack(INEContent.Fluids.fluorine.getBucket());
		}
				
		@Override
		public boolean hasSearchBar() {
			return true;
		}
		
	}.setBackgroundSuffix("item_search.png");
	
	public static CommonProxy proxy = DistExecutor.safeRunForDist(() -> ClientProxy::new, () -> CommonProxy::new);


    public ImmersiveNuclearEngineering() {
    	IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        // Register the setup method for modloading
    	eventBus.addListener(this::setup);
        // Register the doClientStuff method for modloading
    	eventBus.addListener(this::doClientStuff);
    	eventBus.addListener(this::loadComplete);
		
		MinecraftForge.EVENT_BUS.addListener(this::serverStarting);
		MinecraftForge.EVENT_BUS.addListener(this::serverAboutToStart);
		MinecraftForge.EVENT_BUS.addListener(this::serverStarted);
		MinecraftForge.EVENT_BUS.addListener(this::registerCommand);
		MinecraftForge.EVENT_BUS.addListener(this::addReloadListeners);
		
		INEContent.populate();
		INETileTypes.REGISTER.register(eventBus);
        
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        
        proxy.registerContainersAndScreens();
    }

    private void setup(final FMLCommonSetupEvent event)
    {
    	proxy.setup();
    	proxy.preInit();
    	INEContent.preInit();
    	proxy.preInitEnd();
    	INEContent.init();
    	proxy.init();
    	proxy.postInit();
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        
    }
    
    public void serverAboutToStart(FMLServerAboutToStartEvent event){
    	proxy.serverAboutToStart();

	}
	
	public void serverStarting(FMLServerStartingEvent event){
		proxy.serverStarting();
	}
	
	public void registerCommand(RegisterCommandsEvent event){

	}
	
	public void addReloadListeners(AddReloadListenerEvent event){
		
	}
	
	public void serverStarted(FMLServerStartedEvent event){
		proxy.serverStarted();
	}
    
    private void loadComplete(final FMLLoadCompleteEvent event)
    {
    	proxy.completed();
    }
}
