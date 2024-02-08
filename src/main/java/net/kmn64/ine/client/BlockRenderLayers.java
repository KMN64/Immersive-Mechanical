package net.kmn64.ine.client;

import java.util.ArrayList;

import net.kmn64.ine.ImmersiveNuclearEngineering;
import net.kmn64.ine.common.INEContent;
import net.kmn64.ine.common.fluids.INEFluid;
import net.kmn64.ine.common.fluids.INEGaseousFluid;
import net.kmn64.ine.common.fluids.INEMoltenFluid;
import net.kmn64.ine.common.fluids.INESolidFluid;
import net.kmn64.ine.common.items.INEItemMaterialBase;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.fluid.Fluid;
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
		RenderTypeLookup.setRenderLayer(INEContent.Multiblocks.steeltank, RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(INEContent.Multiblocks.oiltank, RenderType.cutoutMipped());
		
		final ArrayList<Fluid> listfluid = new ArrayList<>();
		listfluid.addAll(INEFluid.INE_FLUIDS);
		listfluid.addAll(INEGaseousFluid.INEGASEOUS_FLUIDS);
		listfluid.addAll(INEMoltenFluid.INEMOLTEN_FLUIDS);
		listfluid.addAll(INESolidFluid.INESOLID_FLUIDS);
		for(Fluid f:listfluid){
			setRenderLayer(f, RenderType.translucent());
		}
		
		for(Fluid f:INEContent.registeredINEFluids){
			setRenderLayer(f, RenderType.translucent());
		}
	}
	
	private static void setRenderLayer(Fluid entry, RenderType types){
		RenderTypeLookup.setRenderLayer(entry.getFluid(), types);
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
