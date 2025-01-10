package net.kmn64.im.main.creativetabs;

import net.kmn64.im.IMMain;
import net.kmn64.im.main.item.IMMaterials;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;


/**
 * CreativeTab is a custom item group for the mod, extending the ItemGroup class.
 * It provides a unique creative tab for organizing items in the mod.
 * 
 * <p>This class is a singleton, with the single instance accessible via the {@code INSTANCE} field.</p>
 * 
 * <p>The constructor initializes the creative tab with a name formatted using the mod ID.</p>
 * 
 * <p>The {@code makeIcon} method is overridden to specify the icon for the creative tab,
 * but currently returns an empty ItemStack.</p>
 */
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
