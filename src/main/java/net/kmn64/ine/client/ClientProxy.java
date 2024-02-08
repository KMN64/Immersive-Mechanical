package net.kmn64.ine.client;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import blusunrize.immersiveengineering.api.ManualHelper;
import blusunrize.immersiveengineering.client.manual.ManualElementMultiblock;
import blusunrize.lib.manual.ManualEntry;
import blusunrize.lib.manual.ManualInstance;
import blusunrize.lib.manual.Tree.InnerNode;
import net.kmn64.ine.ImmersiveNuclearEngineering;
import net.kmn64.ine.client.utils.MCUtil;
import net.kmn64.ine.common.CommonProxy;
import net.kmn64.ine.common.multiblocks.multiblocks.SteelSheetmetalTankMultiblock;
import net.kmn64.ine.config.INEServerConfig;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = ImmersiveNuclearEngineering.MODID)
public class ClientProxy extends CommonProxy {
	public void setup(){
	}
	
	public void registerContainersAndScreens(){
	}
	
	public void preInit(){
	}
	
	public void preInitEnd(){
	}
	
	public void init(){
	}
	
	
	public void postInit(){
	}
	
	@SuppressWarnings("deprecation")
	public void completed(){
		DeferredWorkQueue.runLater(() -> ManualHelper.addConfigGetter(str -> switch(str) {
		case "steeltank_tanksize"->INEServerConfig.machines.steeltank.steelTank_tankSize.get();
		default->{
			yield null;
		}
		}));
		
		setupManualPages();
	}
	
	public void serverAboutToStart(){
	}
	
	public void serverStarting(){
	}
	
	public void serverStarted(){
	}
	
	/** Immersive Nuclear-Engineering's Manual Category */
	private static InnerNode<ResourceLocation, ManualEntry> INE_CATEGORY;
	private static InnerNode<ResourceLocation, ManualEntry> ADV_TANK_CATEGORY;
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void onModelBakeEvent(ModelBakeEvent event){
		
	}
	
	public void renderTile(TileEntity te, IVertexBuilder iVertexBuilder, MatrixStack transform, IRenderTypeBuffer buffer){
		
	}
	
	public void handleEntitySound(SoundEvent soundEvent, Entity entity, boolean active, float volume, float pitch){
	}
	
	public void handleTileSound(SoundEvent soundEvent, TileEntity te, boolean active, float volume, float pitch){
	}
	
	public void setupManualPages(){
		ManualInstance man = ManualHelper.getManual();
		
		ADV_TANK_CATEGORY = man.getRoot().getOrCreateSubnode(new ResourceLocation(ImmersiveNuclearEngineering.MODID,"adv_tank"), 100);
		
		steeltank(new ResourceLocation(ImmersiveNuclearEngineering.MODID,"steel_tank"),0);
		oiltank(new ResourceLocation(ImmersiveNuclearEngineering.MODID,"oil_tank"),1);
	}
	
	private void oiltank(ResourceLocation resourceLocation, int i) {
		// TODO Auto-generated method stub
		ManualInstance man = ManualHelper.getManual();
		
		ManualEntry.ManualEntryBuilder builder = new ManualEntry.ManualEntryBuilder(man);
		builder.addSpecialElement("oiltank", 0, () -> new ManualElementMultiblock(man, SteelSheetmetalTankMultiblock.instance));
		builder.readFromFile(resourceLocation);
		man.addEntry(ADV_TANK_CATEGORY, builder.create(), i);
	}

	private void steeltank(ResourceLocation resourceLocation, int i) {
		// TODO Auto-generated method stub
		ManualInstance man = ManualHelper.getManual();
		
		ManualEntry.ManualEntryBuilder builder = new ManualEntry.ManualEntryBuilder(man);
		builder.addSpecialElement("steeltank", 0, () -> new ManualElementMultiblock(man, SteelSheetmetalTankMultiblock.instance));
		builder.readFromFile(resourceLocation);
		man.addEntry(ADV_TANK_CATEGORY, builder.create(), i);
	}

	public void drawUpperHalfSlab(MatrixStack transform, ItemStack stack){
	}
	
	public void openProjectorGui(Hand hand, ItemStack held){
	}
	
	public World getClientWorld(){
		return MCUtil.getWorld();
	}
	
	public PlayerEntity getClientPlayer(){
		return MCUtil.getPlayer();
	}
}
