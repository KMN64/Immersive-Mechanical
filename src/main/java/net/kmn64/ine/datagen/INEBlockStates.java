package net.kmn64.ine.datagen;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;

import blusunrize.immersiveengineering.api.IEProperties;
import blusunrize.immersiveengineering.api.multiblocks.TemplateMultiblock;
import net.kmn64.ine.ImmersiveNuclearEngineering;
import net.kmn64.ine.common.INEContent;
import net.kmn64.ine.common.multiblocks.multiblocks.DistillerMultiblock;
import net.kmn64.ine.common.multiblocks.multiblocks.OilTankMultiblock;
import net.kmn64.ine.common.multiblocks.multiblocks.SteelSheetmetalTankMultiblock;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.Property;
import net.minecraft.util.Direction;
import net.minecraft.util.Direction.Axis;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.VariantBlockStateBuilder;
import net.minecraftforge.client.model.generators.VariantBlockStateBuilder.PartialBlockstate;
import net.minecraftforge.client.model.generators.loaders.OBJLoaderBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;

public class INEBlockStates extends ExtendedBlockstateProvider{
	private static final List<Vector3i> CUBE_THREE = BlockPos.betweenClosedStream(-1, -1, -1, 1, 1, 1)
			.map(BlockPos::immutable)
			.collect(Collectors.toList());
	private static final List<Vector3i> CUBE_TWO = BlockPos.betweenClosedStream(0, 0, -1, 1, 1, 0)
			.map(BlockPos::immutable)
			.collect(Collectors.toList());
	
	protected static final Map<ResourceLocation, String> generatedParticleTextures = new HashMap<>();
	public INEBlockStates(DataGenerator gen, ExistingFileHelper exFileHelper) {
		super(gen, exFileHelper);
		// TODO Auto-generated constructor stub
	}
	@Override
	protected void registerStatesAndModels() {
		createMultiblock(INEContent.Multiblocks.distiller, split(obj("multiblocks/obj/distiller.obj"),OilTankMultiblock.instance),split(obj("multiblocks/obj/distiller_mirrored.obj"),DistillerMultiblock.instance));
		createMultiblock(INEContent.Multiblocks.steeltank, split(obj("multiblocks/obj/steel_tank.obj"),SteelSheetmetalTankMultiblock.instance));
		createMultiblock(INEContent.Multiblocks.oiltank, split(obj("multiblocks/obj/oil_tank.obj"),OilTankMultiblock.instance),split(obj("multiblocks/obj/oil_tank_mirrored.obj"),OilTankMultiblock.instance));
	}
	
	private ModelFile cubeThree(String name, ResourceLocation def, ResourceLocation front)
	{
		ModelFile baseModel = obj(name, modLoc("multiblocks/cube_three.obj"),
				ImmutableMap.of("side", def, "front", front));
		return splitModel(name+"_split", baseModel, CUBE_THREE, false);
	}

	private void createMultiblock(Block b, ModelFile masterModel, ModelFile mirroredModel)
	{
		createMultiblock(b, masterModel, mirroredModel, IEProperties.FACING_HORIZONTAL, IEProperties.MIRRORED);
	}

	private void createMultiblock(Block b, ModelFile masterModel)
	{
		createMultiblock(b, masterModel, null, IEProperties.FACING_HORIZONTAL, null);
	}

	private void createMultiblock(Block b, ModelFile masterModel, @Nullable ModelFile mirroredModel,
								  @Nullable Property<Boolean> mirroredState)
	{
		createMultiblock(b, masterModel, mirroredModel, IEProperties.FACING_HORIZONTAL, mirroredState);
	}

	private void createMultiblock(Block b, ModelFile masterModel, @Nullable ModelFile mirroredModel,
								  EnumProperty<Direction> facing, @Nullable Property<Boolean> mirroredState)
	{
		Preconditions.checkArgument((mirroredModel==null)==(mirroredState==null));
		VariantBlockStateBuilder builder = getVariantBuilder(b);
		boolean[] possibleMirrorStates;
		if(mirroredState!=null)
			possibleMirrorStates = new boolean[]{false, true};
		else
			possibleMirrorStates = new boolean[1];
		for(boolean mirrored : possibleMirrorStates)
			for(Direction dir : facing.getPossibleValues())
			{
				final int angleY;
				final int angleX;
				if(facing.getPossibleValues().contains(Direction.UP))
				{
					angleX = -90*dir.getStepY();
					if(dir.getAxis()!=Axis.Y)
						angleY = getAngle(dir, 180);
					else
						angleY = 0;
				}
				else
				{
					angleY = getAngle(dir, 180);
					angleX = 0;
				}
				ModelFile model = mirrored?mirroredModel: masterModel;
				PartialBlockstate partialState = builder.partialState()
						.with(facing, dir);
				if(mirroredState!=null)
					partialState = partialState.with(mirroredState, mirrored);
				partialState.setModels(new ConfiguredModel(model, angleX, angleY, true));
			}
	}

	private ModelFile split(ModelFile loc, TemplateMultiblock mb)
	{
		return split(loc, mb, false);
	}

	private ModelFile split(ModelFile loc, TemplateMultiblock mb, boolean mirror)
	{
		return split(loc, mb, mirror, false);
	}

	private ModelFile splitDynamic(ModelFile loc, TemplateMultiblock mb, boolean mirror)
	{
		return split(loc, mb, mirror, true);
	}

	private ModelFile split(ModelFile loc, TemplateMultiblock mb, boolean mirror, boolean dynamic)
	{
		UnaryOperator<BlockPos> transform = UnaryOperator.identity();
		if(mirror)
		{
			Vector3i size = mb.getSize(null);
			transform = p -> new BlockPos(size.getX()-p.getX()-1, p.getY(), p.getZ());
		}
		return split(loc, mb, transform, dynamic);
	}

	private ModelFile split(
			ModelFile name, TemplateMultiblock multiblock, UnaryOperator<BlockPos> transform, boolean dynamic
	)
	{
		final Vector3i offset = multiblock.getMasterFromOriginOffset();
		Stream<Vector3i> partsStream = multiblock.getStructure(null)
				.stream()
				.filter(info -> !info.state.isAir())
				.map(info -> info.pos)
				.map(transform)
				.map(p -> p.subtract(offset));
		return split(name, partsStream.collect(Collectors.toList()), dynamic);
	}
	

}
