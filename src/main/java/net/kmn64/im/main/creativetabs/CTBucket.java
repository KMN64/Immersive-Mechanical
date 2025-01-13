package net.kmn64.im.main.creativetabs;

import javax.annotation.Nonnull;

import net.kmn64.im.IMMain;
import net.kmn64.im.main.item.IMMaterials;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.api.distmarker.Dist;

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
public class CTBucket extends ItemGroup {
    public static final CTBucket INSTANCE = new CTBucket();

    @SuppressWarnings("deprecation")
    public CTBucket()
    {
        super(String.format("%s_BUCKET",IMMain.MODID));
        setBackgroundSuffix("item_search.png");
    }

    @Override
    public ItemStack makeIcon() {
        return new ItemStack(null);
    }

    @Override
    public boolean hasSearchBar() {
        return true;
    }
}
