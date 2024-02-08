package net.kmn64.ine.client;

import net.kmn64.ine.ImmersiveNuclearEngineering;
import net.kmn64.ine.common.items.INEItemMaterialBase;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(modid = ImmersiveNuclearEngineering.MODID, value = Dist.CLIENT, bus = Bus.MOD)
public class BlockRenderLayers {
	@SubscribeEvent
	public static void clientSetup(FMLClientSetupEvent event){
		
	}
	
	@SubscribeEvent
    public static void onHandleColorItem(ColorHandlerEvent.Item event){
		INEItemMaterialBase.arrlist.forEach((a)->{
			event.getItemColors().register((p_getColor_1_,p_getColor_2_)->{return INEItemMaterialBase.coloritem.get(a);},INEItemMaterialBase.arrlist.get(INEItemMaterialBase.arrlist.indexOf(a)));
		});
    }
	
	public static boolean lubeLayer(RenderType t){
		return t == RenderType.translucent();
	}
	
	public static boolean stackLayer(RenderType t){
		return t == RenderType.cutout();
	}
	
	public static boolean solidCutout(RenderType t){
		return t == RenderType.solid() || t == RenderType.cutout();
	}
}
