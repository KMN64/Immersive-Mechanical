package net.kmn64.im.main.item;

import net.kmn64.im.IMMain;
import net.kmn64.im.main.creativetabs.CreativeTab;
import net.minecraft.item.Item;

public class IMBaseItem extends Item {

    public IMBaseItem(String name)
    {
        this(name,new Item.Properties().tab(CreativeTab.INSTANCE));
    }

    public IMBaseItem(String name, Item.Properties properties) {
        super(properties);
        this.setRegistryName(IMMain.MODID, name);
    }
}
