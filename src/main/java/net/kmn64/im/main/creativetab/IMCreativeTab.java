package net.kmn64.im.main.creativetab;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Tuple;

public abstract class IMCreativeTab extends ItemGroup {
    public IMCreativeTab()
    {
        super("");
    }

    /**
     * Retrieves the item associated with this creative tab.
     * This method is used to determine which item should be displayed
     * as the icon for the creative tab in the Minecraft user interface.
     *
     * @return The Item object that represents this creative tab's icon.
     */
    public abstract Item getItem();

    /**
     * Retrieves the search bar information for the creative tab.
     *
     * @return A Tuple containing a Boolean and a ResourceLocation. 
     *         The Boolean indicates whether the search bar is enabled, 
     *         and the ResourceLocation specifies the location of the search bar texture.
     */
    public abstract Tuple<Boolean,ResourceLocation> getSearchBar();

    @Override
    public ItemStack makeIcon() {
        return new ItemStack(getItem());
    }

    @Override
    public boolean hasSearchBar()
    {
        boolean hasSearchBar = getSearchBar().getA();
        ResourceLocation resourceLocation = getSearchBar().getB();
        if (!hasSearchBar)
            setBackgroundImage(resourceLocation);

        return hasSearchBar;
    }
}
