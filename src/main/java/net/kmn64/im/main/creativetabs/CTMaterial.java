package net.kmn64.im.main.creativetabs;

import net.kmn64.im.IMMain;
import net.kmn64.im.main.item.IMMaterials;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class CTMaterial extends ItemGroup {
    public static final CTMaterial INSTANCE = new CTMaterial();

    @SuppressWarnings("deprecation")
    public CTMaterial()
    {
        super(String.format("%s_MATERIAL",IMMain.MODID));
        setBackgroundSuffix("item_search.png");
    }

    @Override
    public ItemStack makeIcon() {
        Item item = IMMaterials.find("Sodium Chloride", "Salt");
        return new ItemStack(item);
    }

    @Override
    public boolean hasSearchBar() {
        return true;
    }
}
