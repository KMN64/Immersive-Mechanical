package net.kmn64.ine.common.multiblocks.tileentities;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.annotation.Nonnull;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.ImmutableSet;

import blusunrize.immersiveengineering.api.fluid.FluidUtils;
import blusunrize.immersiveengineering.api.utils.shapes.CachedShapesWithTransform;
import blusunrize.immersiveengineering.client.utils.TextUtils;
import blusunrize.immersiveengineering.common.blocks.IEBlockInterfaces.IBlockBounds;
import blusunrize.immersiveengineering.common.blocks.IEBlockInterfaces.IBlockOverlayText;
import blusunrize.immersiveengineering.common.blocks.IEBlockInterfaces.IComparatorOverride;
import blusunrize.immersiveengineering.common.blocks.IEBlockInterfaces.IHammerInteraction;
import blusunrize.immersiveengineering.common.blocks.IEBlockInterfaces.IPlayerInteraction;
import blusunrize.immersiveengineering.common.blocks.generic.MultiblockPartTileEntity;
import blusunrize.immersiveengineering.common.blocks.multiblocks.IETemplateMultiblock;
import blusunrize.immersiveengineering.common.util.Utils;
import net.kmn64.ine.common.INETileTypes;
import net.kmn64.ine.common.multiblocks.multiblocks.OilTankMultiblock;
import net.kmn64.ine.common.utils.AABBUtils;
import net.kmn64.ine.common.utils.FluidHelper;
import net.kmn64.ine.common.utils.LayeredComparatorOutput;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public class OilTankTileEntity extends MultiblockPartTileEntity<OilTankTileEntity> implements IPlayerInteraction, IBlockOverlayText, IBlockBounds, IHammerInteraction, IComparatorOverride {

	public enum PortState implements IStringSerializable{
		INPUT, OUTPUT;
		
		@Override
		@Nonnull
		public String getSerializedName(){
			return this.toString().toLowerCase(Locale.ENGLISH);
		}
		
		public ITextComponent getText(){
			return new TranslationTextComponent("desc.ine.info.oiltank." + getSerializedName());
		}
		
		public PortState next(){
			return this == INPUT ? OUTPUT : INPUT;
		}
	}
	
	public enum Port implements IStringSerializable{
		TOP(new BlockPos(2, 2, 3)),
		BOTTOM(new BlockPos(2, 0, 3)),
		DYNAMIC_A(new BlockPos(0, 1, 2)),
		DYNAMIC_B(new BlockPos(4, 1, 2)),
		DYNAMIC_C(new BlockPos(0, 1, 4)),
		DYNAMIC_D(new BlockPos(4, 1, 4));
		
		public static final Port[] DYNAMIC_PORTS = {DYNAMIC_A, DYNAMIC_B, DYNAMIC_C, DYNAMIC_D};
		
		public final BlockPos posInMultiblock;
		Port(BlockPos posInMultiblock){
			this.posInMultiblock = posInMultiblock;
		}
		
		public boolean matches(BlockPos posInMultiblock){
			return posInMultiblock.equals(this.posInMultiblock);
		}
		
		@Override
		@Nonnull
		public String getSerializedName(){
			return this.toString().toLowerCase(Locale.ENGLISH);
		}
		
		static Set<BlockPos> toSet(Port... ports){
			ImmutableSet.Builder<BlockPos> builder = ImmutableSet.builder();
			for(Port port:ports){
				builder.add(port.posInMultiblock);
			}
			return builder.build();
		}
	}
	
	/**
	 * Template-Location of the Redstone Input Port. (2 2 5 & 2 2 2)<br>
	 */
	public static final Set<BlockPos> Redstone_IN = ImmutableSet.of(new BlockPos(2, 2, 5), new BlockPos(2, 2, 2));
	
	public final FluidTank tank = new FluidTank(1024000, f -> !f.getFluid().getAttributes().isGaseous());
	public final EnumMap<Port, PortState> portConfig = new EnumMap<>(Port.class);
	public OilTankTileEntity() {
		super(OilTankMultiblock.instance,INETileTypes.OIL_TANK.get(),true);
		this.redstoneControlInverted = false;
		for(Port port:Port.values()){
			if(port == Port.DYNAMIC_B || port == Port.DYNAMIC_C || port == Port.BOTTOM){
				portConfig.put(port, PortState.OUTPUT);
			}else{
				portConfig.put(port, PortState.INPUT);
			}
		}
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void readCustomNBT(CompoundNBT nbt, boolean descPacket){
		super.readCustomNBT(nbt, descPacket);
		this.tank.readFromNBT(nbt.getCompound("tank"));
		
		for(Port port:Port.DYNAMIC_PORTS){
			portConfig.put(port, PortState.values()[nbt.getInt(port.getSerializedName())]);
		}
	}
	
	@Override
	public void writeCustomNBT(CompoundNBT nbt, boolean descPacket){
		super.writeCustomNBT(nbt, descPacket);
		nbt.put("tank", this.tank.writeToNBT(new CompoundNBT()));
		
		for(Port port:Port.DYNAMIC_PORTS){
			nbt.putInt(port.getSerializedName(), getPortStateFor(port).ordinal());
		}
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		if(isDummy()){
			return;
		}
		
		int threshold = 1;
		
		PortState portStateA = getPortStateFor(Port.DYNAMIC_A),
				portStateB = getPortStateFor(Port.DYNAMIC_B),
				portStateC = getPortStateFor(Port.DYNAMIC_C),
				portStateD = getPortStateFor(Port.DYNAMIC_D);
		
		boolean wasBalancing = false;
		if((portStateA == PortState.OUTPUT && portStateC == PortState.INPUT) || (portStateA == PortState.INPUT && portStateC == PortState.OUTPUT)){
			wasBalancing |= equalize(Port.DYNAMIC_A, threshold, FluidAttributes.BUCKET_VOLUME);
		}
		
		if((portStateB == PortState.OUTPUT && portStateD == PortState.INPUT) || (portStateB == PortState.INPUT && portStateD == PortState.OUTPUT)){
			wasBalancing |= equalize(Port.DYNAMIC_B, threshold, FluidAttributes.BUCKET_VOLUME);
		}
		
		if(isRSDisabled()){
			for(Port port:Port.values()){
				if((!wasBalancing && getPortStateFor(port) == PortState.OUTPUT) || (wasBalancing && port == Port.BOTTOM)){
					Direction facing = getPortDirection(port);
					BlockPos pos = getBlockPosForPos(port.posInMultiblock).relative(facing);
					
					FluidUtil.getFluidHandler(this.level, pos, facing.getOpposite()).map(out -> {
						if(this.tank.getFluidAmount() > 0){
							FluidStack fs = FluidHelper.copyFluid(this.tank.getFluid(), Math.min(tank.getFluidAmount(), 432));
							int accepted = out.fill(fs, FluidAction.SIMULATE);
							if(accepted > 0){
								int drained = out.fill(FluidHelper.copyFluid(fs, Math.min(fs.getAmount(), accepted)), FluidAction.EXECUTE);
								this.tank.drain(Utils.copyFluidStackWithAmount(this.tank.getFluid(), drained,false), FluidAction.EXECUTE);
								this.setChanged();
								this.markContainingBlockForUpdate(null);
								return true;
							}
						}
						return false;
					}).orElse(false);
				}
			}
		}
		
		this.comparatorHelper.update(this.tank.getFluidAmount());
	}
	
	private boolean equalize(Port port, int threshold, int maxTransfer){
		Direction facing = getPortDirection(port);
		BlockPos pos = getBlockPosForPos(port.posInMultiblock).relative(facing);
		TileEntity te = getLevel().getBlockEntity(pos);
		
		if(te instanceof OilTankTileEntity otherMaster && (otherMaster = otherMaster.master()) != null){
			
			int diff = otherMaster.tank.getFluidAmount() - this.tank.getFluidAmount();
			int amount = Math.min(Math.abs(diff) / 2, maxTransfer);
			
			return (diff <= -threshold && transfer(this, otherMaster, amount)) || (diff >= threshold && transfer(otherMaster, this, amount));
		}
		
		return false;
	}
	
	private boolean transfer(OilTankTileEntity src, OilTankTileEntity dst, int amount){
		FluidStack fs = new FluidStack(src.tank.getFluid(), amount);
		int accepted = dst.tank.fill(fs, FluidAction.SIMULATE);
		if(accepted > 0){
			fs = new FluidStack(src.tank.getFluid(), accepted);
			dst.tank.fill(fs, FluidAction.EXECUTE);
			src.tank.drain(fs, FluidAction.EXECUTE);
			
			src.setChanged();
			dst.setChanged();
			src.markContainingBlockForUpdate(null);
			dst.markContainingBlockForUpdate(null);
			return true;
		}
		
		return false;
	}
	
	private Direction getPortDirection(Port port){
		switch(port){
			case DYNAMIC_B:
			case DYNAMIC_D:{
				return getIsMirrored() ? getFacing().getCounterClockWise() : getFacing().getClockWise();
			}
			case DYNAMIC_A:
			case DYNAMIC_C:{
				return getIsMirrored() ? getFacing().getClockWise() : getFacing().getCounterClockWise();
			}
			case TOP:{
				return Direction.UP;
			}
			default:
				return Direction.DOWN;
		}
	}
	
	@Override
	public boolean isRSDisabled(){
		Set<BlockPos> rsPositions = getRedstonePos();
		if(rsPositions == null || rsPositions.isEmpty())
			return false;
		MultiblockPartTileEntity<?> master = master();
		if(master == null)
			master = this;
		if(master.computerControl.isStillAttached())
			return !master.computerControl.isEnabled();
		
		boolean ret = false;
		for(BlockPos rsPos:rsPositions){
			OilTankTileEntity tile = this.getTileForPos(rsPos);
			if(tile != null){
				ret |= tile.isRSPowered();
			}
		}
		return this.redstoneControlInverted != ret;
	}

	@Override
	protected IFluidTank[] getAccessibleFluidTanks(Direction paramDirection) {
		// TODO Auto-generated method stub
		OilTankTileEntity master = master();
		if(master!=null&&portConfig.keySet().stream().anyMatch((a)->{return posInMultiblock==a.posInMultiblock;}))
			return new FluidTank[]{master.tank};
		return new FluidTank[0];
	}
	
	@Override
	public Set<BlockPos> getRedstonePos(){
		return Redstone_IN;
	}

	@Override
	protected boolean canFillTankFrom(int paramInt, Direction paramDirection, FluidStack paramFluidStack) {
		// TODO Auto-generated method stub
		return portConfig.keySet().stream().filter((a)->{
			return a.posInMultiblock==posInMultiblock;
		}).anyMatch((a)->{
			return portConfig.get(a)==PortState.INPUT;
		});
	}

	@Override
	protected boolean canDrainTankFrom(int paramInt, Direction paramDirection) {
		// TODO Auto-generated method stub
		return portConfig.keySet().stream().filter((a)->{
			return a.posInMultiblock==posInMultiblock;
		}).anyMatch((a)->{
			return portConfig.get(a)==PortState.OUTPUT;
		});
	}
	
	private final LayeredComparatorOutput comparatorHelper = new LayeredComparatorOutput(
			this.tank.getCapacity(),
			3,
			() -> this.level.updateNeighborsAt(getBlockPos(), getBlockState().getBlock()),
			layer -> {
				BlockPos masterPos = this.worldPosition.subtract(this.offsetToMaster);
				for(int z = -2;z <= 2;z++){
					for(int x = -2;x <= 2;x++){
						BlockPos pos = masterPos.offset(x, layer, z);
						this.level.updateNeighborsAt(pos, this.level.getBlockState(pos).getBlock());
					}
				}
			});

	@Override
	public int getComparatorInputOverride() {
		// TODO Auto-generated method stub
		OilTankTileEntity master = master();
		if(master != null && this.offsetToMaster.getY() >= 0 && this.offsetToMaster.getY() < this.comparatorHelper.getLayers()){
			return master.comparatorHelper.getLayerOutput(this.offsetToMaster.getY());
		}
		return 0;
	}

	@Override
	public boolean hammerUseSide(Direction param1Direction, PlayerEntity param1PlayerEntity, Hand param1Hand,
			Vector3d param1Vector3d) {
		// TODO Auto-generated method stub
		World level = this.getLevel();
		if(!level.isClientSide){
			for(Port port:Port.DYNAMIC_PORTS){
				if(port.posInMultiblock.equals(this.posInMultiblock)){
					OilTankTileEntity master = master();
					if(master != null){
						PortState portState = master.getPortStateFor(port);
						master.portConfig.replace(port,portState, portState.next());
						this.updateMasterBlock(null, true);
						return true;
					}
					break;
				}
			}
		}
		return false;
	}
	
	public PortState getPortStateFor(Port port){
		return this.portConfig.get(port);
	}

	private static final CachedShapesWithTransform<BlockPos, Pair<Direction, Boolean>> SHAPES = CachedShapesWithTransform.createForMultiblock(OilTankTileEntity::getShape);
	@Override
	public VoxelShape getBlockBounds(ISelectionContext param1iSelectionContext) {
		// TODO Auto-generated method stub
		return SHAPES.get(this.posInMultiblock,Pair.of(getFacing(), getIsMirrored()));
	}
	
	private static List<AxisAlignedBB> getShape(BlockPos posInMultiblock){
		int x = posInMultiblock.getX();
		int y = posInMultiblock.getY();
		int z = posInMultiblock.getZ();
		
		List<AxisAlignedBB> main = new ArrayList<>();
		
		// Corner Supports
		if(y == 0){
			// Corner Supports
			if(x == 0 && z == 1){
				AABBUtils.box16(main, 0, 0, 0, 4, 16, 4);
				AABBUtils.box16(main, 8, 0, 8, 16, 8, 16);
			}
			if(x == 4 && z == 1){
				AABBUtils.box16(main, 12, 0, 0, 16, 16, 4);
				AABBUtils.box16(main, 0, 0, 8, 8, 8, 16);
			}
			if(x == 0 && z == 5){
				AABBUtils.box16(main, 0, 0, 12, 4, 16, 16);
				AABBUtils.box16(main, 8, 0, 0, 16, 8, 8);
			}
			if(x == 4 && z == 5){
				AABBUtils.box16(main, 12, 0, 12, 16, 16, 16);
				AABBUtils.box16(main, 0, 0, 0, 8, 8, 8);
			}
			
			// Edge and Corner Angles
			
			if(!(x >= 1 && z >= 2 && x <= 3 && z <= 4))
				AABBUtils.box16(main, 0, 8, 0, 16, 16, 16);
			
			if(z >= 2 && z <= 4){
				if(x == 0) AABBUtils.box16(main, 8, 0, 0, 16, 8, 16);
				if(x == 4) AABBUtils.box16(main, 0, 0, 0, 8, 8, 16);
			}
			if(x >= 1 && x <= 3){
				if(z == 1) AABBUtils.box16(main, 0, 0, 8, 16, 8, 16);
				if(z == 5) AABBUtils.box16(main, 0, 0, 0, 16, 8, 8);
			}
		}
		
		// Easy Access Ladders™
		if(x == 3 && z == 0){
			if(y == 1 || y == 2){
				AABBUtils.box16(main, 2, 0, 15, 14, 16, 16);
			}
		}
		
		// Easy Access Slabs™
		if(y == 2){
			if(z == 0 && (x == 2 || x == 4)){
				AABBUtils.box16(main, 0, 8, 0, 16, 16, 16);
			}
		}
		
		// Railings
		if(y == 3){
			if(z >= 1 && z <= 5){
				if(x == 0){
					AABBUtils.box16(main, 0, 0, 0, 1, 16, 16);
				}else if(x == 4){
					AABBUtils.box16(main, 15, 0, 0, 16, 16, 16);
				}
			}
			if(x >= 0 && x <= 4){
				if(z == 5){
					AABBUtils.box16(main, 0, 0, 15, 16, 16, 16);
				}else if(z == 1 && x != 4){
					AABBUtils.box16(main, 0, 0, 0, 16, 16, 1);
				}
			}
		}
		
		// Use default cube shape if nessesary
		if(main.isEmpty()){
			main.add(AABBUtils.FULL);
		}
		return main;
	}

	@Override
	public ITextComponent[] getOverlayText(PlayerEntity param1PlayerEntity, RayTraceResult param1RayTraceResult,
			boolean param1Boolean) {
		// TODO Auto-generated method stub
		if(Utils.isFluidRelatedItemStack(param1PlayerEntity.getItemInHand(Hand.MAIN_HAND))){
			OilTankTileEntity master = master();
			FluidStack fs = master != null ? master.tank.getFluid() : this.tank.getFluid();
			return new ITextComponent[]{TextUtils.formatFluidStack(fs)};
		}
		return null;
	}

	@Override
	public boolean useNixieFont(PlayerEntity param1PlayerEntity, RayTraceResult param1RayTraceResult) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean interact(Direction param1Direction, PlayerEntity param1PlayerEntity, Hand param1Hand,
			ItemStack param1ItemStack, float param1Float1, float param1Float2, float param1Float3) {
		// TODO Auto-generated method stub
		OilTankTileEntity master = this.master();
		if(master != null){
			if(FluidUtils.interactWithFluidHandler(param1PlayerEntity, param1Hand, master.tank)){
				this.updateMasterBlock(null, true);
				return true;
			}
		}
		return false;
	}

}
