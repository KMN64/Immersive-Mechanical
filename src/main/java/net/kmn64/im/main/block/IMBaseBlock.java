package net.kmn64.im.main.block;

import net.kmn64.im.IMMain;
import net.kmn64.im.main.IMRegister;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;

public class IMBaseBlock extends Block {
    public IMBaseBlock(String name)
    {
        this(name, AbstractBlock.Properties.of(Material.AIR));
    }

    public IMBaseBlock(String name, AbstractBlock.Properties properties) {
        super(properties);
        this.setRegistryName(IMMain.MODID, name);
        IMRegister.itemRegister(new IMBaseBlockItem(this));
    }

    public IMBaseBlock(String name, AbstractBlock.Properties bproperties, Item.Properties iproperties) {
        super(bproperties);
        this.setRegistryName(IMMain.MODID, name);
        IMRegister.itemRegister(new IMBaseBlockItem(this, iproperties));
    }
}
