package net.kmn64.im.main.proxy;

import org.lwjgl.system.CallbackI.S;

import blusunrize.immersiveengineering.common.gui.GuiHandler;
import net.minecraft.client.gui.IHasContainer;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.gui.ScreenManager.IScreenFactory;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;

public class ClientProxy extends CommonProxy {
    @Override
	public void setup(FMLCommonSetupEvent event){
    }

    @Override
	public void preInit(){
	}
	
	@Override
	public void preInitEnd(){
	}
	
	@Override
	public void init(){
    }

    @SuppressWarnings("unchecked")
	public <C extends Container, S extends Screen & IHasContainer<C>> void registerScreen(ResourceLocation name, IScreenFactory<C, S> factory){
		ContainerType<C> type = (ContainerType<C>) GuiHandler.getContainerType(name);
		ScreenManager.register(type, factory);
	}

    @Override
	public void registerContainersAndScreens(){
		super.registerContainersAndScreens();
    }
	
	@Override
	public void completed(FMLLoadCompleteEvent event){
    }
}
