package net.kmn64.ine.datagen;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import net.kmn64.ine.ImmersiveNuclearEngineering;
import net.kmn64.ine.common.INEContent;
import net.kmn64.ine.common.INEContent.Multiblocks;
import net.kmn64.ine.common.fluids.INEFluid;
import net.kmn64.ine.common.fluids.INEGaseousFluid;
import net.kmn64.ine.common.fluids.INEMoltenFluid;
import net.kmn64.ine.common.fluids.INESolidFluid;
import net.minecraft.data.DataGenerator;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelBuilder;
import net.minecraftforge.client.model.generators.ModelBuilder.Perspective;
import net.minecraftforge.client.model.generators.loaders.DynamicBucketModelBuilder;
import net.minecraftforge.client.model.generators.loaders.OBJLoaderBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraft.util.ResourceLocation;

public class INEItemModels extends ItemModelProvider{

	public INEItemModels(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator, ImmersiveNuclearEngineering.MODID, existingFileHelper);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String getName(){
		return ImmersiveNuclearEngineering.MODID+"'s Item Models";
	}

	@Override
	protected void registerModels() {
		// TODO Auto-generated method stub
		final ArrayList<Fluid> listfluid = new ArrayList<>();
		listfluid.addAll(INEFluid.INE_FLUIDS);
		listfluid.addAll(INEGaseousFluid.INEGASEOUS_FLUIDS);
		listfluid.addAll(INEMoltenFluid.INEMOLTEN_FLUIDS);
		listfluid.addAll(INESolidFluid.INESOLID_FLUIDS);
		
		for(Fluid f:listfluid)
			createBucket(f);
		
		steeltankItem();
	}
	
	private void steeltankItem() {
		// TODO Auto-generated method stub
		ItemModelBuilder model = obj(Multiblocks.steeltank, "multiblocks/obj/steel_tank.obj").texture("texture", modLoc("multiblocks/steel_tank"));
		
		ModelBuilder<?>.TransformsBuilder trans = model.transforms();
		List.of(Perspective.values()).forEach((a)->{
			doTransform(trans,a,new Vector3f(0,-0.25f,0),new Vector3f(20,-45,0),0.1875f);
		});
	}

	private final Vector3f ZERO = new Vector3f(0, 0, 0);
	private void doTransform(ModelBuilder<?>.TransformsBuilder transform, Perspective type, Vector3f translation, @Nullable Vector3f rotationAngle, float scale){
		if(rotationAngle == null){
			rotationAngle = ZERO;
		}
		
		transform.transform(type)
				.translation(translation.x(), translation.y(), translation.z())
				.rotation(rotationAngle.x(), rotationAngle.y(), rotationAngle.z())
				.scale(scale)
				.end();
	}
	
	private ItemModelBuilder obj(IItemProvider item, String model){
		return getBuilder(item.asItem().getRegistryName().toString())
				.customLoader(OBJLoaderBuilder::begin)
				.modelLocation(modLoc("models/" + model)).flipV(true).end();
	}
	
	private void genericItem(Item item){
		if(item == null){
			StackTraceElement where = new NullPointerException().getStackTrace()[1];
			//IPDataGenerator.log.warn("Skipping null item. ( {} -> {} )", where.getFileName(), where.getLineNumber());
			return;
		}
		String name = name(item);
		
		getBuilder(name)
			.parent(getExistingFile(mcLoc("item/generated")))
			.texture("layer0", modLoc("item/"+name));
	}
	
	private void createBucket(Fluid f){
		withExistingParent(f.getBucket().asItem().getRegistryName().getPath(), new ResourceLocation("forge","item/bucket"))
			.customLoader(DynamicBucketModelBuilder::begin)
			.fluid(f);
	}
	
	private String name(IItemProvider item){
		return item.asItem().getRegistryName().getPath();
	}
	
}