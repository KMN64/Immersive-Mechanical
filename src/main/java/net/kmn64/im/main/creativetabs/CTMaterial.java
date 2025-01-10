package net.kmn64.im.main.creativetabs;

import net.kmn64.im.IMMain;
import net.kmn64.im.main.item.IMMaterials;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

/**
 * CTMaterial is a custom ItemGroup for organizing items related to materials in the mod.
 * It extends the ItemGroup class and provides a custom icon and search bar functionality.
 * 
 * <p>This class is a singleton, with the single instance accessible via the {@code INSTANCE} field.
 * The constructor sets the background suffix for the item group and initializes it with a name
 * based on the mod ID.</p>
 * 
 * <p>The {@code makeIcon} method specifies the icon for the item group, which is an item stack
 * of "Sodium Chloride" (Salt). The {@code hasSearchBar} method indicates that this item group
 * includes a search bar.</p>
 * 
 * @see ItemGroup
 */
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
        return new ItemStack(IMMaterials.find("Sodium Chloride", "Salt"));
    }

    @Override
    public boolean hasSearchBar() {
        return true;
    }
}
