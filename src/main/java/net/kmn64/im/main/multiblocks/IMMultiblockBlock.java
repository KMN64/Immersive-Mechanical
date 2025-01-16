package net.kmn64.im.main.multiblocks;

import java.util.Iterator;

import com.google.common.base.Supplier;

import blusunrize.immersiveengineering.common.IEContent;
import blusunrize.immersiveengineering.common.blocks.BlockItemIE;
import blusunrize.immersiveengineering.common.blocks.generic.MultiblockPartTileEntity;
import blusunrize.immersiveengineering.common.blocks.metal.MetalMultiblockBlock;
import net.kmn64.im.IMMain;
import net.kmn64.im.main.IMRegister;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.loading.FMLLoader;

public class IMMultiblockBlock<T extends MultiblockPartTileEntity<T>> extends MetalMultiblockBlock<T> {
    public IMMultiblockBlock(String name, Supplier<TileEntityType<T>> te) {
        super(name, te);

        if(!FMLLoader.isProduction()){
			IEContent.registeredIEBlocks.remove(this);
			Iterator<Item> it = IEContent.registeredIEItems.iterator();
			while(it.hasNext()){
				Item item = it.next();
				if(item instanceof BlockItemIE && ((BlockItemIE) item).getBlock() == this){
					it.remove();
					break;
				}
			}
        }

        IMRegister.blockRegister(this);
		
		BlockItem bItem = new BlockItemIE(this, new Item.Properties());
		IMRegister.itemRegister(bItem.setRegistryName(getRegistryName()));
    }

    @Override
	public ResourceLocation createRegistryName(){
        return new ResourceLocation(IMMain.MODID, this.name);
    }
}