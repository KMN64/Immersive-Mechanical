package net.kmn64.im.main.creativetabs;

import net.kmn64.im.IMMain;
import net.kmn64.im.main.item.IMMaterials;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class CreativeTab extends ItemGroup {
    public static final CreativeTab INSTANCE = new CreativeTab();

    public CreativeTab()
    {
        super(String.format("%s_CREATIVETAB",IMMain.MODID));
    }

    @Override
    public ItemStack makeIcon() {
        return new ItemStack(null);
    }

}
