package net.kmn64.ine.client;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import blusunrize.immersiveengineering.api.ManualHelper;
import blusunrize.immersiveengineering.client.manual.ManualElementMultiblock;
import blusunrize.immersiveengineering.common.blocks.IEBlocks;
import blusunrize.immersiveengineering.common.blocks.metal.MetalScaffoldingType;
import blusunrize.lib.manual.ManualEntry;
import blusunrize.lib.manual.ManualInstance;
import blusunrize.lib.manual.Tree.InnerNode;
import net.kmn64.ine.ImmersiveNuclearEngineering;
import net.kmn64.ine.client.utils.MCUtil;
import net.kmn64.ine.common.CommonProxy;
import net.kmn64.ine.common.items.INEItemMaterialBase;
import net.kmn64.ine.common.multiblocks.multiblocks.SteelSheetmetalTankMultiblock;
import net.kmn64.ine.config.INEServerConfig;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Tessellator;
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
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = ImmersiveNuclearEngineering.MODID,bus=Bus.MOD)
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
		// Render slabs on top half
		BlockRendererDispatcher blockRenderer = Minecraft.getInstance().getBlockRenderer();
		BlockState state = IEBlocks.MetalDecoration.steelScaffolding.get(MetalScaffoldingType.STANDARD).defaultBlockState();
		IBakedModel model = blockRenderer.getBlockModelShaper().getBlockModel(state);
		
		IRenderTypeBuffer.Impl buffers = IRenderTypeBuffer.immediate(Tessellator.getInstance().getBuilder());
		
		transform.pushPose();
		transform.translate(0.0F, 0.5F, 1.0F);
		blockRenderer.getModelRenderer().renderModel(transform.last(), buffers.getBuffer(RenderType.solid()), state, model, 1.0F, 1.0F, 1.0F, -1, -1, EmptyModelData.INSTANCE);
		transform.popPose();
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
