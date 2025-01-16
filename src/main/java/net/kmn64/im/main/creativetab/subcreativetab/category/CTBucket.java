package net.kmn64.im.main.creativetab.subcreativetab.category;

import net.kmn64.im.IMMain;
import net.kmn64.im.main.creativetab.IMCreativeTab;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Tuple;

/**
 * CTBucket is a custom ItemGroup for the Immersive Mechanical mod.
 * This class represents a creative tab specifically for bucket-related items.
 * It extends the ItemGroup class and provides custom behavior for the tab.
 * 
 * <p>Features:</p>
 * <ul>
 *   <li>Singleton instance accessible via {@code CTBucket.INSTANCE}</li>
 *   <li>Custom background suffix for the search bar</li>
 *   <li>Custom icon for the tab, represented by Sodium Chloride (Salt)</li>
 *   <li>Enables the search bar within the tab</li>
 * </ul>
 * 
 * <p>Usage:</p>
 * <pre>
 * {@code
 * ItemGroup bucketTab = CTBucket.INSTANCE;
 * }
 * </pre>
 * 
 * @see net.minecraft.item.ItemGroup
 */
public class CTBucket extends IMCreativeTab {
    public static final CTBucket INSTANCE = new CTBucket();

    public CTBucket()
    {
        super();
    }

    @Override
    public Item getItem() {
        return null;
    }

    @SuppressWarnings("null")
    @Override
    public Tuple<Boolean, ResourceLocation> getSearchBar() {
        return new Tuple<>(false,null);
    }
}
